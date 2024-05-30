package com.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.entity.Customers;

@Repository
public interface CustomersRepo extends JpaRepository<Customers, Long> {

	public Customers findByEmailAndPassword(String email, String password);

}
