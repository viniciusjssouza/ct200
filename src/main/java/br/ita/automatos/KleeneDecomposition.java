package br.ita.automatos;

import com.google.common.base.Preconditions;

public class KleeneDecomposition extends TransitionDecomposition {

	@Override
	public boolean isApplicable(Transition transition) {
		Preconditions.checkNotNull(transition, "The transition cannot be null");

		return transition.getInput().matches("^(\\(.+\\)|\\w)\\*$");
	}

	@Override
	protected void decompose(Automata automata, Transition transition) {
		Preconditions.checkNotNull(automata, "The automata cannot be null");
		Preconditions.checkNotNull(transition, "The transition cannot be null");
		
		Node from = transition.getSourceNode();
		Node to = transition.getNextNode();
		Node middleNode = automata.createNode();
				
		from.removeTransition(transition);

		String in = transition.getInput();
		
		from.addTransition(String.valueOf(Alphabet.EPSILON), middleNode);
		middleNode.addTransition(in.substring(0, in.length()-1), middleNode);
		middleNode.addTransition(String.valueOf(Alphabet.EPSILON), to);
	}

}
