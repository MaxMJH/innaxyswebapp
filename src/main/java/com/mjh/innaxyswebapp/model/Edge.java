package com.mjh.innaxyswebapp.model;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Class which represents an edge (link) between two nodes. The edge itself
 * can have a distance (weight) attached to it. Various getters and setters
 * are available so as to provide specific information pertaining to the edge.
 * 
 * The {@link com.mjh.innaxyswebapp.model.Node} can be reviewed to find further
 * information regarding the class.
 * 
 * @author	MaxMJH - MaxHarrisMJH@gmail.com
 * @version 1.0
 * @since 	02-10-2023
 */
@Entity
@Table(name = "edges")
public class Edge {
	/*---- Fields ----*/
	/**
	 * Variable to store the ID of an edge - mainly used by JPA.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * Variable to store the source node of the edge.
	 */
	@ManyToOne
	@JoinColumn(name = "fk_node_source")
	private Node source;
	
	/**
	 * Variable to store the target node of the edge.
	 */
	@ManyToOne
	@JoinColumn(name = "fk_node_target")
	private Node target;
	
	/**
	 * Variable to store that distance (weight) between the two nodes.
	 */
	@Column(name = "distance")
	private int distance;
	
	/*---- Constructors ----*/
	/**
	 * Default constructor which initialises an empty source and target
	 * node, as well as setting a default distance. These nodes and distance
	 * can be set after edge creation.
	 */
	public Edge() {
		this(new Node(), new Node(), 0);
	}
	
	/**
	 * Overloaded constructor which initialises an edge with a source and target node
	 * with an empty distance.
	 * 
	 * @param source The source node of the edge.
	 * @param target The target node of the edge.
	 */
	public Edge(Node source, Node target) {
		this(source, target, 0);
	}
	
	/**
	 * Overloaded constructor which initialises an edge with a source and target node 
	 * with a set distance.
	 * 
	 * @param source The source node of the edge.
	 * @param target The target node of the edge.
	 * @param distance The distance between the two nodes.
	 */
	public Edge(Node source, Node target, int distance) {
		this.source = source;
		this.target = target;
		this.distance = distance;
	}
	
	/*---- Getters and Setters ----*/
	/**
	 * Method to return the current edge's ID - mainly used by JPA.
	 * 
	 * @return The current edge's ID.
	 */
	public Long getId() {
		return this.id;
	}
	
	/**
	 * Method to set the edge's ID - mainly used by JPA.
	 * 
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * Method to return the current edge's source node.
	 * 
	 * @return The current edge's source node.
	 */
	public Node getSource() {
		return this.source;
	}
	
	/**
	 * Method to set the edge's source node.
	 * 
	 * @param source The source node of the edge.
	 */
	public void setSource(Node source) {
		this.source = source;
	}
	
	/**
	 * Method to return the current edge's target node. 
	 * 
	 * @return The current edge's target node. 
	 */
	public Node getTarget() {
		return this.target;
	}
	
	/**
	 * Method to set the edge's target node.
	 * 
	 * @param target The target node of the edge.
	 */
	public void setTarget(Node target) {
		this.target = target;
	}
	
	/**
	 * Method to return the current edge's distance.
	 * 
	 * @return The current edge's distance.
	 */
	public int getDistance() {
		return this.distance;
	}
	
	/**
	 * Method to set the edge's distance.
	 * 
	 * @param distance The distance of the edge.
	 */
	public void setDistance(int distance) {
		this.distance = distance;
	}
	
	/**
	 * Returns the hash code of the edge, consisting of the nodes and distance.
	 * 
	 * @return The hash code of the instance.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.source, this.target, this.distance);
	}
	
	/**
	 * Method used to determine whether or not another instance of Edge is 
	 * equal to another. This is primarily tested via the edge's source and
	 * target nodes.
	 * 
	 * @param  obj The instance which to compare.
	 * @return true if the two instances are the same, false otherwise.
	 */
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

	/**
	 * Returns the string representation of the Edge class, containing the source
	 * and target node, as well as the distance.
	 * 
	 * @return String representation of the Edge class.
	 */
	@Override 
	public String toString() {
		return String.format("{source: %s, target: %s, distance: %d}", this.source.getName(), this.target.getName(), this.distance);
	}
}
