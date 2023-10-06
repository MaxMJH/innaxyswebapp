package com.mjh.innaxyswebapp.model;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
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
	private Timer timer;
	
	/*---- Constructor ----*/
	public Graph(List<Edge> edges, List<Node> nodes) {
		this.edges = edges;
		this.nodes = nodes;
		this.adjacencyList = new HashMap<>();
		this.timer = new Timer();
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
	
	public Map<Node, Node> calculateShortestPaths(Node source) {
		// Set the start time.
		this.timer.addStartTime(System.nanoTime());
		
		// Map to store all nodes and their distances from the source.
		Map<Node, Integer> distances = new HashMap<>(this.nodes.size());
		
		// Map to store all nodes leading up to the source.
		Map<Node, Node> previous = new HashMap<>(this.nodes.size());
		
		// Priority queue to store nodes that need to have their shortest distance checked from source.
		Queue<Node> queue = new PriorityQueue<>(new Comparator<Node>() {
			// Orders the queue based on the smallest distance from source.
			@Override
			public int compare(Node o1, Node o2) {
				int o1Distance = distances.get(o1);
				int o2Distance = distances.get(o2);
				return Integer.compare(o1Distance, o2Distance);
			}
		});
		
		// Iterate through each node in the graph.
		for(Node node : this.nodes) {
			// Set their distance to the highest possible value to signify infinity.
			distances.put(node, Integer.MAX_VALUE);
			
			// Set their place in nodes leading up to source.
			previous.put(node, null);
			
			// Add the node to the queue.
			queue.add(node);
		}
		
		// Set the source node's distance to 0.
		distances.put(source, 0);
		
		// Iterate through the queue until it is empty (all nodes have their distances calculated from source).
		// Effectively calculates the shortest path from the source node to all other nodes.
		while(!queue.isEmpty()) {
			// Get the smallest distance node from the queue.
			Node currentNode = queue.poll();
			
			// Get adjacent nodes from the current node (via the adjacency list).
			for(Node neighbour : this.adjacencyList.get(currentNode).keySet()) {
				// Calculate the distance from the current node to the neighbouring node.
				int length = distances.get(currentNode) + this.adjacencyList.get(currentNode).get(neighbour);
				
				// Check to see if the calculated length is smaller than the current stored length (i.e. set the shortest path).
				if(length < distances.get(neighbour)) {
					// Set the distance from the current node to the current neighbour node.
					distances.put(neighbour, length);
					
					// Add to the previous visited nodes map.
					previous.put(neighbour, currentNode);
					
					// Update the shortest path for the current neighbouring node in the queue.
					queue.remove(neighbour);
					queue.add(neighbour);
				}	
			}
		}

		// Set the stop time.
		this.timer.addStopTime(System.nanoTime());
		
		return previous;
	}
	
	public List<Node> getShortestPath(Map<Node, Node> shortestPaths, Node source, Node target) {
		// Set the start time.
		this.timer.addStartTime(System.nanoTime());
		
		// List to store the shortest path from source to target.
		List<Node> path = new ArrayList<>();
				
		// Check to see if the target node exists in previously shortest paths from source map, or is equal to source.
		if(shortestPaths.get(target) != null || target == source) {
			// Iterate through each node until the source is reached (null after source).
			while(target != null) {
				// Add the node to the path, then get the next shortest node until the source is reached.
				path.add(target);
				target = shortestPaths.get(target);
			}
		}
				
		// As the algorithm returns the path from target to source, reverse the list.
		Collections.reverse(path);
				
		// Set the stop time.
		this.timer.addStopTime(System.nanoTime());
		
		// Return the shortest path from source to target.
		return path;
	}
	
	public ShortestPath getPathMetadata(List<Node> path) {
		// Set variables to find the total distance and total edges for each node in the shortest path.
		int totalDistance = 0;
		int totalEdges = 0;
		
		// Iterate through each node in the shortest path, finding their overall total distance and edges.
		for(int i = 0, j = 1; i < path.size() - 1; i++, j++) {
			// Get current node and its adjacent neighbour.
			Node currentNode = path.get(i);
			Node neighbourNode = path.get(j);
			
			// Use the adjacency list to find the distance between the two nodes.
			totalDistance += this.adjacencyList.get(currentNode).get(neighbourNode);
			
			// As two nodes are being processed at once, assume an edge.
			totalEdges++;
		}
		
		// Return the shortest path from source to target.
		return new ShortestPath(path, totalDistance, totalEdges, this.timer.getTotalTime());
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
	
	public Timer getTimer() {
		return this.timer;
	}
	
	public void setTimer(Timer timer) {
		this.timer = timer;
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
