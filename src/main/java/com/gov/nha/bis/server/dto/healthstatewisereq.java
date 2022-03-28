package com.gov.nha.bis.server.dto;

public class healthstatewisereq {
	
	private String state_code;
	private String district_code;
	public String getState_code() {
		return state_code;
	}
	public void setState_code(String state_code) {
		this.state_code = state_code;
	}
	public String getDistrict_code() {
		return district_code;
	}
	public void setDistrict_code(String district_code) {
		this.district_code = district_code;
	}
	
}
