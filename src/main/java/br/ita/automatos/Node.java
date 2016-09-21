package br.ita.automatos;

import com.google.common.base.Preconditions;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class Node implements Serializable {

    private String id;
    private Set<Transition> transitions = new HashSet<>();

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
    
    public void removeTransition(Transition transition) {
		this.transitions.remove(transition);		
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

    public Set<Transition> getTransitionsForInput(String input) {
    	if (input == null) 
    		return Collections.emptySet();
    	return transitions.stream().filter(t ->	t.isEpsilonTransition() || t.getInput().equals(input))
    			.collect(Collectors.toSet());
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
