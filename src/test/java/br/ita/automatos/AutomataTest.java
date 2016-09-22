package br.ita.automatos;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.containsInAnyOrder;

import java.io.ByteArrayInputStream;

/**
 * Created by vinicius on 19/09/16.
 */
public class AutomataTest {

	@Test
	public void removeEpsilonAutomata1() throws Exception {
		Automata automata = TestData.automata1();

		automata.removeEpsilonTransitions();

		assertThat(automata.getTransitions(), hasItem(automata.transition("0", "4", "a").get()));
		assertThat(automata.getTransitions(), hasItem(automata.transition("0", "3", "b").get()));
		assertThat(automata.getTransitions(), hasItem(automata.transition("4", "3", "b").get()));
		assertThat(automata.getTransitions(), hasItem(automata.transition("6", "1", "b").get()));

		assertThat(automata.getTransitions().stream().filter(Transition::isEpsilonTransition).count(), is(0l));
		assertThat(automata.getTransitions().size(), is(18));
		assertThat(automata.getEndNodes(), containsInAnyOrder(automata.nodesAsArray("5", "6", "1")));
	}

	@Test
	public void removeEpsilonAutomata2() throws Exception {
		Automata automata = TestData.automata2();

		automata.removeEpsilonTransitions();

		assertThat(automata.getTransitions(), hasItem(automata.transition("0", "3", "a").get()));
		assertThat(automata.getTransitions(), hasItem(automata.transition("3", "1", "b").get()));
		assertThat(automata.getTransitions(), hasItem(automata.transition("3", "1", "c").get()));

		assertThat(automata.getTransitions().stream().filter(Transition::isEpsilonTransition).count(), is(0l));
		assertThat(automata.getTransitions().size(), is(6));
		assertThat(automata.getEndNodes(), containsInAnyOrder(automata.nodesAsArray("2", "1", "0")));
	}

	@Test
	public void removeEpsilonAutomata3() throws Exception {
		Automata automata = TestData.automata3();

		automata.removeEpsilonTransitions();

		assertThat(automata.getTransitions(), hasItem(automata.transition("0", "4", "a").get()));
		assertThat(automata.getTransitions(), hasItem(automata.transition("0", "5", "b").get()));
		assertThat(automata.getTransitions(), hasItem(automata.transition("0", "1", "b").get()));

		assertThat(automata.getTransitions().size(), is(12));
		assertThat(automata.getEndNodes(), containsInAnyOrder(automata.nodesAsArray("1")));
	}

	@Test
	public void acceptStringTest1() throws Exception {
		Automata automata = TestData.automata1();
		
		assertThat(automata.accept("ab"), is(false));
		assertThat(automata.accept("abb"), is(true));
		assertThat(automata.accept("bba"), is(true));
		assertThat(automata.accept("abba"), is(true));		
		
	}
	
	@Test
	public void acceptStringTest2() throws Exception {
		Automata automata = TestData.automata2();
		
		assertThat(automata.accept("ab"), is(true));
		assertThat(automata.accept("abb"), is(false));
		assertThat(automata.accept("bba"), is(false));
		assertThat(automata.accept("abba"), is(false));		
		
	}
	
	@Test
	public void acceptStringTest3() throws Exception {
		Automata automata = TestData.automata3();
		
		assertThat(automata.accept("ab"), is(true));
		assertThat(automata.accept("abb"), is(false));
		assertThat(automata.accept("bba"), is(true));
		assertThat(automata.accept("abba"), is(false));		
	}
	
	@Test
	public void acceptStringTest4() throws Exception {
		Automata automata = TestData.automata4();
		
		assertThat(automata.accept("ab"), is(true));
		assertThat(automata.accept("abb"), is(true));
		assertThat(automata.accept("bba"), is(false));
		assertThat(automata.accept("abba"), is(false));		
	}
	
	@Test
	public void createAutomtaFromInputStream() throws Exception {
		 String input = "0, 1, ab\n"
				+ "0, 2, a\n"
				+ "2, 1, b\n"
				+ "0, 3, &\n"
		 		+ "3, 1, &";
		 Automata automata = new Automata(new ByteArrayInputStream(input.getBytes()));
		 
		 Node zero = automata.node("0").get();
		 Node one = automata.node("1").get();
		 Node three = automata.node("3").get();
		 
		 assertThat(automata.getStartNode(), is(zero));
		 assertThat(automata.getEndNodes(), hasItem(one));
		 
		 assertThat(automata.nodes().size(), is(4));
		 assertThat(zero.getTransitions().size(), is(3));
		 assertThat(zero.getTransitions(), hasItem(new Transition(zero, three, "&")));
		 assertThat(zero.getTransitions(), hasItem(new Transition(zero, one, "ab")));
	 }
	
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