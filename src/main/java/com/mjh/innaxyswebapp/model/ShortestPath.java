package com.mjh.innaxyswebapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// TODO Add JavaDoc comments.
public class ShortestPath {
	/*---- Fields ----*/
	private List<Node> shortestPath;
	private int totalDistance;
	private int totalEdges;
	private long totalCalculationTime;
	
	/*---- Constructors ----*/
	public ShortestPath() {
		this(new ArrayList<>(), 0, 0, 0);
	}
	
	public ShortestPath(List<Node> shortestPath, int totalDistance, int totalEdges, long totalCalculationTime) {
		this.shortestPath = shortestPath;
		this.totalDistance = totalDistance;
		this.totalEdges = totalEdges;
		this.totalCalculationTime = totalCalculationTime;
	}
	
	/*---- Getters and Setters ----*/
	public List<Node> getShortestPath() {
		return this.shortestPath;
	}
	
	public void setShortestPath(List<Node> shortestPath) {
		this.shortestPath = shortestPath;
	}
	
	public int getTotalDistance() {
		return this.totalDistance;
	}
	
	public void setTotalDistance(int totalDistance) {
		this.totalDistance = totalDistance;
	}
	
	public int getTotalEdges() {
		return this.totalEdges;
	}
	
	public void setTotalEdges(int totalEdges) {
		this.totalEdges = totalEdges;
	}
	
	public long getTotalCalculationTime() {
		return this.totalCalculationTime;
	}
	
	public void setTotalCalculationTime(long totalCalculationTime) {
		this.totalCalculationTime = totalCalculationTime;
	}
	
	/*---- Overridden Methods ----*/
	@Override
	public int hashCode() {
		return Objects.hash(this.shortestPath, this.totalDistance, this.totalEdges, this.totalCalculationTime);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (!(obj instanceof Node)) {
			return false;
		}
		
		ShortestPath other = (ShortestPath) obj;
		
		return Objects.equals(this.shortestPath, other.shortestPath);
	}
}
