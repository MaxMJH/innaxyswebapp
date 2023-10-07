package com.mjh.innaxyswebapp.data;

import org.springframework.data.jpa.repository.JpaRepository;

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

}
