package com.gov.nha.bis.server.dto;

public class healthstatewiseresp {
	private String state_name;
	private String h_total;
	private String f_total;
	private String p_total;
	public String getState_name() {
		return state_name;
	}
	public void setState_name(String state_name) {
		this.state_name = state_name;
	}
	public String getH_total() {
		return h_total;
	}
	public void setH_total(String h_total) {
		this.h_total = h_total;
	}
	public String getF_total() {
		return f_total;
	}
	public void setF_total(String f_total) {
		this.f_total = f_total;
	}
	public String getP_total() {
		return p_total;
	}
	public void setP_total(String p_total) {
		this.p_total = p_total;
	}

}
