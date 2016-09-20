package br.ita.automatos;

public class AutomataDecomposition {

	private Automata automata;

	public AutomataDecomposition(Automata automata) {
		this.automata = automata;			
	}

	public void decompose(String regex) {
		automata.getStartNode().addTransition(regex, automata.getEndNodes().stream().findFirst().get());
		boolean automataUpdated;
		do {
			automataUpdated = tryToDecomposeTransitions();
		} while (automataUpdated);
	}

	private boolean tryToDecomposeTransitions() {
		for (Transition t : automata.getTransitions()) {				
			if (!t.containsSingleSymbol()) {
				decomposeTransition(t);
				return true;
			}
		}
		return false;
	}

	private void decomposeTransition(Transition t) {
		createDecompositions().apply(this.automata, t);
	}

	private TransitionDecomposition createDecompositions() {
		return new UnionDecomposition()
				.decorate(new ConcatDecomposition());
	}

}
