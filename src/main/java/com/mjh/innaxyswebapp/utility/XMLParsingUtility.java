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

/**
 * Class which allows for XML data to be parsed into a readable format that can be used
 * to create nodes and edges. These created nodes and edges can then be turned into a graph
 * so as to find the shortest path between two specified points. It is assumed that the XML 
 * will follow a strict format. To parse the XML, this class utilises Java's built-in XML parser,
 * where specifically StAX is used to ensure efficient utilisation of resources and avoid
 * overheads. Various getters and setters are available so as to provide specific information 
 * pertaining to the edge.
 * 
 * Both {@link com.mjh.innaxyswebapp.model.Node}, {@link com.mjh.innaxyswebapp.model.Edge} and 
 * {@link com.mjh.innaxyswebapp.model.Graph} can be reviewed to find further information regarding 
 * the class. 
 * 
 * @author	MaxMJH - MaxHarrisMJH@gmail.com
 * @version 1.0
 * @since 	02-10-2023
 */
public class XMLParsingUtility {
	/*---- Field ----*/
	/**
	 * This variable stores the reader necessary to parse the XML.
	 */
	private XMLEventReader xmlReader;
	
	/*---- Constructors ----*/
	/**
	 * Default constructor which essentially does nothing.
	 */
	public XMLParsingUtility() {}
	
	/**
	 * Overloaded constructor which takes a string that points to an XML file that is contained
	 * within the resources (resources/META-INF/resources/static/xml).
	 * 
	 * @param xmlPath 			  Name of the XML file.
	 * @throws URISyntaxException Signifies that the specified XML file name was unable to be parsed.
	 */
	public XMLParsingUtility(String xmlPath) throws URISyntaxException {
		// Hard code the XML location, if dynamic, point to the XML file outside of resources.
		this(Path.of(XMLParsingUtility.class.getClassLoader().getResource("META-INF//resources//static//xml//" + xmlPath).toURI()).toFile());
	}
	
	/**
	 * Overloaded constructor which takes the specific XML file, and attempts to create an XML reader so
	 * as to allow for parsing. If the file could not be opened, this will be logged, and will exit gracefully.
	 * 
	 * @param xmlFile File instance that points to the XML file.
	 */
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
	
	/*---- Method ----*/
	/**
	 * This method parses the specified XML and returns an instance of Graph. Depending
	 * on the 'event', a respective instance of their value will be created. If the XML
	 * contains an 'event' such as Node, this will then be populated etc. Once each
	 * 'event' has been collected, they will be added to a an array, which will
	 * then be passed to a instance of Graph.
	 * 
	 * @return An instance of Graph which is populated determined on the data in the XML.
	 */
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
		return new Graph(edges, new ArrayList<>(nodes.values()));
	}
	
	/*---- Getter and Setter ----*/
	/**
	 * This method returns the current parser.
	 * 
	 * @return The current parser.
	 */
	public XMLEventReader getXmlReader() {
		return this.xmlReader;
	}
	
	/**
	 * This method sets the parser.
	 * 
	 * @param xmlReader Instance to set the parser to.
	 */
	public void setXmlReader(XMLEventReader xmlReader) {
		this.xmlReader = xmlReader;
	}
}
