package br.ita.automatos;

import com.google.common.base.Preconditions;

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
		return chain(new UnionDecomposition(),
				new ConcatDecomposition(),
				new KleeneDecomposition(),
				new ParenthesizedDecomposition()
		);
	}

	private TransitionDecomposition chain(TransitionDecomposition...decompositions) {
		Preconditions.checkNotNull(decompositions, "Null decompositions");
		Preconditions.checkArgument(decompositions.length > 0, "The decompositions cannot be empty");
		
		for (int i = 0; i < decompositions.length-1; i++) {
			decompositions[i].decorate(decompositions[i+1]);
		}		
		return decompositions[0];
	}

}
