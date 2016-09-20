package br.ita.automatos;

import com.google.common.base.Preconditions;

import java.io.Serializable;
import java.util.Objects;

public class Transition implements Serializable {

    private String input;
    private Node sourceNode;
    private Node nextNode;

    public Transition(Node sourceNode, Node nextNode, String input) {
        Preconditions.checkNotNull(sourceNode, "The source node cannot be null");
        Preconditions.checkNotNull(nextNode, "The next node cannot be null");
        Preconditions.checkNotNull(input, "The transition input cannot be null");

        this.input = input;
        this.sourceNode = sourceNode;
        this.nextNode = nextNode;
    }

    public Node getSourceNode() {
        return sourceNode;
    }

    public Node getNextNode() {
        return nextNode;
    }

    public String getInput() {
        return input;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transition that = (Transition) o;
        return Objects.equals(input, that.input) &&
                Objects.equals(sourceNode, that.sourceNode) &&
                Objects.equals(nextNode, that.nextNode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(input, sourceNode, nextNode);
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s", sourceNode.getId(), nextNode.getId(), input);
    }
}
