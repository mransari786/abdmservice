package com.gov.nha.bis.server.dto;

import java.io.Serializable;
import java.util.Date;

public class DashParam  implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6528609704748149682L;
	private int  state_code;
	private String district_code;
	private int block_code;
	private String village_code;
	private String towncode;
	private String wardid;
	private String rural_urban;
	private String gender;
	public int age;
	private Long total_count;
	private int category;
	private Date created_date;
	private String created_by;
	private String txnno;
	public int getState_code() {
		return state_code;
	}
	public void setState_code(int state_code) {
		this.state_code = state_code;
	}
	public String getDistrict_code() {
		return district_code;
	}
	public void setDistrict_code(String district_code) {
		this.district_code = district_code;
	}
	public int getBlock_code() {
		return block_code;
	}
	public void setBlock_code(int block_code) {
		this.block_code = block_code;
	}
	public String getVillage_code() {
		return village_code;
	}
	public void setVillage_code(String village_code) {
		this.village_code = village_code;
	}
	public String getTowncode() {
		return towncode;
	}
	public void setTowncode(String towncode) {
		this.towncode = towncode;
	}
	public String getWardid() {
		return wardid;
	}
	public void setWardid(String wardid) {
		this.wardid = wardid;
	}
	public String getRural_urban() {
		return rural_urban;
	}
	public void setRural_urban(String rural_urban) {
		this.rural_urban = rural_urban;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Long getTotal_count() {
		return total_count;
	}
	public void setTotal_count(Long total_count) {
		this.total_count = total_count;
	}
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	
	public String getCreated_by() {
		return created_by;
	}
	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}
	public Date getCreated_date() {
		return created_date;
	}
	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}
	public String getTxnno() {
		return txnno;
	}
	public void setTxnno(String txnno) {
		this.txnno = txnno;
	}
	
	

}
