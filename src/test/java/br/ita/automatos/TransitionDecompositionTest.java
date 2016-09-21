package br.ita.automatos;

public class TransitionDecompositionTest {

    protected Automata createAutomata(String s) {
        Automata automata = new Automata();
        automata.getStartNode().addTransition(s, automata.getEndNodes().stream().findFirst().get());
        return automata;
    }
}
