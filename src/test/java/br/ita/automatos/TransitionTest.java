package br.ita.automatos;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class TransitionTest {

    @Test
    public void testToString() throws Exception {
        Transition t1 = new Transition(new Node("10"), new Node("20"), "ab+(b+c)*");
        assertThat(t1.toString(), is("10, 20, ab+(b+c)*"));
    }
}