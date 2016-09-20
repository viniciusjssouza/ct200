package br.ita.automatos;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

/**
 * Created by vinicius on 19/09/16.
 */
public class AutomataTest {

    @Test
    public void testToString() throws Exception {
        Automata a = createSimpleAutomata();

        assertThat(a.toString(), containsString("q1, q2, b"));
        assertThat(a.toString(), containsString("q1, q1, a"));
    }

    private Automata createSimpleAutomata() {
        Node node1 = new Node("q1");
        Node node2 = new Node("q2");
        node1.addTransition("a", node1);
        node1.addTransition("b", node2);

        return new Automata(node1, node2);
    }

}