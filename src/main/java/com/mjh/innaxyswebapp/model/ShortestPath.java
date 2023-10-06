package com.mjh.innaxyswebapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class which represents data pertaining to the shortest path. Specific data consists
 * of total distance, total number of edges, and the total time taken to find the shortest
 * path. The shortest path is also stored within this class. Various getters and setters
 * are available so as to provide specific information pertaining to the edge.
 * 
 * The {@link com.mjh.innaxyswebapp.model.Node} can be reviewed to find further
 * information regarding the class. 
 * 
 * @author	MaxMJH - MaxHarrisMJH@gmail.com
 * @version 1.0
 * @since 	02-10-2023
 */
public class ShortestPath {
	/*---- Fields ----*/
	/**
	 * Variable to store the shortest path found using Dijkstra's Shortest Path algorithm.
	 */
	private List<Node> shortestPath;
	
	/**
	 * Variable to store the total distance of the shortest path.
	 */
	private int totalDistance;
	
	/**
	 * Variable to store the total number of edges of the shortest path.
	 */
	private int totalEdges;
	
	/**
	 * Variable to store the calculated total calculation time of finding the shortest path.
	 */
	private double totalCalculationTime;
	
	/*---- Constructors ----*/
	/**
	 * Default constructor which initialises an empty Node array, as well as default total distance,
	 * total edges, and total time taken to calculate the time taken to find the shortest path. 
	 * Information pertaining to the shortest path can be set after creation.
	 */
	public ShortestPath() {
		this(new ArrayList<>(), 0, 0, 0);
	}
	
	/**
	 * Overloaded constructor which sets the shortest path, as well as total distance, total edges, and 
	 * total calculation time.
	 * 
	 * @param shortestPath		   The shortest path from source to target.
	 * @param totalDistance		   The total distance of the shortest path from source to target.
	 * @param totalEdges		   The total number of edges in the shortest path from source to target.
	 * @param totalCalculationTime The total calculation time of finding the shortest path from source to target.
	 */
	public ShortestPath(List<Node> shortestPath, int totalDistance, int totalEdges, double totalCalculationTime) {
		this.shortestPath = shortestPath;
		this.totalDistance = totalDistance;
		this.totalEdges = totalEdges;
		this.totalCalculationTime = totalCalculationTime;
	}
	
	/*---- Getters and Setters ----*/
	/**
	 * Method to return the stored shortest path.
	 * 
	 * @return The stored shortest path.
	 */
	public List<Node> getShortestPath() {
		return this.shortestPath;
	}
	
	/**
	 * Method which sets the to be stored shortest path
	 * 
	 * @param shortestPath To be stored shortest path.
	 */
	public void setShortestPath(List<Node> shortestPath) {
		this.shortestPath = shortestPath;
	}
	
	/**
	 * Method which gets the total distance of the shortest path.
	 * 
	 * @return The total distance of the shortest path.
	 */
	public int getTotalDistance() {
		return this.totalDistance;
	}
	
	/**
	 * Method which sets the total distance of the shortest path.
	 * 
	 * @param totalDistance The total distance of the shortest path.
	 */
	public void setTotalDistance(int totalDistance) {
		this.totalDistance = totalDistance;
	}
	
	/**
	 * Method which gets the total number of edges of the shortest path.
	 * 
	 * @return The total number of edges of the shortest path.
	 */
	public int getTotalEdges() {
		return this.totalEdges;
	}
	
	/**
	 * Method which sets the total number of edges of the shortest path.
	 * 
	 * @param totalEdges The total number of edges of the shortest path.
	 */
	public void setTotalEdges(int totalEdges) {
		this.totalEdges = totalEdges;
	}
	
	/**
	 * Method which gets the total calculation time of finding the shortest path.
	 * 
	 * @return The total calculation time of finding the shortest path.
	 */
	public double getTotalCalculationTime() {
		return this.totalCalculationTime;
	}
	
	/**
	 * Method which sets the total calculation time of finding the shortest path.
	 * 
	 * @param totalCalculationTime The total calculation time of finding the shortest path.
	 */
	public void setTotalCalculationTime(double totalCalculationTime) {
		this.totalCalculationTime = totalCalculationTime;
	}
	
	/*---- Overridden Methods ----*/
	/**
	 * Returns the hash code of the shortest path, total distance, total edges,
	 * and total calculation time.
	 * 
	 * @return The hash code of the instance.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.shortestPath, this.totalDistance, this.totalEdges, this.totalCalculationTime);
	}

	/**
	 * Method used to determine whether or not another instance of ShortestPath is 
	 * equal to another. This is primarily tested via the shortest path.
	 * 
	 * @param  obj The instance which to compare.
	 * @return true if the two instances are the same, false otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (!(obj instanceof ShortestPath)) {
			return false;
		}
		
		ShortestPath other = (ShortestPath) obj;
		
		return Objects.equals(this.shortestPath, other.shortestPath);
	}
}
