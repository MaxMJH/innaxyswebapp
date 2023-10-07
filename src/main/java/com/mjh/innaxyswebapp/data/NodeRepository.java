package com.mjh.innaxyswebapp.data;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mjh.innaxyswebapp.model.Node;

/**
 * This interface allows for communication with the PostgreSQL database. 
 * It is likely for this particular class to be utilised via a service class
 * so as to apply CRUD. Further, if a primary key that is passed exists
 * within the relevant table, a {@link com.mjh.innaxyswebapp.model.Node} will
 * be returned, populated with stored data.
 * 
 * @param <Node>   Returning node if the primary key value exists.
 * @param <String> Primary key of a relevant node (name of the node).
 */
public interface NodeRepository extends JpaRepository<Node, String> {

}
