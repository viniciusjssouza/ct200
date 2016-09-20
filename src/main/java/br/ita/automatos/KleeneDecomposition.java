package br.ita.automatos;

import com.google.common.base.Preconditions;

public class KleeneDecomposition extends TransitionDecomposition {

	@Override
	public boolean isApplicable(Transition transition) {
		Preconditions.checkNotNull(transition, "The transition cannot be null");
		
		return transition.getInput().endsWith(Alphabet.KLEENE_CLOSURE);
	}

	@Override
	protected void decompose(Automata automata, Transition transition) {
		Preconditions.checkNotNull(automata, "The automata cannot be null");
		Preconditions.checkNotNull(transition, "The transition cannot be null");
		
		Node from = transition.getSourceNode();
		Node to = transition.getNextNode();
		Node middleNode = automata.createNode();
				
		from.removeTransition(transition);
		//AQUIII
		from.addTransition(transition.getInput().charAt(0) + "", middleNode);
		middleNode.addTransition(transition.getInput().substring(1), to);
	}


}
