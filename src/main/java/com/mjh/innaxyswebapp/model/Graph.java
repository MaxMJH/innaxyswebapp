package com.mjh.innaxyswebapp.model;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

// TODO Add JavaDoc comments.
@Component
public class Graph {
	/*---- Fields ----*/
	private Map<Node, List<Node>> adjacencyList;
	
	/*---- Constructor ----*/
	public Graph(List<Edge> edges) {
		this.adjacencyList = new HashMap<>();
		
		// Iterate through each edge to create a graph's adjacency list.
		for(Edge edge : edges) {
			// Get the current edge's source and target.
			Node source = edge.getSource();
			Node target = edge.getTarget();
			
			// Check if the source is contained within the map.
			if(this.adjacencyList.containsKey(source)) {
				// If so, add the target to the existing source.
				this.adjacencyList.get(source).add(target);
			} else {
				// If not, add the source to the map.
				this.adjacencyList.put(source, new ArrayList<>());
				this.adjacencyList.get(source).add(target);
			}
			
			// As the graph is undirected, also need to add the source to the target node.
			// Check if the target is contained within the map.
			if(this.adjacencyList.containsKey(target)) {
				// If so, add the source to the existing target.
				this.adjacencyList.get(target).add(source);
			} else {
				// If not, add the target to the map.
				this.adjacencyList.put(target, new ArrayList<>());
				this.adjacencyList.get(target).add(source);
			}
		}
	}
	
	/*---- Getter and Setter ----*/
	public Map<Node, List<Node>> getAdjacencyList() {
		return this.adjacencyList;
	}
	
	public void setAdjacencyList(Map<Node, List<Node>> adjacencyList) {
		this.adjacencyList = adjacencyList;
	}

	/*---- Overridden Methods ----*/
	@Override
	public int hashCode() {
		return Objects.hash(this.adjacencyList);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (!(obj instanceof Graph)) {
			return false;
		}
		
		Graph other = (Graph) obj;
		
		return Objects.equals(this.adjacencyList, other.adjacencyList);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		builder.append("Adjacency List:\n");
		
		for(Node source : this.adjacencyList.keySet()) {
			builder.append(source.getName());
			builder.append(" --> ");
			
			for(Node target : this.adjacencyList.get(source)) {
				builder.append(target.getName());
				builder.append(", ");
			}
			builder.deleteCharAt(builder.lastIndexOf(", "));
			builder.append("\n");
		}
		
		return builder.toString();
	}
}
