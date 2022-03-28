package com.gov.nha.bis.server.dto;

public class AccumlateResp {
	private String categoryid;
	private String category_name;
	private String total_count;
	private String accumlate_count;
	public String getCategoryid() {
		return categoryid;
	}
	public void setCategoryid(String categoryid) {
		this.categoryid = categoryid;
	}
	public String getCategory_name() {
		return category_name;
	}
	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}
	public String getTotal_count() {
		return total_count;
	}
	public void setTotal_count(String total_count) {
		this.total_count = total_count;
	}
	public String getAccumlate_count() {
		return accumlate_count;
	}
	public void setAccumlate_count(String accumlate_count) {
		this.accumlate_count = accumlate_count;
	}
	
}
