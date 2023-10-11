package com.mjh.innaxyswebapp.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mjh.innaxyswebapp.model.Graph;
import com.mjh.innaxyswebapp.model.Node;
import com.mjh.innaxyswebapp.model.ShortestPath;
import com.mjh.innaxyswebapp.service.GraphService;

@RestController
public class GraphAPIController {
	@Autowired
	private GraphService graphService;
	
	@GetMapping("/api/graph")
	public ResponseEntity<Graph> test() {
		Graph graph = this.graphService.getGraph();
		
		return new ResponseEntity<Graph>(graph, HttpStatus.OK);
	}
	
	@GetMapping("/api/graph/shortestpath")
	public ResponseEntity<ShortestPath> test2(@RequestParam String source, @RequestParam String target) {
		Graph graph = this.graphService.getGraph();
		
		Node sourceNode = graph.getNodes().get(graph.getNodes().indexOf(new Node(source, 0, 0)));
		Node targetNode = graph.getNodes().get(graph.getNodes().indexOf(new Node(target, 0, 0)));
		
		ShortestPath shortestPath = this.graphService.getShortestPath(sourceNode, targetNode);
		
		return new ResponseEntity<ShortestPath>(shortestPath, HttpStatus.OK);
	}
}
