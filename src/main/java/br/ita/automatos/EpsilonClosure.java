package br.ita.automatos;

import com.google.common.base.Preconditions;

import java.util.HashSet;
import java.util.Set;

public class EpsilonClosure {

    private Automata automata;

    public EpsilonClosure(Automata automata) {
        Preconditions.checkNotNull(automata, "The automata cannot be null");
        this.automata = automata;
    }

    public Set<Node> compute(Node node) {
        Preconditions.checkNotNull(node, "the node cannot be null");
        Set<Node> closure = new HashSet<>();
        compute(closure, node);
        return closure;
    }

    private void compute(Set<Node> closure, Node node) {
        closure.add(node);
        node.getTransitions().stream()
            .filter(Transition::isEpsilonTransition)
            .forEach(t-> {
                if (!closure.contains(t.getNextNode())) {
                    compute(closure, t.getNextNode());
                }
        });
    }

}
