package br.ita.automatos;

import java.util.Optional;

public abstract class TransitionDecomposition {

	private Optional<TransitionDecomposition> next = Optional.empty();
	
	public TransitionDecomposition() {	
	}
	
	public TransitionDecomposition(TransitionDecomposition next) {
		this.next = Optional.ofNullable(next);
	}
	
	public void apply(Automata automata, Transition transition) {
		if (this.isApplicable(transition)) {
			this.decompose(automata, transition);
		} else {
			next.ifPresent(next -> next.apply(automata, transition));
		}
	}

	public TransitionDecomposition decorate(TransitionDecomposition other) {
		this.next = Optional.ofNullable(other);
		return this;
	}
	
	protected abstract void decompose(Automata automata, Transition transition);
	public abstract boolean isApplicable(Transition transition);

	protected int indexOfFirstNonEnclosedChar(String text, char character) {
		boolean enclosed = false;
		for (int i = 0; i < text.length(); i++) {
			char currChar = text.charAt(i);
			if (currChar == '(') {
				enclosed = true;
			} else if (currChar == ')') {
				enclosed = false;
			} else if (currChar == character && !enclosed) {
				return i;
			}
		}
		return -1;
	}
}
