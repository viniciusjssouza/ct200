package br.ita.automatos;

import com.google.common.base.Preconditions;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class AutomataComposition {

	private static final String FINAL_STATE = "F";
	private static final String START_STATE = "S";
	private Automata automata;

	public AutomataComposition(Automata automata) {
		Preconditions.checkNotNull(automata, "The automata cannot be null");
		this.automata = automata;
	}

	public String compose() {
		automata.newStartState(START_STATE);
		automata.newFinalState(FINAL_STATE);
		
		while (automata.nodes().size() > 2) {
			joinTransitions();
			Node toRemove = automata.nodes().stream()
					.filter(n -> !automata.isStartNode(n) && !automata.isEndNode(n))
					.findAny().get();
			removeNode(toRemove);
		}
		
		joinTransitions();
		return automata.getStartNode().getTransitions().iterator().next().getInput();
		
	}

	private void removeNode(Node qi) {
		Set<Transition> toAdd =  new HashSet<Transition>();
		automata.getTransitions().stream()
			.filter(t1 -> t1.getNextNode().equals(qi) && !t1.getSourceNode().equals(qi))
			.forEach(t1 -> {
				automata.getTransitions().stream()
					.filter(t2 -> t2.getSourceNode().equals(qi) && !t2.getNextNode().equals(qi))
					.forEach(t2 -> {
						Node qj = t1.getSourceNode();
						Node qk = t2.getNextNode();
						final StringBuilder input = new StringBuilder();
						if (qi.hasSelfTransition()) {
							qi.getTransitions().stream()
								.filter(ti -> ti.isSelfTransition())
								.forEach(ti -> {
									if (ti.containsSingleSymbol())
										input.append(String.format("%s%s*%s", t1.getInput(), ti.getInput(), t2.getInput()));
									else 
										input.append(String.format("%s(%s)*%s", t1.getInput(), ti.getInput(), t2.getInput()));
								});
						} else {	
							if (t1.containsSingleSymbol())
								input.append(t1.getInput()); 
							else 
								input.append(String.format("(%s)", t1.getInput()));
							
							if (t2.containsSingleSymbol())
								input.append(t2.getInput()); 
							else 
								input.append(String.format("(%s)", t2.getInput()));
						}
						String transitionInput = input.toString().replace(String.valueOf(Alphabet.EPSILON), "");
						toAdd.add(new Transition(qj, qk, transitionInput.isEmpty() ? String.valueOf(Alphabet.EPSILON) : transitionInput));
					});
			});
		automata.removeNode(qi);
		toAdd.forEach(t -> t.getSourceNode().addTransition(t.getInput(), t.getNextNode()));
	}

	private void joinTransitions() {
		Set<Transition> toRemove = new HashSet<>();
		Set<Transition> toAdd = new HashSet<>();
		automata.nodes().forEach(nodeA -> {
			toAdd.clear();
			toRemove.clear();

			nodeA.getTransitions().stream()
				.filter(tA -> !toRemove.contains(tA))
				.forEach(tA -> {
					StringBuilder sb = new StringBuilder(tA.getInput());
					nodeA.getTransitions().stream()
							.filter(t -> !t.equals(tA) && t.hasSameDestination(tA))
							.forEach(t -> {
								sb.append("+").append(t.getInput());
								toRemove.add(t);
							});
					toRemove.add(tA);
					toAdd.add(new Transition(nodeA, tA.getNextNode(), sb.toString()));
			});
			toRemove.forEach(t -> t.getSourceNode().removeTransition(t));
			toAdd.forEach(t -> t.getSourceNode().addTransition(t.getInput(), t.getNextNode()));
		});
	}


}
