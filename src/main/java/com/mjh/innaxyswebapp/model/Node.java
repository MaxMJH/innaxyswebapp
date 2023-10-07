package com.mjh.innaxyswebapp.model;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/** 
 * Class which represents a node. The node itself contains its name, as well as its x and y-axis
 * coordinates. Various getters and setters are available so as to provide specific information 
 * pertaining to the node.
 * 
 * @author	MaxMJH - MaxHarrisMJH@gmail.com
 * @version 1.0
 * @since 	02-10-2023
 */
@Entity
@Table(name = "nodes")
public class Node {
	/*---- Fields ----*/
	/**
	 * Variable to store the name of the node.
	 */
	@Id
	@Column(name = "name")
	private String name;
	
	/**
	 * Variable to store the x-axis of the node.
	 */
	@Column(name = "x")
	private int x;
	
	/**
	 * Variable to store the y-axis of the node.
	 */
	@Column(name = "y")
	private int y;
	
	/*---- Constructors ----*/
	/**
	 * Default constructor which initialises an empty node. This node can be set
	 * after node creation.
	 */
	public Node() {
		this("", 0, 0);
	}
	
	/**
	 * Overloaded constructor which only sets the name of the node, with the x and
	 * y-axis coordinates being set to a default value of 0.
	 * 
	 * @param name The name of the node.
	 */
	public Node(String name) {
		this(name, 0, 0);
	}
	
	/**
	 * Overloaded constructor which sets the name, x, and y-axis coordinates of
	 * the node.
	 *  
	 * @param name The name of the node.
	 * @param x	   The x-axis coordinate of the node.
	 * @param y	   The y-axis coordinate of the node.
	 */
	public Node(String name, int x, int y) {
		this.name = name;
		this.x = x;
		this.y = y;
	}

	/*---- Getters and Setters ----*/
	/**
	 * Method to return the name of the node.
	 * 
	 * @return The name of the node.
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Method which sets the name of the node.
	 * 
	 * @param name Name (string) of the node.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Method to return the x-axis coordinate of the node.
	 * 
	 * @return The x-axis coordinate of the node.
	 */
	public int getX() {
		return this.x;
	}
	
	/**
	 * Method which sets the x-axis coordinate of the node.
	 * 
	 * @param x Integer coordinate of the node's x-axis.
	 */
	public void setX(int x) {
		this.x = x;
	}
	
	/**
	 * Method to return the y-axis coordinate of the node.
	 * 
	 * @return The y-axis coordinate of the node.
	 */
	public int getY() {
		return this.y;
	}
	
	/**
	 * Method which sets the y-axis coordinate of the node.
	 * 
	 * @param y Integer coordinate of the node's y-axis.
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	/*---- Overridden Methods ----*/
	/**
	 * Returns the hash code of the node's name, x, and
	 * y-axis coordinate.
	 * 
	 * @return The hash code of the instance.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.name, this.x, this.y);
	}

	/**
	 * Method used to determine whether or not another instance of Node is 
	 * equal to another. This is primarily tested via the graph's
	 * name.
	 * 
	 * @param  obj The instance which to compare.
	 * @return true if the two instances are the same, false otherwise.
	 */
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
	
	/**
	 * Returns the string representation of the Node class, containing the name,
	 * x, and y-axis coordinate.
	 * 
	 * @return String representation of the Node class.
	 */
	@Override 
	public String toString() {
		return String.format("{name: %s, x: %d, y: %d}", this.name, this.x, this.y);
	}
}
