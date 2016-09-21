package br.ita.automatos;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by vinicius on 20/09/16.
 */
public class UnionDecompositionTest extends TransitionDecompositionTest {

    protected UnionDecomposition decomposition = new UnionDecomposition();

    @Test
    public void testIsApplicable() throws Exception {
        Transition t1 = new Transition(new Node(), new Node(), "aba");
        Transition t2 = new Transition(new Node(), new Node(), "ab+(b+c)*");
        Transition t3 = new Transition(new Node(), new Node(), "(a+b)*bb(b+a)*");
        Transition t4 = new Transition(new Node(), new Node(), "a*b+b*a");
        Transition t5 = new Transition(new Node(), new Node(), "a*b*c*");

        assertThat(decomposition.isApplicable(t1), is(false));
        assertThat(decomposition.isApplicable(t2), is(true));
        assertThat(decomposition.isApplicable(t3), is(false));
        assertThat(decomposition.isApplicable(t4), is(true));
        assertThat(decomposition.isApplicable(t5), is(false));

    }

    @Test
    public void testDecompose1() throws Exception {
        Automata automata = createAutomata("ab+(b+c)*");
        decomposition.decompose(automata, automata.getTransitions().stream().findFirst().get());

        Node node0 = automata.getStartNode();
        Node node1 = automata.getEndNodes().stream().findFirst().get();

        assertThat(automata.getNodes().size(), is(2));
        assertThat(automata.getTransitions().size(), is(2));
        assertThat(automata.getTransitions(), hasItem(new Transition(node0, node1, "ab")));
        assertThat(automata.getTransitions(), hasItem(new Transition(node0, node1, "(b+c)*")));
    }

    @Test
    public void testDecompose2() throws Exception {
        Automata automata = createAutomata("a*b+b*a");
        decomposition.decompose(automata, automata.getTransitions().stream().findFirst().get());

        Node node0 = automata.getStartNode();
        Node node1 = automata.getEndNodes().stream().findFirst().get();

        assertThat(automata.getNodes().size(), is(2));
        assertThat(automata.getTransitions().size(), is(2));
        assertThat(automata.getTransitions(), hasItem(new Transition(node0, node1, "a*b")));
        assertThat(automata.getTransitions(), hasItem(new Transition(node0, node1, "b*a")));
    }

}
