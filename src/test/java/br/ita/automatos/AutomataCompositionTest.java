package br.ita.automatos;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by vinicius on 21/09/16.
 */
public class AutomataCompositionTest {

    @Test
    public void testComposeAutomata1() throws Exception {
        Automata automata = TestData.automata1();
        automata.toRegex();

        System.out.println(automata);
    }
}