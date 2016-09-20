package br.ita.automatos;

import com.google.common.base.Preconditions;

import java.io.Serializable;
import java.util.*;

public class Node implements Serializable {

    private static int counter = 0;

    private String id;
    private Set<Transition> transitions = new HashSet<>();

    public Node() {
        this(generateIntegerAscendingId());
    }

    private synchronized static String generateIntegerAscendingId() {
        String id = String.valueOf(counter);
        counter++;
        return id;
    }

    public Node(String id) {
        Preconditions.checkNotNull(id, "The node id cannot be null");
        Preconditions.checkArgument(!id.isEmpty(), "The node id cannot be empty");

        this.id = id;
    }

    public String getId() {
        return id;
    }

    public Node addTransition(String input, Node nextNode) {
        transitions.add(new Transition(this, nextNode, input));
        return this;
    }

    public Set<Transition> getTransitions() {
        return Collections.unmodifiableSet(transitions);
    }

    public String getTransitionsAsString() {
        StringBuilder sb = new StringBuilder();
        for (Transition t : this.transitions) {
            sb.append(t.toString());
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(id, node.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Node " + this.id;
    }
}
