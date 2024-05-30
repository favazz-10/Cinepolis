package com.demo.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Customers {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long cId;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false, unique = true)
	private String email;
	@Column(nullable = false)
	private String password;

	@OneToMany(mappedBy = "customers", fetch = FetchType.EAGER)
	private List<Seats> seat;
	@OneToOne(mappedBy = "customer", fetch = FetchType.EAGER)
	private Orders orders;

	public Customers() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getcId() {
		return cId;
	}

	public void setcId(long cId) {
		this.cId = cId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Seats> getSeat() {
		return seat;
	}

	public void setSeat(List<Seats> seat) {
		this.seat = seat;
	}

	public Orders getOrders() {
		return orders;
	}

	public void setOrders(Orders orders) {
		this.orders = orders;
	}

	public Customers(long cId, String name, String email, String password, List<Seats> seat, Orders orders) {
		super();
		this.cId = cId;
		this.name = name;
		this.email = email;
		this.password = password;
		this.seat = seat;
		this.orders = orders;
	}

	public Customers(String name, String email, String password, List<Seats> seat, Orders orders) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.seat = seat;
		this.orders = orders;
	}

	@Override
	public String toString() {
		return "Customers [cId=" + cId + ", name=" + name + ", email=" + email + ", password=" + password + ", seat="
				+ seat + ", orders=" + orders + "]";
	}

}
