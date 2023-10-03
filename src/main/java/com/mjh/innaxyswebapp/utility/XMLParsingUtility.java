package com.mjh.innaxyswebapp.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import com.mjh.innaxyswebapp.model.Edge;
import com.mjh.innaxyswebapp.model.Graph;
import com.mjh.innaxyswebapp.model.Node;

// TODO Add JavaDoc comments.
public class XMLParsingUtility {
	/*---- Fields ----*/
	private XMLEventReader xmlReader;
	
	/*---- Constructor ----*/
	public XMLParsingUtility() {}
	
	public XMLParsingUtility(String xmlPath) throws URISyntaxException {
		// Hard code the XML location, if dynamic, point to the XML file outside of resources.
		this(Path.of(XMLParsingUtility.class.getClassLoader().getResource("META-INF//resources//static//xml//" + xmlPath).toURI()).toFile());
	}
	
	public XMLParsingUtility(File xmlFile) {
		// Create an instance of the Javax XML library.
		XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
		
		try {
			// Create an XML reader that reads the passed XML File.
			this.xmlReader = xmlInputFactory.createXMLEventReader(new FileInputStream(xmlFile));
		} catch (FileNotFoundException | XMLStreamException e) {
			e.printStackTrace(); // Log this in future.
		}
	}
	
	/*---- Methods ----*/
	public Graph parseXML() {
		// Conscious on complexity, so use hashmap as O(1) access is available.
		// Instantiate a map to store all found nodes within the XML.
		Map<String, Node> nodes = new HashMap<>();
		
		// Instantiate node to be used within method scope.
		Node node = new Node();
		
		// Instantiate an array to store all found edges within the XML.
		List<Edge> edges = new ArrayList<>();
		
		// Instantiate edge to be used within method scope.
		Edge edge = new Edge();
		
		// Iterate through each 'event' in the XML.
		while(this.xmlReader.hasNext()) {
			try {
				// Get the XML 'event'.
				XMLEvent currentEvent = this.xmlReader.nextEvent();
				
				// Check to see if the current 'event' is an opening tag (e.g. <node>).
				if(currentEvent.isStartElement()) {
					// Get the current start element.
					StartElement currentElement = currentEvent.asStartElement();
					
					// Get the name of the actual tag.
					switch(currentElement.getName().getLocalPart()) {
						case "node":
							node = new Node();
							
							// Set the name of the current parsed node in the XML.
							node.setName(currentElement.getAttributeByName(new QName("name")).getValue());
							break;
						case "x":
							// Get the next 'event'.
							currentEvent = this.xmlReader.nextEvent();
							
							// Attempt to parse the data as an integer, and set the x co-ord value for the current node.
							node.setX(Integer.parseInt(currentEvent.asCharacters().getData()));
							break;
						case "y":
							// Get the next 'event'.
							currentEvent = this.xmlReader.nextEvent();
							
							// Attempt to parse the data as an integer, and set the y co-ord value for the current node.
							node.setY(Integer.parseInt(currentEvent.asCharacters().getData()));
							break;
						case "link":
							edge = new Edge();
							
							// Get the source and target node based on their name, and set the edge's source and target.
							edge.setSource(nodes.get(currentElement.getAttributeByName(new QName("start")).getValue()));
							edge.setTarget(nodes.get(currentElement.getAttributeByName(new QName("end")).getValue()));
							break;
						case "weight":
							// Get the next 'event'.
							currentEvent = this.xmlReader.nextEvent();
							
							// Attempt to parse the data as an integer, and set the distance (weight) value for the current edge.
							edge.setDistance(Integer.parseInt(currentEvent.asCharacters().getData()));
							break;
					}
				}
				
				// Check to see if the current 'event' is a closing tag (e.g </node>).
				if(currentEvent.isEndElement()) {
					// Get the current end element.
					EndElement currentElement = currentEvent.asEndElement();
					
					// Check to see if the end element is 'node'.
					if(currentElement.getName().getLocalPart().equals("node")) {
						// If so, add the created node to the map. Insinuates the end of node data.
						nodes.put(node.getName(), node);
					}
					
					// Check to see if the end element is 'link'.
					if(currentElement.getName().getLocalPart().equals("link")) {
						// If so, add the created edge to the list. Insinuates the end of edge data.
						edges.add(edge);
					}
				}
			} catch (XMLStreamException e) {
				e.printStackTrace(); // Log this in future.
			}
		}
		
		// Return a graph, with a populated adjacency list.
		return new Graph(edges);
	}
	
	/*---- Getter and Setter ----*/
	public XMLEventReader getXmlReader() {
		return this.xmlReader;
	}
	
	public void setXmlReader(XMLEventReader xmlReader) {
		this.xmlReader = xmlReader;
	}
}
