package br.ita.automatos;

public class TransitionDecompositionTest {

    protected Automata createAutomata(String s) {
        Node.clearIds();
        Automata automata = new Automata(new Node(), new Node());
        automata.getStartNode().addTransition(s, automata.getEndNodes().stream().findFirst().get());
        return automata;
    }
}
