package com.demo.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Seats {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long sId;
	@ElementCollection
	private List<String> seatNo;
	@ElementCollection
	private List<Double> price;

	private double total;

	@ManyToOne
	private Customers customers;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Timings timing;

	public Seats() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getsId() {
		return sId;
	}

	public void setsId(long sId) {
		this.sId = sId;
	}

	public List<String> getSeatNo() {
		return seatNo;
	}

	public void setSeatNo(List<String> seatNo) {
		this.seatNo = seatNo;
	}

	public List<Double> getPrice() {
		return price;
	}

	public void setPrice(List<Double> price) {
		this.price = price;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public Customers getCustomers() {
		return customers;
	}

	public void setCustomers(Customers customers) {
		this.customers = customers;
	}

	public Timings getTiming() {
		return timing;
	}

	public void setTiming(Timings timing) {
		this.timing = timing;
	}

	public Seats(long sId, List<String> seatNo, List<Double> price, double total, Customers customers, Timings timing) {
		super();
		this.sId = sId;
		this.seatNo = seatNo;
		this.price = price;
		this.total = total;
		this.customers = customers;
		this.timing = timing;
	}

	public Seats(List<String> seatNo, List<Double> price, double total, Customers customers, Timings timing) {
		super();
		this.seatNo = seatNo;
		this.price = price;
		this.total = total;
		this.customers = customers;
		this.timing = timing;
	}

	@Override
	public String toString() {
		return "Seats [sId=" + sId + ", seatNo=" + seatNo + ", price=" + price + ", total=" + total + ", customers="
				+ customers + ", timing=" + timing + "]";
	}

}
