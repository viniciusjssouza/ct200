package br.ita.automatos;

import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;


/**
 * Created by vinicius on 21/09/16.
 */
public class AutomataCompositionTest {

    @Test
    public void testComposeAutomata1() throws Exception {
        Automata automata = TestData.automata1();
        assertThat(automata.toRegex(),  is("((b+a)*bb)(b+a)*"));
    }
    
    @Test
    public void testComposeAutomata2() throws Exception {
        Automata automata = TestData.automata2();
        String regex = automata.toRegex();
        assertThat(regex,  is("&+a((c+b)a)*(c+b)"));
    }
    
    @Test
    public void testComposeAutomata3() throws Exception {
        Automata automata = TestData.automata3();
        String regex = automata.toRegex();
        assertThat(regex,  is("a*b+b*a"));
    }
    
    @Test
    public void testComposeAutomata4() throws Exception {
        Automata automata = TestData.automata4();
        String regex = automata.toRegex();
        assertThat(regex,  is("a*b*c*"));
    }
}