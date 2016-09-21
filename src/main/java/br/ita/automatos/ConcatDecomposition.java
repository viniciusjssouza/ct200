package br.ita.automatos;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Preconditions;

public class ConcatDecomposition extends TransitionDecomposition {

	@Override
	public boolean isApplicable(Transition transition) {
		Preconditions.checkNotNull(transition, "The transition cannot be null");
		return getMatcher(transition).find();
	}

	private Matcher getMatcher(Transition transition) {
		Matcher m = Pattern.compile("^(\\w\\*?|\\(.+\\)\\*?)[\\w\\(]+").matcher(transition.getInput());
		return m;
	}

	@Override
	protected void decompose(Automata automata, Transition transition) {
		Preconditions.checkNotNull(automata, "The automata cannot be null");
		Preconditions.checkNotNull(transition, "The transition cannot be null");
		
		Node from = transition.getSourceNode();
		Node to = transition.getNextNode();
		Node middleNode = automata.createNode();
				
		Matcher matcher = getMatcher(transition);
		
		if (matcher.find()) {
			int indx = matcher.end(1);
			from.removeTransition(transition);
			from.addTransition(transition.getInput().substring(0, indx) + "", middleNode);
			middleNode.addTransition(transition.getInput().substring(indx, transition.getInput().length()), to);
		}
	}


}
