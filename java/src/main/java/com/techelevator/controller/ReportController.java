package com.techelevator.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.dao.ReportSqlDAO;
import com.techelevator.model.DamageClaim;
import com.techelevator.model.Report;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@RestController
@CrossOrigin
public class ReportController {
	

	private ReportSqlDAO ReportSqlDAO;
	
	public ReportController(ReportSqlDAO ReportSqlDAO) {
		this.ReportSqlDAO = ReportSqlDAO;
	}
	
	@RequestMapping(path = "/reports", method = RequestMethod.GET)
	public List<Report> getReports() {
		return ReportSqlDAO.listReports();
	}
	
//	@PreAuthorize("isAuthenticated()")
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(path = "/reports", method = RequestMethod.POST)
	public void postReport(@RequestBody Report report) {
		ReportSqlDAO.addReport(report);
	}
	
//	@PreAuthorize("isAuthenticated()")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@RequestMapping(path = "/reports/{report_id}", method = RequestMethod.DELETE)
	public void deleteReport(@PathVariable int report_id) {
		ReportSqlDAO.deleteReportByReportId(report_id);
	}
	
	@RequestMapping(path = "/reports", method = RequestMethod.PUT)
	public void updateReport(@RequestBody Report report) {
		ReportSqlDAO.updateReport(report);
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(path = "/damage-claims", method = RequestMethod.POST)
	public void postDamageClaim(@RequestBody DamageClaim damageClaim) {
		ReportSqlDAO.addDamageClaim(damageClaim);
	}
	
	@RequestMapping(path = "/damage-claims", method = RequestMethod.GET)
	public List<DamageClaim> getDamageClaims() {
		return ReportSqlDAO.listDamageClaims();
	}
	
	// BELOW ENDPOINTS NEVER USED IN THE APP! 
	
//	@PreAuthorize("isAuthenticated()")
	@RequestMapping(path = "/reports/{report_id}/status/{status_id}", method = RequestMethod.PUT)
	public void updateStatus(@PathVariable int report_id, @PathVariable int status_id) {
		ReportSqlDAO.updateStatusByReportId(report_id, status_id);
	}
	
//	@PreAuthorize("isAuthenticated()")
	@RequestMapping(path = "/reports/{report_id}/severity/{severity_id}", method = RequestMethod.PUT)
	public void updateSeverity(@PathVariable int report_id, @PathVariable int severity_id) {
		ReportSqlDAO.updateSeverityByReportId(report_id, severity_id);
	}
	
//	@PreAuthorize("isAuthenticated()")
	@RequestMapping(path = "/reports/{report_id}/inspected/{inspected_date}", method = RequestMethod.PUT)
	public void updateInspected(@PathVariable int report_id, @PathVariable String inspected_date) {
		ReportSqlDAO.updateInspectedByReportId(report_id, inspected_date);
	}
	
//	@PreAuthorize("isAuthenticated()")
	@RequestMapping(path = "/reports/{report_id}/repaired/{repaired_date}", method = RequestMethod.PUT)
	public void updateRepaired(@PathVariable int report_id, @PathVariable String repaired_date) {
		ReportSqlDAO.updateRepairedByReportId(report_id, repaired_date);
	}
}
