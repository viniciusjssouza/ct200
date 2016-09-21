package br.ita.automatos;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by vinicius on 19/09/16.
 */
public class NodeTest {

    @Test
    public void testAddTransition() throws Exception {
        Node node1 = new Node("q1");
        Node node2 = new Node("q2");
        node1.addTransition("a", node1);
        node1.addTransition("b", node2);

        node2.addTransition("b", node1);
        node2.addTransition("a", node2);
        node2.addTransition(String.valueOf(Alphabet.EPSILON), node2);
        node2.addTransition(Alphabet.EPSILON + "", node2); // try to repeat transition. It must not be counted.

        assertThat(node1.getTransitions().size(), is(2));
        assertThat(node2.getTransitions().size(), is(3));

        assertThat(node1.getTransitions(), hasItem(new Transition(node1, node2, "b")));
    }

    @Test
    public void testGetTransitionsAsString() throws Exception {
        Node node1 = new Node("q1");
        Node node2 = new Node("q2");
        node1.addTransition("a", node1);
        node1.addTransition("b", node2);

        assertThat(node1.getTransitionsAsString(), containsString("q1, q2, b"));
        assertThat(node1.getTransitionsAsString(), containsString("q1, q1, a"));
    }
}
