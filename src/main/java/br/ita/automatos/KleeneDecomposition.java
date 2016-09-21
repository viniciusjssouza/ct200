package br.ita.automatos;

import com.google.common.base.Preconditions;

public class KleeneDecomposition extends TransitionDecomposition {

	@Override
	public boolean isApplicable(Transition transition) {
		Preconditions.checkNotNull(transition, "The transition cannot be null");

		int index = indexOfFirstNonEnclosedChar(transition.getInput(), Alphabet.KLEENE_CLOSURE);
		return index > 0;
	}

	@Override
	protected void decompose(Automata automata, Transition transition) {
		Preconditions.checkNotNull(automata, "The automata cannot be null");
		Preconditions.checkNotNull(transition, "The transition cannot be null");
		
		Node from = transition.getSourceNode();
		Node to = transition.getNextNode();
		Node middleNode = automata.createNode();

				
		from.removeTransition(transition);

		int index = indexOfFirstNonEnclosedChar(transition.getInput(), Alphabet.KLEENE_CLOSURE);

		from.addTransition(String.valueOf(Alphabet.EPSILON), middleNode);
		middleNode.addTransition(transition.getInput().substring(0, index),	middleNode);

		if (isKleeneLastChar(transition, index)) {
			middleNode.addTransition(String.valueOf(Alphabet.EPSILON), to);
		} else {
			Node middleNode2 = automata.createNode();
			middleNode.addTransition(String.valueOf(Alphabet.EPSILON),	middleNode2);
			middleNode2.addTransition(transition.getInput().substring(index+1, transition.getInput().length()), to);
		}
	}

	private boolean isKleeneLastChar(Transition transition, int index) {
		return index == transition.getInput().length()-1;
	}


}
