package br.ita.automatos;

import com.google.common.base.Preconditions;

public class ConcatDecomposition extends TransitionDecomposition {

	@Override
	public boolean isApplicable(Transition transition) {
		Preconditions.checkNotNull(transition, "The transition cannot be null");
		
		return transition.getInput().length() > 1;
	}

	@Override
	protected void decompose(Automata automata, Transition transition) {
		Preconditions.checkNotNull(automata, "The automata cannot be null");
		Preconditions.checkNotNull(transition, "The transition cannot be null");
		
		Node from = transition.getSourceNode();
		Node to = transition.getNextNode();
		Node middleNode = automata.createNode();
				
		from.removeTransition(transition);
		from.addTransition(transition.getInput().charAt(0) + "", middleNode);
		middleNode.addTransition(transition.getInput().substring(1), to);
	}


}
