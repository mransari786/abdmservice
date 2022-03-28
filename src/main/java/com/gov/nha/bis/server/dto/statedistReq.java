package com.gov.nha.bis.server.dto;

public class statedistReq {
	private String type;
	private String state_code;
	private String district_code;
	private String rpttype;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDistrict_code() {
		return district_code;
	}
	public void setDistrict_code(String district_code) {
		this.district_code = district_code;
	}
	public String getState_code() {
		return state_code;
	}
	public void setState_code(String state_code) {
		this.state_code = state_code;
	}
	public String getRpttype() {
		return rpttype;
	}
	public void setRpttype(String rpttype) {
		this.rpttype = rpttype;
	}
	
}
