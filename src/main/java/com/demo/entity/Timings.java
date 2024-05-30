package com.demo.entity;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
public class Timings {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long tId;
	@Temporal(value = TemporalType.DATE)
	private Date showDate;

	private String showTime;

	@OneToMany(mappedBy = "timing", fetch = FetchType.EAGER)
	private List<Seats> seat;

	public Timings() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long gettId() {
		return tId;
	}

	public void settId(long tId) {
		this.tId = tId;
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

	public List<Seats> getSeat() {
		return seat;
	}

	public void setSeat(List<Seats> seat) {
		this.seat = seat;
	}

	public Timings(long tId, Date showDate, String showTime, List<Seats> seat) {
		super();
		this.tId = tId;
		this.showDate = showDate;
		this.showTime = showTime;
		this.seat = seat;
	}

	public Timings(Date showDate, String showTime, List<Seats> seat) {
		super();
		this.showDate = showDate;
		this.showTime = showTime;
		this.seat = seat;
	}

	@Override
	public String toString() {
		return "Timings [tId=" + tId + ", showDate=" + showDate + ", showTime=" + showTime + ", seat=" + seat + "]";
	}

}
