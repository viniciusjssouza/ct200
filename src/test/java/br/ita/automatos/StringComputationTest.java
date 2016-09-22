package br.ita.automatos;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

/**
 * Created by vinicius on 20/09/16.
 */
public class StringComputationTest  {

    @Test
    public void testCompute1() throws Exception {
    	Automata automata = TestData.automata1();
    	StringComputation computation = new StringComputation(automata);
    	
    	Set<Node> nodes = computation.compute("ab");
    	assertHasNodes(automata, nodes, "2", "3", "4");
    	
    	nodes = computation.compute("abba");
    	assertHasNodes(automata, nodes, "4", "2", "6", "1");
    }
    
    @Test
    public void testCompute2() throws Exception {
    	Automata automata = TestData.automata2();
    	
    	StringComputation computation = new StringComputation(automata);
    	
    	Set<Node> nodes = computation.compute("abb");
    	assertThat(nodes, is(empty()));
    	
    	nodes = computation.compute("abba");
    	assertThat(nodes, is(empty()));
    	
    	nodes = computation.compute("ab");
    	assertHasNodes(automata, nodes, "1", "2");
    }

	private void assertHasNodes(Automata automata, Set<Node> nodes, String... ids) {
		assertThat(nodes, containsInAnyOrder(
			Stream.of(ids).map(id -> automata.node(id).get())
				.collect(Collectors.toList()).toArray(new Node[0])				
		));
	}
    
}
