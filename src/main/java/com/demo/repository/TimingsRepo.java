package com.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.entity.Timings;

@Repository
public interface TimingsRepo extends JpaRepository<Timings, Long> {

}
