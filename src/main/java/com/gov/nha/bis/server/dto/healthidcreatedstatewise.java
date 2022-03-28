package com.gov.nha.bis.server.dto;

public class healthidcreatedstatewise {
	private String state_name;
	private String today;
	private String overall;
	private String aadhaarper;
	private String populationper;
	public String getState_name() {
		return state_name;
	}
	public void setState_name(String state_name) {
		this.state_name = state_name;
	}
	public String getToday() {
		return today;
	}
	public void setToday(String today) {
		this.today = today;
	}
	public String getOverall() {
		return overall;
	}
	public void setOverall(String overall) {
		this.overall = overall;
	}
	public String getAadhaarper() {
		return aadhaarper;
	}
	public void setAadhaarper(String aadhaarper) {
		this.aadhaarper = aadhaarper;
	}
	public String getPopulationper() {
		return populationper;
	}
	public void setPopulationper(String populationper) {
		this.populationper = populationper;
	}

}
