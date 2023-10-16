package com.mjh.innaxyswebapp.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.mjh.innaxyswebapp.model.Graph;
import com.mjh.innaxyswebapp.model.Node;
import com.mjh.innaxyswebapp.model.ShortestPath;
import com.mjh.innaxyswebapp.service.GraphService;

import jakarta.validation.constraints.Pattern;

/**
 * Class which represents the controller used to handle API routes. It is expected for a JS script to send requests
 * to the routes defined in this controller. Mainly, this will either return the entire graph, or the shortest path.
 * 
 * @author	MaxMJH - MaxHarrisMJH@gmail.com
 * @version 1.0
 * @since 	02-10-2023
 */
@Validated
@RestController
public class GraphAPIController {
	/*---- Fields ----*/
	/**
	 * Variable to store the {@link com.mjh.innaxyswebapp.service.GraphService}. 
	 */
	@Autowired
	private GraphService graphService;
	
	/*---- Methods / Routes ----*/
	/**
	 * This method defines the route '/api/graph' and obtains the graph, and returns
	 * it's contents in a JSON form so that it can be parsed via JS.
	 * 
	 * @param <ResponseEntity<Graph>> JSON form of {@link com.mjh.innaxyswebapp.model.Graph}. 
	 * @return JSON form of {@link com.mjh.innaxyswebapp.model.Graph}.
	 */
	@GetMapping("/api/graph")
	public ResponseEntity<Graph> displayGraph() {
		Graph graph = this.graphService.getGraph();
		
		// Return the JSON representation of the Graph instance.
		return new ResponseEntity<Graph>(graph, HttpStatus.OK);
	}
	
	/**
	 * This method defines the route '/api/graph/shortestpath' and obtains the shortest path
	 * based on the input parameters. If a shortest path has been found, it will be available
	 * in JSON form, alongside its metadata such as total distance, etc...
	 * 
	 * This routing also ensures that the passed parameters are valid (must be a single, 
	 * capitalised letter), as well as ensuring that the nodes are actually contained within 
	 * the database. If the overall validation fails, a 500 response is returned, of which
	 * will then need to be handled by JS.
	 * 
	 * @param <ResponseEntity<ShortestPath>> JSON form of {@link com.mjh.innaxyswebapp.model.ShortestPath}. 
	 * @param source Name of the source node.
	 * @param target Name of the target node.
	 * @return JSON form of {@link com.mjh.innaxyswebapp.model.ShortestPath}
	 */
	@GetMapping("/api/graph/shortestpath")
	public ResponseEntity<ShortestPath> displayShortestPath(
			@RequestParam @Pattern(regexp = "^[A-Z]{1}", message = "Source node failed to validate!") String source, 
			@RequestParam @Pattern(regexp = "^[A-Z]{1}", message = "Target node failed to validate!") String target
	) {
		// Get an instance of 'Graph' from the 'GraphService';
		Graph graph = this.graphService.getGraph();
		
		// Get the list of nodes from the graph.
		List<Node> nodes = graph.getNodes();
		
		// Check to see if the provided nodes are within the applicable range in the 'nodes' table.
		if(!nodes.contains(new Node(source, 0, 0)) || !nodes.contains(new Node(target, 0, 0))) {
			// Throw a 500 signifying that the selected nodes are not registered in the database.
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "displayShortestPath: The nodes you selected are not within the correct range!");
		}
		
		// Get the source and target nodes based on the selected nodes via their name.
		Node sourceNode = nodes.get(nodes.indexOf(new Node(source, 0, 0)));
		Node targetNode = nodes.get(nodes.indexOf(new Node(target, 0, 0)));
		
		// Obtain the shortest path and its metadata.
		ShortestPath shortestPath = this.graphService.getShortestPath(sourceNode, targetNode);
		
		// Return JSON representation of the ShortestPath instance.
		return new ResponseEntity<ShortestPath>(shortestPath, HttpStatus.OK);
	}
}
