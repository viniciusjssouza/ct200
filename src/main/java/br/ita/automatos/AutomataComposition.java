package br.ita.automatos;

import com.google.common.base.Preconditions;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class AutomataComposition {

	private Automata automata;

	public AutomataComposition(Automata automata) {
		Preconditions.checkNotNull(automata, "The automata cannot be null");
		this.automata = automata;
	}

	public String compose() {
		automata.removeEpsilonTransitions();
		while (automata.nodes().size() > 2) {
			System.out.println(automata);
			joinTransitions();
			Node toRemove = automata.getStartNode().neighbours()
					.stream()
					.filter(n -> !automata.isEndNode(n))
					.findAny()
					.get();
			removeNode(automata.getStartNode(), toRemove);
		}
		return "";
	}

	private void removeNode(Node startNode, Node anyNode) {
		for (Transition transition : anyNode.getTransitions()) {
			if (transition.isSelfTransition() || automata.isStartNode(transition.getNextNode())) {
				String input = transition.containsSingleSymbol() ?
						transition.getInput() + Alphabet.KLEENE_CLOSURE : String.format("(%s)*", transition.getInput());
				startNode.addTransition(input, startNode);
			} else {
				Transition tA = automata.transition(startNode, anyNode).iterator().next();
				String input = transition.containsSingleSymbol() || transition.getInput().startsWith("(") ?
						tA.getInput() + transition.getInput() : String.format("%s(%s)", tA.getInput(), transition.getInput());
				startNode.addTransition(input, transition.getNextNode());
			}
		}
		automata.removeNode(anyNode);
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
