package br.ita.automatos;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.base.Preconditions;

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

    public Automata(String automataDescription) {
        Preconditions.checkNotNull(automataDescription, "the automata description cannot be null");
        ByteArrayInputStream in = new ByteArrayInputStream(automataDescription.getBytes());
        try {
            fromStream(in);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public Automata() {
        createDefaultNodes();
    }

    public static Automata fromRegex(String regex) {
        Automata automata = new Automata();
        new AutomataDecomposition(automata).decompose(regex);
        return automata;
    }

    public String toRegex() {
        return new AutomataComposition(this).compose();
    }

    public void makeEndNode(Node node) {
        Preconditions.checkArgument(nodes().contains(node),
                "The provided node does not exist in this automata");

        this.endNodes.add(node);
    }

    public Collection<Node> nodes() {
        return this.nodes.values();
    }

    public Set<Transition> getTransitions() {
        return this.nodes.values().stream().flatMap(node -> node.getTransitions().stream())
                .collect(Collectors.toSet());
    }

    public Optional<Transition> transition(String source, String sink, String input) {
        Optional<Node> node = node(source);
        if (node.isPresent()) {
            return node.get()
                    .getTransitionsForInput(input).stream()
                    .filter(t -> t.getNextNode().getId().equals(sink))
                    .findFirst();
        } else {
            return Optional.empty();
        }
    }

    public Set<Transition> transition(Node a, Node b) {
        Preconditions.checkNotNull(a, "the node A cannot be null");
        Preconditions.checkNotNull(b, "the node B cannot be null");
        return a.getTransitions().stream().filter(t -> t.getNextNode().equals(b))
                .collect(Collectors.toSet());

    }

    public Node getStartNode() {
        return startNode;
    }

    public Set<Node> getEndNodes() {
        return Collections.unmodifiableSet(endNodes);
    }

    public Optional<Node> node(String id) {
        Preconditions.checkNotNull(id, "The node id cannot be null");
        return Optional.ofNullable(this.nodes.get(id));
    }

    public void removeEpsilonTransitions() {
        new EpsilonRemover(this).removeEpsilonTransitions();
    }

    public boolean accept(String language) {
        if (language == null || language.isEmpty()) return false;

        StringComputation computation = new StringComputation(this);

        Set<Node> finalStates = computation.compute(language);

        return !Collections.disjoint(this.endNodes, finalStates);
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
        return node(id).orElseGet(
                () -> createNode(id)
        );

    }

    public void removeNode(Node anyNode) {
    	Preconditions.checkNotNull(anyNode, "The node to be removed cannot be null");
    	
    	this.getTransitions().stream().filter(t->t.getNextNode().equals(anyNode))
    		.forEach(t -> t.getSourceNode().removeTransition(t));
        this.nodes.remove(anyNode.getId());
    }

    public Node createNode() {
        int nodeCount = nodes.size();
        while (nodes.containsKey(String.valueOf(nodeCount))) {
            nodeCount++;
        }
        Node node = new Node(String.valueOf(nodeCount));
        this.nodes.put(node.getId(), node);
        return node;
    }

    public boolean isStartNode(Node node) {
        return this.startNode.equals(node);
    }

    public boolean isEndNode(Node node) {
        return this.endNodes.contains(node);
    }

    public Node createNode(String id) {
        Node node = new Node(id);
        this.nodes.put(id, node);
        return node;
    }

    public Set<Node> nodes(String... ids) {
        if (ids != null) {
            return this.nodes.values().stream().filter(n ->
                    Stream.of(ids).anyMatch(id -> n.getId().equals(id))
            ).collect(Collectors.toSet());
        }
        return Collections.emptySet();
    }

    public Node[] nodesAsArray(String... ids) {
        return this.nodes(ids).toArray(new Node[0]);
    }
    
    public Node newStartState(String startState) {
		Node node = this.createNode(startState);
		node.addTransition(Alphabet.EPSILON+"", this.startNode);
		this.startNode = node;
		return node;
	}

	public Node newFinalState(String id) {
		Node node = this.createNode(id);
		for (Node finalNode : endNodes) {
			finalNode.addTransition(Alphabet.EPSILON+"", node);			
		}
		this.endNodes.clear();
		this.endNodes.add(node);
		return node;		
	}
    
    public String toGraphViz() {
		Node startNode = this.getStartNode();
		StringBuilder sb = new StringBuilder();
		if (startNode != null) {
			sb.append("digraph g {\n");
			sb.append("\trankdir=LR;\n");
			sb.append("\tsize=\"8,5\";\n");
			sb.append("\tnode [shape = doublecircle]; ");
			for (Node node : this.getEndNodes()) {
				sb.append(node.getId()).append(" ");
			}
			sb.append(";\n");
			sb.append("\tnode [shape = circle];\n");

			for (Node node : this.nodes()) {
				Map<String, StringBuilder> transitions = new LinkedHashMap<>();
				for (Transition transition : node.getTransitions()) {
					String nextNodeId = transition.getNextNode().getId();
					StringBuilder label = transitions.getOrDefault(nextNodeId, new StringBuilder());
					if (label.length() > 0)
						label.append(",");
					label.append(transition.getInput());
					transitions.put(nextNodeId, label);
				}
				for (Entry<String, StringBuilder> entrySet : transitions.entrySet()) {
					sb.append("\t").append(node.getId()).append(" -> ")
					.append(entrySet.getKey())
					.append(String.format(" [ label = \"%s\" ];\n",
							entrySet.getValue().toString()));
				}
			}

			sb.append("}");
		}
		return sb.toString();
	}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((endNodes == null) ? 0 : endNodes.hashCode());
        result = prime * result + ((nodes == null) ? 0 : nodes.hashCode());
        result = prime * result + ((startNode == null) ? 0 : startNode.hashCode());
        result = prime * result + getTransitions().hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Automata other = (Automata) obj;
        if (endNodes == null) {
            if (other.endNodes != null)
                return false;
        } else if (!endNodes.equals(other.endNodes))
            return false;
        if (nodes == null) {
            if (other.nodes != null)
                return false;
        } else if (!nodes.equals(other.nodes))
            return false;
        if (startNode == null) {
            if (other.startNode != null)
                return false;
        } else if (!startNode.equals(other.startNode))
            return false;
        return getTransitions().equals(other.getTransitions());
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Node n : this.nodes.values()) {
            builder.append(n.getTransitionsAsString());
        }
        return builder.toString();
    }	
}
