package com.techelevator.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.techelevator.model.DamageClaim;
import com.techelevator.model.Report;
import com.techelevator.model.User;

@Service
public class ReportSqlDAO implements ReportDAO {

	private JdbcTemplate jdbcTemplate;

	public ReportSqlDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Report> listReports() {

		List<Report> reports = new ArrayList<>();
		String sql = "select * from reports";

		SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
		while(results.next()) {
			Report report = mapRowToReport(results);
			reports.add(report);
		}
		return reports;
	}

	@Override
	public void addReport(Report report) {

		String sql = "INSERT INTO reports "
				+ "(username, lat, lng, location, user_severity, reported, status, severity)"
				+ "VALUES (?,?,?,?,?,?,?,?)";

		jdbcTemplate.update(sql, report.getUsername(), report.getLat(), 
				report.getLng(), report.getLocation(), report.getUser_severity(), report.getReported(), 1, 0);
	}

	@Override
	public void deleteReportByReportId(int report_id) {

		String sql = "DELETE FROM reports WHERE report_id = ?";
		jdbcTemplate.update(sql, report_id);

	}
	
	@Override
	public void updateReport(Report report) {
		
		String sql = "UPDATE reports SET inspected = ?, repaired = ?, "
					+ "status = ?, severity = ? WHERE report_id = ?";
		jdbcTemplate.update(sql, report.getInspected(), report.getRepaired(),
				report.getStatus(), report.getSeverity(), report.getReport_id());
		
	}
	
	@Override
	public void addDamageClaim(DamageClaim damageClaim) {
		
		String sql = "INSERT INTO damage_claim "
		+ "(report_id, full_name, phone_number, email, address, incident_date, car, damage_description) "
		+ "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?)";
		
		jdbcTemplate.update(sql, damageClaim.getReport_id(), damageClaim.getFull_name(), damageClaim.getPhone_number(), 
		damageClaim.getEmail(), damageClaim.getAddress(), damageClaim.getIncident_date(), damageClaim.getCar(), damageClaim.getDamage_description());
		
	}
	
	@Override
	public List<DamageClaim> listDamageClaims() {
		List<DamageClaim> damageClaims = new ArrayList<>();
		String sql = "SELECT * FROM damage_claim";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
		
		while(results.next()) {
			DamageClaim damageClaim = mapRowToDamageClaim(results);
			damageClaims.add(damageClaim);
		}
		return damageClaims;
	}
	
	
	@Override
	public void updateStatusByReportId(int report_id, int status_id) {

		String sql = "UPDATE reports SET status = ? WHERE report_id = ?";
		jdbcTemplate.update(sql, status_id, report_id);
	}

	@Override
	public void updateSeverityByReportId(int report_id, int severity_id) {

		String sql = "UPDATE reports SET severity = ? WHERE report_id = ?";
		jdbcTemplate.update(sql, severity_id, report_id);
	}

	@Override
	public void updateInspectedByReportId(int report_id, String inspected_date) {

		String sql = "UPDATE reports SET inspected = ? WHERE report_id = ?";
		jdbcTemplate.update(sql, inspected_date, report_id);
	}

	@Override
	public void updateRepairedByReportId(int report_id, String repaired_date) {

		String sql = "UPDATE reports SET repaired = ? WHERE report_id = ?";
		jdbcTemplate.update(sql, repaired_date, report_id);
	}

	// helper methods

	private Report mapRowToReport(SqlRowSet rs) {
		Report report = new Report();
		report.setReport_id(rs.getInt("report_id"));
		report.setUsername(rs.getString("username"));
		report.setLat(rs.getBigDecimal("lat"));
		report.setLng(rs.getBigDecimal("lng"));
		report.setLocation(rs.getString("location"));
		report.setUser_severity(rs.getString("user_severity"));
		report.setReported(rs.getString("reported"));
		report.setInspected(rs.getString("inspected"));
		report.setRepaired(rs.getString("repaired"));
		report.setStatus(rs.getInt("status"));
		report.setSeverity(rs.getInt("severity"));
		report.setImg(rs.getString("img"));
		return report;
	}
	
	private DamageClaim mapRowToDamageClaim(SqlRowSet rs) {
		DamageClaim damageClaim = new DamageClaim();
		damageClaim.setDamage_claim_id(rs.getInt("damage_claim_id"));
		damageClaim.setReport_id(rs.getInt("report_id"));
		damageClaim.setFull_name(rs.getString("full_name"));
		damageClaim.setPhone_number(rs.getString("phone_number"));
		damageClaim.setEmail(rs.getString("email"));
		damageClaim.setAddress(rs.getString("address"));
		damageClaim.setIncident_date(rs.getString("incident_date"));
		damageClaim.setCar(rs.getString("car"));
		damageClaim.setDamage_description(rs.getString("damage_description"));
		return damageClaim;
		
	}



}
