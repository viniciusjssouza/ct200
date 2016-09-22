package br.ita.automatos;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by vinicius on 21/09/16.
 */
public class EpsilonRemover {

    private Automata automata;
    private EpsilonClosure closureFinder;
    private Set<Transition> transitionsToAdd = new HashSet<>();

    public EpsilonRemover(Automata automata) {
        this.automata = automata;
        this.closureFinder = new EpsilonClosure(this.automata);
    }

    public void removeEpsilonTransitions() {
        transitionsToAdd.clear();
        // Statement II
        this.automata.getTransitions()
                .stream()
                .filter(t -> !t.isEpsilonTransition())
                .forEach(t -> {
                    replaceTransitiveEpsilons(t);
                });

        // Statement III
        this.automata.getTransitions()
            .stream()
            .filter(t -> t.isEpsilonTransition() && !t.isSelfTransition())
            .forEach(t -> {
                replaceEpsilonTransition(t);
            });

        // Statement IV
        this.automata.nodes().stream()
                .filter(n -> !automata.getEndNodes().contains(n) &&
                        hasFinalNodesOnClosure(n))
                .forEach(nodeA -> automata.makeEndNode(nodeA));


        automata.getTransitions().stream()
                .filter(Transition::isEpsilonTransition)
                .forEach(t -> t.getSourceNode().removeTransition(t));

        transitionsToAdd.forEach(t -> t.getSourceNode().addTransition(t.getInput(), t.getNextNode()));
    }

    private boolean hasFinalNodesOnClosure(Node nodeA) {
        return closureFinder.compute(nodeA).stream()
            .filter(nodeB -> automata.getEndNodes().contains(nodeB))
            .count() > 0;
    }

    private void replaceTransitiveEpsilons(Transition transition) {
        Node nodeA = transition.getSourceNode();
        Node nodeB = transition.getNextNode();

        closureFinder.compute(nodeB).stream()
                .filter(n -> !n.equals(nodeB))
                .forEach(nodeC ->
                    transitionsToAdd.add(new Transition(nodeA, nodeC, transition.getInput()))
                );
    }

    private void replaceEpsilonTransition(Transition transition) {
        Node nodeA = transition.getSourceNode();
        Set<Node> closure = closureFinder.compute(nodeA);
        closure.stream()
            .filter(nodeB -> !nodeB.equals(nodeA))
            .forEach(nodeB -> {
                nodeB.getTransitions().stream()
                    .filter(tB -> !tB.isEpsilonTransition() && !tB.getNextNode().equals(nodeA))
                    .forEach(tB -> {
                        transitionsToAdd.add(new Transition(nodeA, tB.getNextNode(), tB.getInput()));
                    });
            });
    }
}

