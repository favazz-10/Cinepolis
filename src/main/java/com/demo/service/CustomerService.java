package com.demo.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.entity.Customers;
import com.demo.entity.Movies;
import com.demo.entity.Orders;
import com.demo.entity.Seats;
import com.demo.entity.Timings;
import com.demo.repository.CustomersRepo;
import com.demo.repository.MoviesRepo;
import com.demo.repository.OrdersRepo;
import com.demo.repository.SeatsRepo;

@Service
public class CustomerService {

	@Autowired
	private CustomersRepo cRepo;
	@Autowired
	private OrdersRepo oRepo;
	@Autowired
	private SeatsRepo sRepo;
	@Autowired
	private MoviesRepo mRepo;

	public int save(Customers cust) {
		cRepo.save(cust);
		return 1;
	}

	public Customers Login(String email, String password) {
		Customers cust = cRepo.findByEmailAndPassword(email, password);
		return cust;
	}

	public int saveSeat(Seats seat, Customers customer, Date date, String time) {
		List<Seats> list = new ArrayList<Seats>();
		list.add(seat);
		customer.setSeat(list);
		Timings t = new Timings();
		t.setShowDate(date);
		t.setShowTime(time);
		t.setSeat(list);

		seat.setTiming(t);
		seat.setTiming(t);
		seat.setCustomers(customer);
		sRepo.save(seat);
		return 1;
	}

	public List<Seats> getSeats(long id) {
		List<Seats> list = sRepo.getAllSeat(id);
		return list;
	}

	public List<Customers> getAll() {
		List<Customers> findAll = cRepo.findAll();
		return findAll;
	}

	public Orders saveHistory(Orders orders, Customers customer) {
		customer.setOrders(orders);
		Orders save = oRepo.save(orders);
		return save;
	}

	public List<Orders> getAllHistory(long id) {
		List<Orders> list = oRepo.getAllHistory(id);
		return list;
	}

	public List<Seats> getAllSeat(LocalDate date, String time) {
		List<Seats> list = sRepo.getAllByDate(date, time);
		return list;
	}

	public void delete(long id) {
		sRepo.deleteById(id);
	}

	public int updateDetail(Customers customer) {
		cRepo.save(customer);
		return 1;
	}

	public List<Movies> getAllMovie() {
		List<Movies> list = this.mRepo.findAll();
		return list;
	}
}
