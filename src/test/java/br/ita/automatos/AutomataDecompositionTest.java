package br.ita.automatos;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

/**
 * Created by vinicius on 20/09/16.
 */
public class AutomataDecompositionTest  {

    @Test
    public void testDecompose1() throws Exception {
        Automata automata = Automata.fromRegex("aba");        
        Automata expected = new Automata(
        	"0, 2, a\n 2, 3, b\n 3, 1, a"        		
        );        
        assertThat(automata, is(expected));
    }

    @Test
    public void testDecompose2() throws Exception {
    	Automata automata = Automata.fromRegex("(a+b)*bb(b+a)*");        
        Automata expected = TestData.automata1();
        assertThat(automata, is(equalTo(expected)));
    }
    
    @Test
    public void testDecompose3() throws Exception {
    	Automata automata = Automata.fromRegex("(a(b+c))*");        
        Automata expected = TestData.automata2();
        assertThat(automata, is(expected));
    }
    
    @Test
    public void testDecompose4() throws Exception {
    	Automata automata = Automata.fromRegex("a*b+b*a");        
        Automata expected = TestData.automata3();
        assertThat(automata, is(expected));
    }
    
    @Test
    public void testDecompose5() throws Exception {
    	Automata automata = Automata.fromRegex("a*b*c*");        
        Automata expected = TestData.automata4();     
        assertThat(automata, is(expected));
    }
    
}
