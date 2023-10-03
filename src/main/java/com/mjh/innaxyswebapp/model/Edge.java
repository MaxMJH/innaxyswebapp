package com.mjh.innaxyswebapp.model;

import java.util.Objects;

// TODO Add JavaDoc comments.
public class Edge {
	/*---- Fields ----*/
	private Node source;
	private Node target;
	private int distance;
	
	/*---- Constructors ----*/
	public Edge() {
		this(new Node(), new Node(), 0);
	}
	
	public Edge(Node source, Node target) {
		this(source, target, 0);
	}
	
	public Edge(Node source, Node target, int distance) {
		this.source = source;
		this.target = target;
		this.distance = distance;
	}
	
	/*---- Getters and Setters ----*/
	public Node getSource() {
		return this.source;
	}
	
	public void setSource(Node source) {
		this.source = source;
	}
	
	public Node getTarget() {
		return this.target;
	}
	
	public void setTarget(Node target) {
		this.target = target;
	}
	
	public int getDistance() {
		return this.distance;
	}
	
	public void setDistance(int distance) {
		this.distance = distance;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.source, this.target, this.distance);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (!(obj instanceof Edge)) {
			return false;
		}
		
		Edge other = (Edge) obj;
		
		return Objects.equals(this.source, other.source) && Objects.equals(this.target, target);
	}

	@Override 
	public String toString() {
		return String.format("{source: %s, target: %s, distance: %d}", this.source.getName(), this.target.getName(), this.distance);
	}
}
