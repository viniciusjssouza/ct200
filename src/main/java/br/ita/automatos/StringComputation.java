package br.ita.automatos;

import java.util.HashSet;
import java.util.Set;

import com.google.common.base.Preconditions;

public class StringComputation {

	private Automata automata;

	private Set<Node> finalNodes = new HashSet<>();

	public StringComputation(Automata automata) {
		Preconditions.checkNotNull(automata, "The automata cannot be null");

		this.automata = automata;
	}

	public Set<Node> compute(String str) {
		finalNodes.clear();

		compute(str, automata.getStartNode(), 0);
		return finalNodes;
	}

	private void compute(String str, Node node, int index) {
		if (index == str.length()) {
			if (!finalNodes.contains(node)) {
				finalNodes.add(node);
				node.getTransitionsForInput(Alphabet.EPSILON + "").forEach(t -> {
					compute(str, t.getNextNode(), index);
				});
			}			
		} else {
			char curr = str.charAt(index);
			Set<Transition> transitions = node.getTransitionsForInput(curr + "");
			transitions.forEach(t -> {
				if (t.isEpsilonTransition())
					compute(str, t.getNextNode(), index);
				else
					compute(str, t.getNextNode(), index+1);				
			});
		}
	}

}
