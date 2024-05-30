package com.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.demo.entity.Orders;

@Repository
public interface OrdersRepo extends JpaRepository<Orders, Long> {

	@Query(value = "select * from orders where customer_c_id=? ORDER BY o_id DESC", nativeQuery = true)
	public List<Orders> getAllHistory(long id);

}
