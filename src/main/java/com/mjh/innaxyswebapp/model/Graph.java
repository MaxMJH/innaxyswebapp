package com.mjh.innaxyswebapp.model;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;

// TODO Add JavaDoc comments.
@Component
public class Graph {
	/*---- Fields ----*/
	private List<Edge> edges;
	private List<Node> nodes;
	private Map<Node, Map<Node, Integer>> adjacencyList;
	
	/*---- Constructor ----*/
	public Graph(List<Edge> edges, List<Node> nodes) {
		this.edges = edges;
		this.nodes = nodes;
		this.adjacencyList = new HashMap<>();
		this.createAdjacencyList();
	}
	
	/*---- Methods ----*/
	public void createAdjacencyList() {
		// Iterate through each edge to create a graph's adjacency list.
		for(Edge edge : edges) {
			// Get the current edge's source and target.
			Node source = edge.getSource();
			Node target = edge.getTarget();
			
			// Check if the source is contained within the map.
			if(this.adjacencyList.containsKey(source)) {
				// If so, add the target to the existing source.
				this.adjacencyList.get(source).put(target, edge.getDistance());
			} else {
				// If not, add the source to the map.
				this.adjacencyList.put(source, new HashMap<>());
				this.adjacencyList.get(source).put(target, edge.getDistance());
			}
					
			// As the graph is undirected, also need to add the source to the target node.
			// Check if the target is contained within the map.
			if(this.adjacencyList.containsKey(target)) {
				// If so, add the source to the existing target.
				this.adjacencyList.get(target).put(source, edge.getDistance());
			} else {
				// If not, add the target to the map.
				this.adjacencyList.put(target, new HashMap<>());
				this.adjacencyList.get(target).put(source, edge.getDistance());
			}
		}
	}
	
	public List<Node> shortestPath(Node source, Node target) {
		Map<Node, Integer> distances = new HashMap<>(this.nodes.size());
		Map<Node, Node> previous = new HashMap<>(this.nodes.size());
		Queue<Node> queue = new PriorityQueue<>(new Comparator<Node>() {
			@Override
			public int compare(Node o1, Node o2) {
				int o1Distance = distances.get(o1);
				int o2Distance = distances.get(o2);
				return Integer.compare(o1Distance, o2Distance);
			}
		});
		
		for(Node node : this.nodes) {
			distances.put(node, Integer.MAX_VALUE);
			previous.put(node, null);
			queue.add(node);
		}
		
		distances.put(source, 0);
		
		while(!queue.isEmpty()) {
			Node currentNode = queue.poll();
			
			for(Node neighbour : this.adjacencyList.get(currentNode).keySet()) {
				int length = distances.get(currentNode) + this.adjacencyList.get(currentNode).get(neighbour);
				
				if(length < distances.get(neighbour)) {
					distances.put(neighbour, length);
					previous.put(neighbour, currentNode);
					queue.remove(neighbour);
					queue.add(neighbour);
				}	
			}
		}
		
		List<Node> path = new ArrayList<>();
		
		if(previous.get(target) != null || target == source) {
			while(target != null) {
				path.add(target);
				target = previous.get(target);
			}
		}
		
		return path;
	}
	
	
	/*---- Getters and Setters ----*/
	public List<Edge> getEdges() {
		return this.edges;
	}
	
	public void setEdges(List<Edge> edges) {
		this.edges = edges;
	}
	
	public List<Node> getNodes() {
		return this.nodes;
	}
	
	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}
	
	public Map<Node, Map<Node, Integer>> getAdjacencyList() {
		return this.adjacencyList;
	}
	
	public void setAdjacencyList(Map<Node, Map<Node, Integer>> adjacencyList) {
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
			builder.append(String.format("%s --> ", source.getName()));
			
			for(Node target : this.adjacencyList.get(source).keySet()) {			
				builder.append(String.format("%s (%d), ", target.getName(), this.adjacencyList.get(source).get(target)));
			}
			builder.deleteCharAt(builder.lastIndexOf(", "));
			builder.append("\n");
		}
		
		return builder.toString();
	}
}
