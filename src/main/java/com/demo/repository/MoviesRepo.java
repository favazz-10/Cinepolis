package com.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.entity.Movies;

@Repository
public interface MoviesRepo extends JpaRepository<Movies, Long> {

}
