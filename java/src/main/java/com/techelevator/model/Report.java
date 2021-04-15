package com.techelevator.model;

import java.math.BigDecimal;

public class Report {
	
	// data members

	private int report_id;
	private String username;
	private BigDecimal lat;
	private BigDecimal lng;
	private String location;
	private String user_severity;
	private String reported;
	private String inspected;
	private String repaired;
	private int status;
	private int severity;
	private String img;
	
	// Getters and Setters
	
	public int getReport_id() {
		return report_id;
	}
	public void setReport_id(int report_id) {
		this.report_id = report_id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public BigDecimal getLat() {
		return lat;
	}
	public void setLat(BigDecimal lat) {
		this.lat = lat;
	}
	public BigDecimal getLng() {
		return lng;
	}
	public void setLng(BigDecimal lng) {
		this.lng = lng;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getUser_severity() {
		return user_severity;
	}
	public void setUser_severity(String user_severity) {
		this.user_severity = user_severity;
	}
	public String getReported() {
		return reported;
	}
	public void setReported(String reported) {
		this.reported = reported;
	}
	public String getInspected() {
		return inspected;
	}
	public void setInspected(String inspected) {
		this.inspected = inspected;
	}
	public String getRepaired() {
		return repaired;
	}
	public void setRepaired(String repaired) {
		this.repaired = repaired;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getSeverity() {
		return severity;
	}
	public void setSeverity(int severity) {
		this.severity = severity;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	

	
}
