package com.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer>{

}
