package br.ita.automatos;

import org.junit.Test;

import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

/**
 * Created by vinicius on 21/09/16.
 */
public class EpsilonClosureTest {

    @Test
    public void computeClosureAutomata1() throws Exception {
        Automata automata = TestData.automata1();
        EpsilonClosure closure = new EpsilonClosure(automata);

        Set<Node> result = closure.compute(automata.node("0").get());
        assertThat(result, containsInAnyOrder(automata.nodesAsArray("0", "4", "2")));

        result = closure.compute(automata.node("3").get());
        assertThat(result, containsInAnyOrder(automata.nodesAsArray("3")));

        result = closure.compute(automata.node("5").get());
        assertThat(result, containsInAnyOrder(automata.nodesAsArray("5", "6", "1")));
    }

    @Test
    public void computeClosureAutomata2() throws Exception {
        Automata automata = TestData.automata2();
        EpsilonClosure closure = new EpsilonClosure(automata);

        Set<Node> result = closure.compute(automata.node("0").get());
        assertThat(result, containsInAnyOrder(automata.nodesAsArray("0", "1", "2")));
    }

    @Test
    public void computeClosureAutomata3() throws Exception {
        Automata automata = TestData.automata3();
        EpsilonClosure closure = new EpsilonClosure(automata);

        Set<Node> result = closure.compute(automata.node("0").get());
        assertThat(result, containsInAnyOrder(automata.nodesAsArray("0", "2", "3", "4", "5")));

        result = closure.compute(automata.node("5").get());
        assertThat(result, containsInAnyOrder(automata.nodesAsArray("3", "5")));

    }
}