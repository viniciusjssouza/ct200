package br.ita.automatos;

import com.google.common.base.Preconditions;

public class ParenthesizedDecomposition extends TransitionDecomposition {

	@Override
	public boolean isApplicable(Transition transition) {
		Preconditions.checkNotNull(transition, "The transition cannot be null");
		return transition.getInput().startsWith("(") &&
				transition.getInput().endsWith(")");
	}

	@Override
	protected void decompose(Automata automata, Transition transition) {
		Preconditions.checkNotNull(automata, "The automata cannot be null");
		Preconditions.checkNotNull(transition, "The transition cannot be null");
		
		Node from = transition.getSourceNode();
		Node to = transition.getNextNode();

		from.removeTransition(transition);

		from.addTransition(transition.getInput().substring(1, transition.getInput().length()-1),
				to);
	}


}
