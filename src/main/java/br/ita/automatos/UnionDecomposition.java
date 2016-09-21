package br.ita.automatos;

import com.google.common.base.Preconditions;

public class UnionDecomposition extends TransitionDecomposition {

	@Override
	public boolean isApplicable(Transition transition) {
		Preconditions.checkNotNull(transition, "The transition cannot be null");
		int index = indexOfFirstNonEnclosedUnion(transition.getInput());
		return index > 0;
	}

	@Override
	protected void decompose(Automata automata, Transition transition) {
		Preconditions.checkNotNull(automata, "The automata cannot be null");
		Preconditions.checkNotNull(transition, "The transition cannot be null");
		
		Node from = transition.getSourceNode();
		Node to = transition.getNextNode();

		int index = indexOfFirstNonEnclosedUnion(transition.getInput());

		from.removeTransition(transition);

		from.addTransition(transition.getInput().substring(0, index).trim(), to);
		from.addTransition(transition.getInput().substring(index+1,  transition.getInput().length()).trim(),
				to);
	}

	private int indexOfFirstNonEnclosedUnion(String text) {
		return this.indexOfFirstNonEnclosedChar(text, Alphabet.UNION_OPERATOR);
	}

}
