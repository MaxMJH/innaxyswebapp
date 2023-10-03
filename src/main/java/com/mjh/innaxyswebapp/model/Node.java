package com.mjh.innaxyswebapp.model;

import java.util.Objects;

// TODO Add JavaDoc comments.
public class Node {
	/*---- Fields ----*/
	private String name;
	private int x;
	private int y;
	
	/*---- Constructors ----*/
	public Node() {
		this("", 0, 0);
	}
	
	public Node(String name) {
		this(name, 0, 0);
	}
	
	public Node(String name, int x, int y) {
		this.name = name;
		this.x = x;
		this.y = y;
	}

	/*---- Getters and Setters ----*/
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getX() {
		return this.x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	/*---- Overridden Methods ----*/
	@Override
	public int hashCode() {
		return Objects.hash(this.name, this.x, this.y);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (!(obj instanceof Node)) {
			return false;
		}
		
		Node other = (Node) obj;
		
		return Objects.equals(this.name, other.name);
	}
	
	@Override 
	public String toString() {
		return String.format("{name: %s, x: %d, y: %d}", this.name, this.x, this.y);
	}
}
