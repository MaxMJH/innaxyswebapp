"use strict";

/*---- Script Scope Variables ----*/
var source = null;
var target = null;
var selectedNodes = {};
var edges = []

// Set the size of the SVG, currently set to 800x800.
const margin = {top: 30, right: 30, bottom: 30, left: 40};
const width = 800 - margin.left - margin.right;
const height = 800 - margin.top - margin.bottom;

/*---- JSON Requests ----*/
// Send a GET request to obtain the JSON at '/api/graph', which displays the entire graph.
d3.json('/api/graph').then(data => {
  /*---- Event Listeners ----*/
  // Set an event listener for the 'Clear' button.
  $( '.btn-danger' ).on('click', function() {
	// Essentially 'reset' the entire view back to its origin state.
    source = null;
    target = null;
    selectedNodes = {};
    edges = [];
    
    $( 'circle' ).css('fill', '#6C757D');
    $( '.btn-danger' ).prop('disabled', true);
    $( '.btn-primary' ).prop('disabled', true);
    $( '.btn-info' ).prop('disabled', true);
    
    edgeGroups.selectAll('line')
      .style('stroke', '#003D97');
  });
  
  // Set an event listener for the 'Find Shortest Path' button.
  $( '.btn-primary').on('click', function() {
	// Send a GET request to obtain the JSON at '/api/graph/shortestpath/', which consists of data containing the shortest path from source to target.
    d3.json(`/api/graph/shortestpath?source=${source}&target=${target}`).then(shortestPath => {
	  // Obtain each node within the shortest path.
	  const nodes = shortestPath.shortestPath.map(node => node.name);
	  
	  // Iterate through each node, finding their respective edge from source to target.
	  for(var i = 0; i < nodes.length - 1; i++) {
		  const sourceNodeName = nodes[i];
		  const targetNodeName = nodes[i + 1];
		  
		  // Grab the edges from the graph that have source and target of the current source and target nodes.
		  const edge = data.edges.filter(e => (e.source.name === sourceNodeName && e.target.name === targetNodeName) || (e.source.name === targetNodeName && e.target.name === sourceNodeName));
		  
		  // Add the edge.
		  edges.push(edge[0]);
	  }
	  
	  // For all edges within the shortest path, set their line colour to '#FF0000' to signify the shortest path's edges.
	  edgeGroups.selectAll('line')
        .style("stroke", d => {
          const isInShortestPath = edges.some(edge => (edge.source.name === d.source.name && edge.target.name === d.target.name) || (edge.source.name === d.target.name && edge.target.name === d.source.name));

          return isInShortestPath ? "#FF0000" : "#003D97";
        });
      
      // Enabled the 'Statistics' button.
      $( '.btn-info' ).prop('disabled', false);
      
      // Set an event listener for the 'Statistics' button.
      $( '.btn-info' ).on('click', function() {
		// From the JSON request sent to '/api/graph/shortestpath/', show the remaining data, such as total edges, etc.
	    $( '.modal-body' ).html(`Total Distance: ${shortestPath.totalDistance}<br>Total Edges: ${shortestPath.totalEdges}<br>Calculation Time: ~${shortestPath.totalCalculationTime}ms`);
	  });
	});
  });
	
  // Set a flag which determines whether or not a node or edge is currently being hovered upon.
  var tooltipShown = false;
  
  /*---- Graph Creation ----*/
  // Select the SVG that will hold the actual graph, and set its size dynamically.
  const svg = d3.select('svg#graph')
	.attr('width', width + margin.left + margin.right)
	.attr('height', height + margin.top + margin.bottom)
	.append('g')
	  .attr('transform', `translate(${margin.left}, ${margin.top})`);

  // As coordinates have been supplied, allow their coordinates to fit correctly within the SVG.
  var xScale = d3.scaleLinear().domain([1, 14]).range([0, width]);
  var yScale = d3.scaleLinear().domain([1, 9]).range([height, 0]);

  // Create a group for each specific edge.
  const edgeGroups = svg
    .selectAll('.distance-label')
    .data(data.edges)
	.enter()
	.append('g')
	  .attr('class', 'distance-label');
	  
  // Add a line for each edge, plotting their coordinate positions between their designated source and target node.
  edgeGroups
    .append('line')
	  .attr('x1', d => (xScale(d.source.x)))
	  .attr('y1', d => (yScale(d.source.y)))
	  .attr('x2', d => (xScale(d.target.x)))
	  .attr('y2', d => (yScale(d.target.y)))
	  .on('mouseover', function(d) {
		// Check to see if the edge is currently already being hovered.
		if(!tooltipShown) {
		  // If not, obtain the position where the tooltip will show based on the x and y coordinates of the hovered edge.
		  const x = ($('svg#graph').position().left) + (parseFloat(d.originalTarget.attributes.x1.value) + parseFloat(d.originalTarget.attributes.x2.value)) / 2 + ($( 'div#popover' ).width());
		  const y = ($('svg#graph').position().top) + (parseFloat(d.originalTarget.attributes.y1.value) + parseFloat(d.originalTarget.attributes.y2.value)) / 2 - ($( 'div#popover' ).height() / 4);
		  
		  // Define the position and contents of the tooltip, and set visibility.
		  tooltip.style('top', y + 'px')
            .style('left', x + 'px')
            .style('visibility', 'visible')
            .select('h2')
              .text(`${d.currentTarget.__data__.source.name} <--> ${d.currentTarget.__data__.target.name}`)
              .style('text-align', 'center');
          
          // Set the inner-content of the tooltip with the hovered edge's distance.
          tooltip.select('p')
            .html(`Distance: ${d.currentTarget.__data__.distance}`);
		}
		
		// Check to see if the current hovered edge belongs to an edge in the shortest path. 
		if(!edges.some(e => (e.source.name === d.currentTarget.__data__.source.name && e.target.name === d.currentTarget.__data__.target.name) || (e.target.name === d.currentTarget.__data__.source.name && e.source.name === d.currentTarget.__data__.target.name))) {
	      // If not, the current hovered edge is a normal node that does not belong to the shortest path.
	      d3.select(this).style('stroke', '#7FACEE');
	    } else {
		  // If so, the current hovered edge is an edge that belongs to the shortest path.
		  d3.select(this).style('stroke', '#A80C0C');
		}
	    
	    // As the edge is currently hovered, set the flag to insinuate such.
	    tooltipShown = true;
	  })
	  .on('mouseout', function(d) {
		// Once the edge is no longer hovered, hide the tooltip.
		tooltip.style('visibility', 'hidden');
		
		// Check to see if the once hovered node belongs to an edge in the shortest path.
		if(!edges.some(e => (e.source.name === d.currentTarget.__data__.source.name && e.target.name === d.currentTarget.__data__.target.name) || (e.target.name === d.currentTarget.__data__.source.name && e.source.name === d.currentTarget.__data__.target.name))) {
	      // If not, the once hovered edge that does not belong to the shortest path returns back to its original colour.
	      d3.select(this).style('stroke', '#003D97');
        } else {
	      // If so, the once hovered edge that does belong to the shortest path returns back to its original colour.
		  d3.select(this).style('stroke', '#FF0000');
		}
        
        // As the edge is no longer hovered, set the flag to insinuate such.
        tooltipShown = false;
	  });
	  
  // To display a user-friendly indication of all edges' distance, create a rectangle (to easily discern the distance).
  // Note that both the rectangle and inner text will be located on the midpoint of each edge. 
  edgeGroups
    .append('rect')
	  .attr('x', d => (xScale(d.source.x) + xScale(d.target.x)) / 2 - 20)
	  .attr('y', d => (yScale(d.source.y) + yScale(d.target.y)) / 2 - 15)
	  .attr('width', 40)
	  .attr('height', 30)
	  .attr('fill', '#FFFFFF');
	
  // Then display the actual distance over the created rectangle.
  edgeGroups
    .append('text')
	  .attr('x', d => (xScale(d.source.x) + xScale(d.target.x)) / 2)
	  .attr('y', d => (yScale(d.source.y) + yScale(d.target.y)) / 2)
	  .attr('dy', '0.35em')
	  .attr('text-anchor', 'middle')
	  .text(d => d.distance);

  // Define the tooltip used when an edge or node is hovered.
  const tooltip = d3.select('div#graph')
    .append('div')
      .attr('id', 'popover');
 
  tooltip.append("h2");
 
  tooltip.append("p");
	
  // Create a group for each specific node.
  const nodeGroups = svg
    .selectAll('.node-group')
    .data(data.nodes)
    .enter()
    .append('g')
      .on('mouseover', function(d) {
		// Check to see if the node is currently already being hovered.
		if(!tooltipShown) {
		  // If not, obtain the position where the tooltip will show based on the x and y coordinates of the hovered node.
		  const x = ($('svg#graph').position().left) + (parseFloat(d.originalTarget.attributes.cx.value) - 7.5);
		  const y = ($('svg#graph').position().top) + (parseFloat(d.originalTarget.attributes.cy.value) - ($( 'div#popover' ).height() + 10));
		  
		  // Define the position and contents of the tooltip, and set visibility - also set the textual contents pertaining to node data.
		  tooltip.style('top', y + 'px')
            .style('left', x + 'px')
            .style('visibility', 'visible')
            .select('h2')
              .text(d.target.parentElement.__data__.name)
              .style('text-align', 'center');
              
            tooltip.select('p')
              .html(`X: ${d.target.parentElement.__data__.x}<br>Y: ${d.target.parentElement.__data__.y}`);
		}
	    
	    // Check to see if the currently hovered node has been selected.
	    if(!(d.target.__data__.name in selectedNodes)) {
		  // If not, allow other nodes that have not been selected to change colour when hovered.
	      d3.select(this).selectAll('circle').style('fill', '#AEBAC6');
	    }
	    
	    // As the node is currently hovered, set the flag to insinuate such.
	    tooltipShown = true;
	  })
	  .on('mouseout', function(d) {
		// Once the node is no longer hovered, hide the tooltip.
		tooltip.style('visibility', 'hidden');
		
		// Check to see if that when the currently hovered node is unhovered, if it has been selected.
	    if(!(d.target.__data__.name in selectedNodes)) {
		  // If not, change the colour of the node back to its original.
          d3.select(this).selectAll('circle').style('fill', '#6C757D');
        }
        
        // As the node is no longer hovered, set the flag to insinuate such.
        tooltipShown = false;
	  })
	  .on('click', function(d) {
		// On click, set source and target node.
	    if(source === null) {
		  // If the source node has not yet been selected, set clicked node as source.
	      source = d.target.__data__.name;
	      
	      // Change the colour of the selected node.
	      d3.select(this).selectAll('circle').style('fill', '#AEBAC6');
	      
	      // Add the selected node to selected nodes.
	      selectedNodes[source] = true;
	      
	      // Allow the 'Clear' button to be used.
	      $( '.btn-danger' ).prop('disabled', false);
	    } else if(target === null) {
		  // If the target node has not yet been selected, set clicked node as target.
	      target = d.target.__data__.name;
	      
	      // Change the colour of the selected node.
	  	  d3.select(this).selectAll('circle').style('fill', '#AEBAC6');
	  	  
	  	  // Add the selected node to selected nodes.
	  	  selectedNodes[target] = true;
	  	  
	  	  // Allow the 'Clear' button to be used.
	  	  $( '.btn-danger' ).prop('disabled', false);
	    }
	    
	    // Check if both a source and target node has been selected.
		if(source !== null && target !== null) {
		  // Allow the 'Find Shortest Path' button to be used.
		  $( '.btn-primary' ).prop('disabled', false);
		}
	  });
	
  // Display all nodes within the graph.
  nodeGroups
	.append('circle')
	  .attr('cx', d => xScale(d.x))
	  .attr('cy', d => yScale(d.y))
	  .attr('r', 30)
  
  // Set the name of the node inside the node itself.
  nodeGroups
	.append('text')
	  .attr('class', 'node-label')
	  .attr('x', d => xScale(d.x))
	  .attr('y', d => yScale(d.y))
	  .attr('dy', '0.35em')
	  .attr('text-anchor', 'middle')
	  .text(d => d.name);
});
