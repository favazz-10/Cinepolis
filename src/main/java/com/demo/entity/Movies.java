package com.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Movies {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long mId;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private String image;
	@Column(nullable = false, columnDefinition = "varchar(500)")
	private String details;

	public long getMid() {
		return mId;
	}

	public void setMid(long mid) {
		this.mId = mid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Movies() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Movies(String name, String image, String details) {
		super();
		this.name = name;
		this.image = image;
		this.details = details;
	}

	public Movies(long mid, String name, String image, String details) {
		super();
		this.mId = mid;
		this.name = name;
		this.image = image;
		this.details = details;
	}

	@Override
	public String toString() {
		return "Movies [mid=" + mId + ", name=" + name + ", image=" + image + ", details=" + details + "]";
	}

}
