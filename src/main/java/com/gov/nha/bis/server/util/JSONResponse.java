package com.gov.nha.bis.server.util;

import org.springframework.stereotype.Component;

@Component
public class JSONResponse {
	String status;
	String message;
	Object data;
	Object userDetails;
	
	public JSONResponse() {
		// TODO Auto-generated constructor stub
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "JSONResponse [status=" + status + ", message=" + message
				+", userDetails=" + userDetails+ ", data=" + data +  "]";
	}

	public Object getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(Object userDetails) {
		this.userDetails = userDetails;
	}

	

}

