package com.example.customerinfo.model;

import javax.persistence.*;

@Entity
@Table(name = "customers")
public class customer {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "phone")
	private String phone;
	
	@Column(name = "active")
	private boolean active;
	
	public customer() {
		
	}
	
	public customer(String name,String address,String phone,boolean active) {
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.active = active;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	
	@Override
	public String toString() {
		return "Customer [id" + id + ", name=" + name + ", address=" + address + ",phone=" + phone + "]";
	}
	
}
