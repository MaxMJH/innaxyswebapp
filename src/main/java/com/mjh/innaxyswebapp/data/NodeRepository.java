package com.mjh.innaxyswebapp.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

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
	/**
	 * Method to delete an edge where the specified node is a source or target.
	 * 
	 * @param name The node being removed.
	 */
	@Modifying
    @Transactional
    @Query(value = "DELETE FROM edges e WHERE e.source = ?1 OR e.target = ?1", nativeQuery = true)
    void deleteEdgeByNode(String name);
	
	/**
	 * Method to remove all edges from the database - typically used when all nodes
	 * are first removed.
	 */
	@Modifying
    @Transactional
    @Query(value = "TRUNCATE TABLE edges", nativeQuery = true)
    void deleteAllEdges();
	
	/**
	 * Method to check if the 'nodes' table exists within the database.
	 * 
	 * @return 0 if the 'nodes' table does not exist, >0 otherwise.
	 */
	@Query(value = "SELECT COUNT(*) FROM information_schema.tables WHERE table_name = 'nodes'", nativeQuery = true)
	int tableExists();
	
	/**
	 * Method to create the 'nodes' table.
	 */
	@Modifying
	@Transactional
	@Query(value = "CREATE TABLE nodes (name VARCHAR(255) NOT NULL, x INTEGER, y INTEGER, PRIMARY KEY (name))", nativeQuery = true)
	void createNodesTable();
}
