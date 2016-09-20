package br.ita.automatos;

import com.google.common.base.Preconditions;

public class UnionDecomposition extends TransitionDecomposition {


	@Override
	public boolean isApplicable(Transition transition) {
		Preconditions.checkNotNull(transition, "The transition cannot be null");
		
		return transition.getInput().contains(Alphabet.UNION_OPERATOR);
	}

	@Override
	protected void decompose(Automata automata, Transition transition) {
		Preconditions.checkNotNull(automata, "The automata cannot be null");
		Preconditions.checkNotNull(transition, "The transition cannot be null");
		
		Node from = transition.getSourceNode();
		Node to = transition.getNextNode();
		String[] parts = transition.getInput().split(Alphabet.UNION_OPERATOR);
		
		from.addTransition(parts[0].trim(), to);
		from.addTransition(parts[1].trim(), to);
	}


}
