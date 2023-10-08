package com.mjh.innaxyswebapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mjh.innaxyswebapp.data.EdgeRepository;
import com.mjh.innaxyswebapp.model.Edge;

/**
 * Class which allows for access to various methods which will alter the data
 * within the 'edges' table of the database. CRUD principles are found here and can
 * be used to add edges to the 'edges' table, as well as retrieval, deletion, and
 * updating.
 * 
 * The {@link com.mjh.innaxyswebapp.data.EdgeRepository} can be reviewed to find
 * further queries necessary.
 * 
 * @author	MaxMJH - MaxHarrisMJH@gmail.com
 * @version 1.0
 * @since 	02-10-2023
 */
@Service
public class EdgeService {
	/*---- Field ----*/
	/**
	 * Variable to store the {@link com.mjh.innaxyswebapp.data.EdgeRepository} for database access.
	 */
	@Autowired
	private EdgeRepository edgeRepository;
	
	/*---- Methods ----*/
	/**
	 * Method used to add an edge to the database.
	 * 
	 * @param  edge The edge of which to add.
	 * @return The saved edge.
	 */
	public Edge createEdge(Edge edge) {
		return this.edgeRepository.save(edge);
	}
	
	/**
	 * Method used to get all edges from the database.
	 * 
	 * @return A list of all edges within the edges table.
	 */
	public List<Edge> getAllEdges() {
		return this.edgeRepository.findAll();
	}
	
	/**
	 * Method used to get an edge specified by a primary key (ID of the edge).
	 * 
	 * @param  id The id of the edge.
	 * @return An {@link java.util.Optional} if the edge exists, or an empty {@link java.util.Optional} otherwise.
	 */
	public Optional<Edge> getEdgeById(Long id) {
		return this.edgeRepository.findById(id);
	}
	
	/**
	 * Method used to get all edges specified by the name of a source node.
	 * 
	 * @param  name The source name of the node.
	 * @return A {@link java.util.List} if the edge exists, or null otherwise.
	 */
	public List<Edge> getEdgeBySource(String name) {
		return this.edgeRepository.findEdgeBySource(name);
	}
	
	/**
	 * Method used to get all edges specified by the name of a target node.
	 * 
	 * @param  name The target name of the node.
	 * @return A {@link java.util.List} if the edge exists, or null otherwise.
	 */
	public List<Edge> getEdgeByTarget(String name) {
		return this.edgeRepository.findEdgeByTarget(name);
	}
	
	/**
	 * Method used to get an edge specified by the name of the source and target node.
	 * 
	 * @param  source The source name of the node.
	 * @param  target The target name of the node.
	 * @return A {@link java.util.Optional} if the edge exists, or an empty {@link java.util.Optional} otherwise.
	 */
	public Optional<Edge> getEdgeBySourceAndTarget(String source, String target) {
		return this.edgeRepository.findEdgeBySourceAndTarget(source, target);
	}
	
	/**
	 * Method used to get an edge specified by the name of the target and source node.
	 * 
	 * @param  target The target name of the node.
	 * @param  source The source name of the node.
	 * @return A {@link java.util.Optional} if the edge exists, or an empty {@link java.util.Optional} otherwise.
	 */
	public Optional<Edge> getEdgeByTargetAndSource(String target, String source) {
		return this.edgeRepository.findEdgeByTargetAndSource(target, source);
	}
	
	/**
	 * Method used to update an existing edge within the database - note that
	 * the edge's primary key must also be passed.
	 * 
	 * @param  id The id of the edge to update.
	 * @param  edge The edge which contains the new data.
	 * @return The updated edge, or null if update failed.
	 */
	public Edge updateEdge(Long id, Edge edge) {
		// Obtain the edge represented by its primary key value.
		Optional<Edge> savedEdge = this.edgeRepository.findById(id);
				
		// Check to see if an edge was actually returned.
		if(savedEdge.isPresent()) {
			// If so, set the new values.
			savedEdge.get().setSource(edge.getSource());
			savedEdge.get().setSource(edge.getTarget());
			savedEdge.get().setDistance(edge.getDistance());
					
			// Return the edge if added successfully.
			return this.edgeRepository.save(savedEdge.get());
		}
				
		// Return null signifying that the primary key value does not exist.
		return null;
	}
	
	/**
	 * Method used to update an existing edge within the database.
	 * 
	 * @param  existingEdge The edge to update.
	 * @param  newEdge The edge which contains the new data.
	 * @return The updated edge, or null if update failed.
	 */
	public Edge updateEdge(Edge existingEdge, Edge newEdge) {
		return this.updateEdge(existingEdge.getId(), newEdge);
	}
	
	/**
	 * Method used to remove edges specified by their source node name.
	 * 
	 * @param name The edge to remove from the database by their source node name.
	 */
	public void removeEdgeBySource(String name) {
		this.edgeRepository.deleteEdgeBySource(name);
	}
	
	/**
	 * Method used to remove edges specified by their target node name.
	 * 
	 * @param name The edge to remove from the database by their target node name.
	 */
	public void removeEdgeByTarget(String name) {
		this.edgeRepository.deleteEdgeByTarget(name);
	}
	
	/**
	 * Method used to remove an edge from the database.
	 * 
	 * @param edge The edge to remove from the database.
	 */
	public void removeEdge(Edge edge) {
		this.edgeRepository.delete(edge);
	}
	
	/**
	 * Method used to remove all edges from the 'edges' table.
	 */
	public void removeAll() {
		this.edgeRepository.deleteAll();
	}
}
