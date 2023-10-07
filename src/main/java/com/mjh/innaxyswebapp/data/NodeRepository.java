package com.mjh.innaxyswebapp.data;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mjh.innaxyswebapp.model.Node;

public interface NodeRepository extends JpaRepository<Node, String> {

}
