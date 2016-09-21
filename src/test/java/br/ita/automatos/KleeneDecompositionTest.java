package br.ita.automatos;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by vinicius on 20/09/16.
 */
public class KleeneDecompositionTest extends TransitionDecompositionTest {

    protected KleeneDecomposition decomposition = new KleeneDecomposition();

    @Test
    public void testIsApplicable() throws Exception {
        Transition t1 = new Transition(new Node(), new Node(), "aba");
        Transition t2 = new Transition(new Node(), new Node(), "ab+(b+c)*");
        Transition t3 = new Transition(new Node(), new Node(), "(a+b)*bb(b+a)*");
        Transition t4 = new Transition(new Node(), new Node(), "a*b+b*a");
        Transition t5 = new Transition(new Node(), new Node(), "a*b*c*");

        assertThat(decomposition.isApplicable(t1), is(false));
        assertThat(decomposition.isApplicable(t2), is(true));
        assertThat(decomposition.isApplicable(t3), is(true));
        assertThat(decomposition.isApplicable(t4), is(true));
        assertThat(decomposition.isApplicable(t5), is(true));

    }

    @Test
    public void testDecompose1() throws Exception {
        Automata automata = createAutomata("(a+b)*bb(b+a)*");
        decomposition.decompose(automata, automata.getTransitions().stream().findFirst().get());

        Node node0 = automata.getStartNode();
        Node node1 = automata.getEndNodes().stream().findFirst().get();
        Node node2 = automata.getNodeById("2").get();
        Node node3 = automata.getNodeById("3").get();

        assertThat(automata.getNodes().size(), is(4));
        assertThat(automata.getTransitions().size(), is(4));
        assertThat(automata.getTransitions(), hasItem(new Transition(node0, node2, Alphabet.EPSILON + "")));
        assertThat(automata.getTransitions(), hasItem(new Transition(node2, node2, "(a+b)")));
        assertThat(automata.getTransitions(), hasItem(new Transition(node2, node3, Alphabet.EPSILON + "")));
        assertThat(automata.getTransitions(), hasItem(new Transition(node3, node1, "bb(b+a)*")));
    }

    @Test
    public void testDecompose2() throws Exception {
        Automata automata = createAutomata("a*b+b*a");
        decomposition.decompose(automata, automata.getTransitions().stream().findFirst().get());

        Node node0 = automata.getStartNode();
        Node node1 = automata.getEndNodes().stream().findFirst().get();
        Node node2 = automata.getNodeById("2").get();
        Node node3 = automata.getNodeById("3").get();

        assertThat(automata.getNodes().size(), is(4));
        assertThat(automata.getTransitions().size(), is(4));
        assertThat(automata.getTransitions(), hasItem(new Transition(node0, node2, Alphabet.EPSILON + "")));
        assertThat(automata.getTransitions(), hasItem(new Transition(node2, node2, "a")));
        assertThat(automata.getTransitions(), hasItem(new Transition(node2, node3, Alphabet.EPSILON + "")));
        assertThat(automata.getTransitions(), hasItem(new Transition(node3, node1, "b+b*a")));
    }

    @Test
    public void testDecompose3() throws Exception {
        Automata automata = createAutomata("b+ca*");
        decomposition.decompose(automata, automata.getTransitions().stream().findFirst().get());

        Node node0 = automata.getStartNode();
        Node node1 = automata.getEndNodes().stream().findFirst().get();
        Node node2 = automata.getNodeById("2").get();

        assertThat(automata.getNodes().size(), is(3));
        assertThat(automata.getTransitions().size(), is(3));
        assertThat(automata.getTransitions(), hasItem(new Transition(node0, node2, Alphabet.EPSILON + "")));
        assertThat(automata.getTransitions(), hasItem(new Transition(node2, node2, "b+ca")));
        assertThat(automata.getTransitions(), hasItem(new Transition(node2, node1, Alphabet.EPSILON + "")));
    }

    @Test
    public void testDecompose4() throws Exception {
        Automata automata = createAutomata("a*");
        decomposition.decompose(automata, automata.getTransitions().stream().findFirst().get());

        Node node0 = automata.getStartNode();
        Node node1 = automata.getEndNodes().stream().findFirst().get();
        Node node2 = automata.getNodeById("2").get();

        assertThat(automata.getNodes().size(), is(3));
        assertThat(automata.getTransitions().size(), is(3));
        assertThat(automata.getTransitions(), hasItem(new Transition(node0, node2, Alphabet.EPSILON + "")));
        assertThat(automata.getTransitions(), hasItem(new Transition(node2, node2, "a")));
        assertThat(automata.getTransitions(), hasItem(new Transition(node2, node1, Alphabet.EPSILON + "")));
    }
}
