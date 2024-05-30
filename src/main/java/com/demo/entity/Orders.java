package com.demo.entity;

import java.util.Date;
import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
public class Orders {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long oId;

	@ElementCollection
	private List<String> seat;

	@ElementCollection
	private List<Double> price;

	private double total;

	private String movieName;

	@Temporal(value = TemporalType.TIME)
	private Date bookedDate;
	@Temporal(value = TemporalType.TIME)
	private Date showDate;

	private String showTime;
	@OneToOne
	private Customers customer;

	public Orders() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getoId() {
		return oId;
	}

	public void setoId(long oId) {
		this.oId = oId;
	}

	public List<String> getSeat() {
		return seat;
	}

	public void setSeat(List<String> seat) {
		this.seat = seat;
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

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public Date getBookedDate() {
		return bookedDate;
	}

	public void setBookedDate(Date bookedDate) {
		this.bookedDate = bookedDate;
	}

	public Date getShowDate() {
		return showDate;
	}

	public void setShowDate(Date showDate) {
		this.showDate = showDate;
	}

	public String getShowTime() {
		return showTime;
	}

	public void setShowTime(String showTime) {
		this.showTime = showTime;
	}

	public Customers getCustomer() {
		return customer;
	}

	public void setCustomer(Customers customer) {
		this.customer = customer;
	}

	public Orders(long oId, List<String> seat, List<Double> price, double total, String movieName, Date bookedDate,
			Date showDate, String showTime, Customers customer) {
		super();
		this.oId = oId;
		this.seat = seat;
		this.price = price;
		this.total = total;
		this.movieName = movieName;
		this.bookedDate = bookedDate;
		this.showDate = showDate;
		this.showTime = showTime;
		this.customer = customer;
	}

	public Orders(List<String> seat, List<Double> price, double total, String movieName, Date bookedDate, Date showDate,
			String showTime, Customers customer) {
		super();
		this.seat = seat;
		this.price = price;
		this.total = total;
		this.movieName = movieName;
		this.bookedDate = bookedDate;
		this.showDate = showDate;
		this.showTime = showTime;
		this.customer = customer;
	}

	@Override
	public String toString() {
		return "Orders [oId=" + oId + ", seat=" + seat + ", price=" + price + ", total=" + total + ", movieName="
				+ movieName + ", bookedDate=" + bookedDate + ", showDate=" + showDate + ", showTime=" + showTime
				+ ", customer=" + customer + "]";
	}

}
