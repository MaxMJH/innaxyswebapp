package com.mjh.innaxyswebapp.model;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Class which represents a graph. The graph itself contains both the edges and 
 * nodes which construct the entire graph. As it is typical for the graph to be
 * undirected and weighted, an adjacency list is also constructed on initialisation
 * so as to find the neighbouring nodes of each node within the graph itself. 
 * Various getters and setters are available so as to provide specific information 
 * pertaining to the graph. 
 * 
 * {@link com.mjh.innaxyswebapp.model.Node}, {@link com.mjh.innaxyswebapp.model.Edge},
 * and {@link com.mjh.innaxyswebapp.model.Timer} can be reviewed to find further
 * information regarding the classes.
 * 
 * @author	MaxMJH - MaxHarrisMJH@gmail.com
 * @version 1.0
 * @since 	02-10-2023
 */
@Component
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Graph {
	/*---- Fields ----*/
	/**
	 * Variable to store the a list of nodes in the graph.
	 */
	@JsonProperty
	private List<Node> nodes;
	
	/**
	 * Variable to store the a list of edges in the graph.
	 */
	@JsonProperty
	private List<Edge> edges;
	
	/**
	 * Variable to store the adjacency list of the graph.
	 */
	@JsonIgnore
	private Map<Node, Map<Node, Integer>> adjacencyList;
	
	/**
	 * Variable to store an instance of {@link com.mjh.innaxyswebapp.model.Timer}.
	 */
	@JsonIgnore
	private Timer timer;
	
	/*---- Constructor ----*/
	/**
	 * Overloaded constructor which initialises an edge with a source and target node 
	 * with a set distance.
	 * 
	 * Constructor which initialises a graph based on the passed edges and nodes. 
	 * This constructor will also generate an adjacency list.
	 * 
	 * @param nodes The list containing nodes.
	 * @param edges The list containing edges.
	 */
	public Graph(List<Node> nodes, List<Edge> edges) {
		this.nodes = nodes;
		this.edges = edges;
		this.adjacencyList = new HashMap<>();
		this.timer = new Timer();
		this.createAdjacencyList();
	}
	
	/*---- Methods ----*/
	/**
	 * This method creates an adjacency list pertaining to the graph. It is expected
	 * that each node within the graph will have their respected neighbours as well as
	 * their distances.
	 */
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
	
	/**
	 * This method calculates the shortest path from the source to all other
	 * nodes within the graph. This uses Dijkstra's Shortest Path algorithm to determine
	 * the shortest paths based on the source. To calculate the total time taken to find
	 * all shortest paths, at the start and end of the method, the times are recorded.
	 * 
	 * @param  source The node within the graph that represents the node of interest.
	 * @return A mapping of each node within the graph alongside their neighbours, based on their distances.
	 */
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
		
		// As distance has been updated, remove node from queue and re-add to set change.
		queue.remove(source);
		queue.add(source);
		
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
		
		// Return the shortest path to each node from the source.
		return previous;
	}
	
	/**
	 * This method calculates the shortest path between the specified source and target node 
	 * within the graph. Once the shortest distance from source to target is found, the outcome
	 * is reversed to represent to represent source to target, rather than target to source.  
	 * To calculate the total time taken to find all shortest paths, at the start and end of 
	 * the method, the times are recorded.
	 * 
	 * @param shortestPaths The shortest path to each node from the source.
	 * @param source		The source node (starting point).
	 * @param target		The target node (ending point).
	 * @return 				A list of nodes equating to the shortest path applicable from source to target.
	 */
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
	
	/**
	 * This method is used to find the shortest path metadata, such as the total distance, total
	 * amount of edges, and the overall time taken to find the shortest path.
	 * 
	 * @param path A list containing the shortest path from the source node to the target node. 
	 * @return 	   An instance of {@link com.mjh.innaxyswebapp.model.ShortestPath} containing path metadata.
	 */
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
	/**
	 * Method to return all nodes within the graph.
	 * 
	 * @return All nodes within the graph.
	 */
	public List<Node> getNodes() {
		return this.nodes;
	}
	
	/**
	 * Method which sets the nodes within the graph, note that if this is done,
	 * the adjacency matrix should be re-constructed.
	 * 
	 * @param nodes Nodes that should be used in the graph.
	 */
	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}
	
	/**
	 * Method to return all edges within the graph.
	 * 
	 * @return All edges within the graph.
	 */
	public List<Edge> getEdges() {
		return this.edges;
	}
	
	/**
	 * Method which sets the edges within the graph, note that if this is done,
	 * the adjacency matrix should be re-constructed.
	 * 
	 * @param edges Edges that should be used in the graph.
	 */
	public void setEdges(List<Edge> edges) {
		this.edges = edges;
	}
	
	/**
	 * Method to return the adjacency list of the graph.
	 * 
	 * @return The adjacency list of the graph.
	 */
	public Map<Node, Map<Node, Integer>> getAdjacencyList() {
		return this.adjacencyList;
	}
	
	/**
	 * Method which sets the adjacency list of the graph, ensure that the adjacency list
	 * correctly matches that of the nodes and their edges.
	 * 
	 * @param adjacencyList The new adjacency list for the graph.
	 */
	public void setAdjacencyList(Map<Node, Map<Node, Integer>> adjacencyList) {
		this.adjacencyList = adjacencyList;
	}
	
	/**
	 * Method to return the {@link com.mjh.innaxyswebapp.model.Timer} instance used by the graph.
	 * 
	 * @return The {@link com.mjh.innaxyswebapp.model.Timer} instance used by the graph.
	 */
	public Timer getTimer() {
		return this.timer;
	}
	
	/**
	 * Method which sets the instance of the {@link com.mjh.innaxyswebapp.model.Timer} class.
	 * 
	 * @param timer Instance of {@link com.mjh.innaxyswebapp.model.Timer}.
	 */
	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	/*---- Overridden Methods ----*/
	/**
	 * Returns the hash code of the adjacency list.
	 * 
	 * @return The hash code of the instance.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.adjacencyList);
	}

	/**
	 * Method used to determine whether or not another instance of Graph is 
	 * equal to another. This is primarily tested via the graph's
	 * adjacency list.
	 * 
	 * @param  obj The instance which to compare.
	 * @return true if the two instances are the same, false otherwise.
	 */
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
	
	/**
	 * Returns the string representation of the Graph class, containing the source
	 * all nodes and their neighbours, alongside their distances.
	 * 
	 * @return String representation of the Graph class.
	 */
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
