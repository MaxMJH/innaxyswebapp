package com.mjh.innaxyswebapp.service;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mjh.innaxyswebapp.model.Edge;
import com.mjh.innaxyswebapp.model.Graph;
import com.mjh.innaxyswebapp.model.Node;
import com.mjh.innaxyswebapp.model.ShortestPath;
import com.mjh.innaxyswebapp.utility.XMLParsingUtility;

/**
 * Class which allows for access to both {@link com.mjh.innaxyswebapp.service.NodeService} and 
 * {@link com.mjh.innaxyswebapp.service.EdgeService}, as well as ensuring that the static XML
 * is parsed so that a {@link com.mjh.innaxyswebapp.model.Graph} can be produced.
 * 
 * @author	MaxMJH - MaxHarrisMJH@gmail.com
 * @version 1.0
 * @since 	02-10-2023
 */
@Service
public class GraphService {
	/*---- Fields ----*/
	/**
	 * Variable to store the {@link com.mjh.innaxyswebapp.service.NodeService}. 
	 */
	@Autowired
	private NodeService nodeService;
	
	/**
	 * Variable to store the {@link com.mjh.innaxyswebapp.service.EdgeService}. 
	 */
	@Autowired
	private EdgeService edgeService;
	
	/*---- Methods ----*/
	/**
	 * Method to check if the XML already exists within the database. Note that,
	 * as the XML file is static, that it simply checks if the respective tables
	 * have any data.
	 * 
	 * @return true if the tables are not empty, false otherwise.
	 */
	private boolean xmlExists() {		
		// Check to see if the 'nodes' table exists.
		if(!this.nodeService.tableExists()) {
			// If not, create the table.
			this.nodeService.createTable();
		}
		
		// Check to see if the 'edges' table exists.
		if(!this.edgeService.tableExists()) {
			// If not, create the table.
			this.edgeService.createTable();
		}
		
		// Return whether or not the both tables are empty.
		return !this.nodeService.getAllNodes().isEmpty() || !this.edgeService.getAllEdges().isEmpty();
	}
	
	/**
	 * Method to return the graph regardless if it exists in the database or not.
	 * 
	 * @return An instance of {@link com.mjh.innaxyswebapp.model.Graph} with the XML data parsed into it.
	 */
	public Graph getGraph() {
		// Check if the XML already exists within the database.
		if(xmlExists()) {
			// If so, return an instance of Graph with the stored data.
			return new Graph(this.nodeService.getAllNodes(), this.edgeService.getAllEdges());
		} else {
			// If not, parse the XML, add it to the database, and return an instance of Graph.
			try {
				// Parse the XML.
				XMLParsingUtility parser = new XMLParsingUtility("graph.xml");
				
				// Get the XML in Graph form.
				Graph graph = parser.parseXML();
				
				// Add each node to the table.
				for(Node node : graph.getNodes()) {
					this.nodeService.createNode(node);
				}
				
				// Add each edge to the table.
				for(Edge edge : graph.getEdges()) {
					this.edgeService.createEdge(edge);
				}
				
				// Return the graph.
				return graph;
			} catch (URISyntaxException e) {
				// TODO Change to log.
				e.printStackTrace();
				return new Graph(null, null);
			}
		}
	}
	
	public ShortestPath getShortestPath(Node source, Node target) {
		Graph graph = this.getGraph();
		
		Map<Node, Node> shortestPaths = graph.calculateShortestPaths(source);
		List<Node> shortestPath = graph.getShortestPath(shortestPaths, source, target);
		return graph.getPathMetadata(shortestPath);
	}
}
