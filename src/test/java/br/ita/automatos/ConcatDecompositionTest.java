package br.ita.automatos;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by vinicius on 20/09/16.
 */
public class ConcatDecompositionTest extends TransitionDecompositionTest {

    private ConcatDecomposition decomposition = new ConcatDecomposition();

    @Test
    public void testIsApplicable() throws Exception {
        Transition t1 = new Transition(new Node(), new Node(), "aba");
        Transition t2 = new Transition(new Node(), new Node(), "ab+(b+c)*");
        Transition t3 = new Transition(new Node(), new Node(), "(a+b)*bb(b+a)*");
        Transition t4 = new Transition(new Node(), new Node(), "a*b+b*a");
        Transition t5 = new Transition(new Node(), new Node(), "a*b*c*");

        assertThat(decomposition.isApplicable(t1), is(true));
        assertThat(decomposition.isApplicable(t2), is(false));
        assertThat(decomposition.isApplicable(t3), is(false));
        assertThat(decomposition.isApplicable(t4), is(false));
        assertThat(decomposition.isApplicable(t5), is(false));

    }

    @Test
    public void testDecompose1() throws Exception {
        Automata automata = createAutomata("aba");
        decomposition.decompose(automata, automata.getTransitions().stream().findFirst().get());

        Node node0 = automata.getStartNode();
        Node middle = automata.getNodeById("2").get();
        Node node1 = automata.getEndNodes().stream().findFirst().get();

        assertThat(automata.getNodes().size(), is(3));
        assertThat(automata.getTransitions().size(), is(2));
        assertThat(automata.getTransitions(), hasItem(new Transition(node0, middle, "a")));
        assertThat(automata.getTransitions(), hasItem(new Transition(middle, node1, "ba")));
    }
}