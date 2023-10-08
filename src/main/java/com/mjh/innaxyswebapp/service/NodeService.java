package com.mjh.innaxyswebapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mjh.innaxyswebapp.data.NodeRepository;
import com.mjh.innaxyswebapp.model.Node;

/**
 * Class which allows for access to various methods which will alter the data
 * within the 'nodes' table of the database. CRUD principles are found here and can
 * be used to add nodes to the 'nodes' table, as well as retrieval, deletion, and
 * updating.
 * 
 * The {@link com.mjh.innaxyswebapp.data.NodeRepository} can be reviewed to find
 * further queries necessary to ensure that if a node is removed, its edge will also
 * be removed.
 * 
 * @author	MaxMJH - MaxHarrisMJH@gmail.com
 * @version 1.0
 * @since 	02-10-2023
 */
@Service
public class NodeService {
	/*---- Field ----*/
	/**
	 * Variable to store the {@link com.mjh.innaxyswebapp.data.NodeRepository} for database access.
	 */
	@Autowired
	private NodeRepository nodeRepository;
	
	/*---- Methods ----*/
	/**
	 * Method used to add a node to the database.
	 * 
	 * @param  node The node of which to add.
	 * @return The saved node.
	 */
	public Node createNode(Node node) {
		return this.nodeRepository.save(node);
	}
	
	/**
	 * Method used to get all nodes from the database.
	 * 
	 * @return A list of all nodes within the nodes table.
	 */
	public List<Node> getAllNodes() {
		return this.nodeRepository.findAll();
	}
	
	/**
	 * Method used to get a node specified by a primary key (name of the node).
	 * 
	 * @param  name The name of the node.
	 * @return An {@link java.util.Optional} if the node exists, or an empty {@link java.util.Optional} otherwise.
	 */
	public Optional<Node> getNode(String name) {
		return this.nodeRepository.findById(name);
	}
	
	/**
	 * Method used to update an existing node within the database - note that
	 * the nodes primary key must also be passed.
	 * 
	 * @param  name The name of the node to update.
	 * @param  node The node which contains the new data.
	 * @return The updated node, or null if update failed.
	 */
	public Node updateNode(String name, Node node) {
		// Obtain the node represented by its primary key value.
		Optional<Node> savedNode = this.nodeRepository.findById(name);
		
		// Check to see if a node was actually returned.
		if(savedNode.isPresent()) {
			// If so, set the new values.
			savedNode.get().setName(node.getName());
			savedNode.get().setX(node.getX());
			savedNode.get().setY(node.getY());
			
			// Return the node if added successfully.
			return this.nodeRepository.save(savedNode.get());
		}
		
		// Return null signifying that the primary key value does not exist.
		return null;
	}
	
	/**
	 * Method used to remove a node from the database as well as its
	 * edges.
	 * 
	 * @param node The node to remove from the database.
	 */
	public void removeNode(Node node) {
		this.nodeRepository.delete(node);
		this.nodeRepository.deleteEdgeByNode(node.getName());
	}
	
	/**
	 * Method used to remove a node from the database as well as its
	 * edges, specified by its name.
	 * 
	 * @param name The name of the node to remove from the database.
	 */
	public void removeNode(String name) {
		this.nodeRepository.deleteById(name);
		this.nodeRepository.deleteEdgeByNode(name);
	}
	
	/**
	 * Method used to remove all nodes from the nodes table as well
	 * as all edges.
	 */
	public void removeAll() {
		this.nodeRepository.deleteAll();
		this.nodeRepository.deleteAllEdges();
	}
	
	/**
	 * Method used to check if the 'nodes' table exists in the database.
	 * 
	 * @return true if the table exists, false otherwise.
	 */
	public boolean tableExists() {
		return this.nodeRepository.tableExists() > 0;
	}
	
	/**
	 * Method used to ensure that the 'nodes' table exists even if deleted after application has started.
	 */
	public void createTable() {
		this.nodeRepository.createNodesTable();
	}
}
