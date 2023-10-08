package com.mjh.innaxyswebapp.data;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.mjh.innaxyswebapp.model.Edge;

/**
 * This interface allows for communication with the PostgreSQL database. 
 * It is likely for this particular class to be utilised via a service class
 * so as to apply CRUD. Further, if a primary key that is passed exists
 * within the relevant table, a {@link com.mjh.innaxyswebapp.model.Edge} will
 * be returned, populated with stored data.
 * 
 * @param <Edge> Returning node if the primary key value exists.
 * @param <Long> Primary key of a relevant edge (ID of the edge).
 */
public interface EdgeRepository extends JpaRepository<Edge, Long> {
	/**
	 * Method to find edges based on source node.
	 * 
	 * @param source The source node.
	 */
	@Modifying
    @Transactional
    @Query(value = "SELECT e FROM edges e WHERE e.source = ?1", nativeQuery = true)
    List<Edge> findEdgeBySource(String source);
	
	/**
	 * Method to find edges based on target node.
	 * 
	 * @param target The target node.
	 */
	@Modifying
    @Transactional
    @Query(value = "SELECT e FROM edges e WHERE e.target = ?1", nativeQuery = true)
    List<Edge> findEdgeByTarget(String target);
	
	/**
	 * Method to find edge based on source and target node.
	 * 
	 * @param source The source node.
	 * @param target The target node.
	 */
	@Modifying
    @Transactional
    @Query(value = "SELECT e FROM edges e WHERE e.source = ?1 AND e.target = ?2", nativeQuery = true)
    Optional<Edge> findEdgeBySourceAndTarget(String source, String target);
	
	/**
	 * Method to find edge based on target and source node.
	 * 
	 * @param target The target node.
	 * @param source The source node.
	 */
	@Modifying
    @Transactional
    @Query(value = "SELECT e FROM edges e WHERE e.target = ?1 AND e.source = ?2", nativeQuery = true)
    Optional<Edge> findEdgeByTargetAndSource(String target, String source);
	
	/**
	 * Method to delete an edge where the specified node is a source.
	 * 
	 * @param name The node being removed.
	 */
	@Modifying
    @Transactional
    @Query(value = "DELETE FROM edges e WHERE e.source = ?1", nativeQuery = true)
    void deleteEdgeBySource(String name);
	
	/**
	 * Method to delete an edge where the specified node is a target.
	 * 
	 * @param name The node being removed.
	 */
	@Modifying
    @Transactional
    @Query(value = "DELETE FROM edges e WHERE e.target = ?1", nativeQuery = true)
    void deleteEdgeByTarget(String name);
	
	/**
	 * Method to check if the 'edges' table exists within the database.
	 * 
	 * @return 0 if the 'edges' table does not exist, >0 otherwise.
	 */
	@Query(value = "SELECT COUNT(*) FROM information_schema.tables WHERE table_name = 'edges'", nativeQuery = true)
	int tableExists();
	
	/**
	 * Method to create the 'edges' table.
	 */
	@Modifying
	@Transactional
	@Query(value = "CREATE TABLE edges (id BIGSERIAL NOT NULL, distance INTEGER, source VARCHAR(255), target VARCHAR(255), PRIMARY KEY (id))", nativeQuery = true)
	void createEdgesTable();
	
	/**
	 * Method to set the source constraint for the 'edges' table.
	 */
	@Modifying
	@Transactional
	@Query(value = "ALTER TABLE IF EXISTS edges ADD CONSTRAINT FKgsoyaq0ea46pkspcqhge4ac14 FOREIGN KEY (source) REFERENCES nodes", nativeQuery = true)
	void createSourceConstraint();
	
	/**
	 * Method to set the target constraint for the 'edges' table.
	 */
	@Modifying
	@Transactional
	@Query(value = "ALTER TABLE IF EXISTS edges ADD CONSTRAINT FK5yt38t69hap9tbg042pt0qaaj FOREIGN KEY (target) REFERENCES nodes", nativeQuery = true)
	void createTargetConstraint();
}
