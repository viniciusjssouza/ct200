package br.ita.automatos;

import com.google.common.base.Preconditions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Automata {

	public static final String DEFAULT_END_NODE = "1";
	public static final String DEFAULT_START_NODE = "0";
	
	private Map<String, Node> nodes = new HashMap<>();
	private Node startNode;
	private Set<Node> endNodes = new HashSet<>();

	public Automata(Node start, Node... end) {
		Preconditions.checkNotNull(start, "The start node cannot be null");
		Preconditions.checkNotNull(end, "The end nodes cannot be null");

		this.startNode = start;
		this.endNodes.addAll(Stream.of(end).collect(Collectors.toSet()));
		nodes = (Stream.concat(Stream.of(start), Stream.of(end))
				.collect(Collectors.toMap(node -> node.getId(), node -> node))
		);
	}

	public Automata(InputStream inputStream) throws IOException {
		fromStream(inputStream);
	}

	public Automata(String regex) {
		new AutomataDecomposition(this).decompose(regex);
	}

	public Collection<Node> getNodes() {
		return this.nodes.values();
	}
	
	public Set<Transition> getTransitions() {
		return this.nodes.values().stream().flatMap(node -> node.getTransitions().stream())
				.collect(Collectors.toSet());
	}
	
	public Node getStartNode() {
		return startNode;
	}
	
	public Set<Node> getEndNodes() {
		return Collections.unmodifiableSet(endNodes);
	}
	
	public Optional<Node> getNodeById(String id) {
		Preconditions.checkNotNull(id, "The node id cannot be null");		
		return Optional.ofNullable(this.nodes.get(id));
	}

	public boolean accept(String language) {
		// TODO exercicio 2
		throw new UnsupportedOperationException("TO BE IMPLEMENTED");
	}

	protected void fromStream(InputStream inputStream) throws IOException {
		Preconditions.checkNotNull(inputStream, "The input stream cannot be null");
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		createDefaultNodes();
		
		while (reader.ready()) {
			String line = reader.readLine();
			if (line != null && !line.isEmpty()) {
				this.parseLine(line);
			}
		}	
		
	}

	private void createDefaultNodes() {
		this.startNode = this.createNode(DEFAULT_START_NODE);
		this.endNodes = Stream.of(createNode(DEFAULT_END_NODE)).collect(Collectors.toSet());
	}

	private void parseLine(String line) throws IOException {
		String[] parts = line.split(",");
		
		if (parts.length != 3) {
			throw new IOException("Invalid transition description: \"" + line + "\"");
		}
		Node node = this.getOrCreateNode(parts[0].trim());
		node.addTransition(parts[2].trim(), getOrCreateNode(parts[1].trim()));
	}

	private Node getOrCreateNode(String id) {
		return getNodeById(id).orElseGet(
				() -> createNode(id)
		);
		
	}

	public Node createNode() {
		Node node = new Node();
		this.nodes.put(node.getId(), node);
		return node;
	}
	
	public Node createNode(String id) {
		Node node = new Node(id);
		this.nodes.put(id, node);
		return node;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (Node n : this.nodes.values()) {
			builder.append(n.getTransitionsAsString());
			builder.append(System.lineSeparator());
		}
		return builder.toString();
	}
}
