package br.ita.automatos;

import com.google.common.base.Preconditions;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Automata {

    private Set<Node> nodes = new HashSet<>();
    private Node start;
    private Node end;

    private Automata() {}

    public Automata(Node start, Node end) {
        Preconditions.checkNotNull(start, "The start node cannot be null");
        Preconditions.checkNotNull(end, "The end node cannot be null");

        this.start = start;
        this.end = end;
        nodes.addAll(Stream.of(start, end).collect(Collectors.toSet()));
    }

    public Automata(InputStream inputStream) throws  IOException {
        fromStream(inputStream);
    }

    public Automata(String sourceNode, String endNode, String regex) {
        fromRegex(sourceNode, endNode, regex);
    }

    public Node getStart() {
        return start;
    }

    public Node getEnd() {
        return end;
    }

    public boolean accept(String language) {
        // TODO exercicio 2
        throw new UnsupportedOperationException("TO BE IMPLEMENTED");
    }

    protected Automata fromStream(InputStream inputStream) throws IOException {
        throw new UnsupportedOperationException("TO BE IMPLEMENTED");
    }

    protected Automata fromRegex(String sourceNode, String endNode, String regex) {
        // TODO exercicio 1
        throw new UnsupportedOperationException("TO BE IMPLEMENTED");
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Node n : this.nodes) {
            builder.append(n.getTransitionsAsString());
            builder.append(System.lineSeparator());
        }
        return builder.toString();
    }
}
