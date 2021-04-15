package com.techelevator.model;

public class DamageClaim {
	
	// instance variables

	private int damage_claim_id;
	private int report_id;
	private String full_name;
	private String phone_number;
	private String email;
	private String address;
	private String incident_date;
	private String car;
	private String damage_description;
	
	// getters and setters
	
	public int getDamage_claim_id() {
		return damage_claim_id;
	}
	public void setDamage_claim_id(int damage_claim_id) {
		this.damage_claim_id = damage_claim_id;
	}
	public int getReport_id() {
		return report_id;
	}
	public void setReport_id(int report_id) {
		this.report_id = report_id;
	}
	public String getFull_name() {
		return full_name;
	}
	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}
	public String getPhone_number() {
		return phone_number;
	}
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getIncident_date() {
		return incident_date;
	}
	public void setIncident_date(String incident_date) {
		this.incident_date = incident_date;
	}
	public String getCar() {
		return car;
	}
	public void setCar(String car) {
		this.car = car;
	}
	public String getDamage_description() {
		return damage_description;
	}
	public void setDamage_description(String damage_description) {
		this.damage_description = damage_description;
	}
	
	
	
	
}
