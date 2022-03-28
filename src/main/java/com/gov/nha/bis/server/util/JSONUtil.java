package com.gov.nha.bis.server.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


/**
 * 
 * @author Alim
 * 
 */
public class JSONUtil {

	public Map<String, Object> getRequestHeaderMap(HttpServletRequest request) {
		Map<String,Object> requestHeaderMap = new HashMap<String,Object>();
		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String key = (String) headerNames.nextElement();
			String value = request.getHeader(key);
			requestHeaderMap.put(key, value);
		}
		return requestHeaderMap;
	}
	
	
	public static JSONResponse setJSONResonse(String message, boolean status,Object data) {

		JSONResponse jsonResponse = new JSONResponse();
		if (status) {
			jsonResponse.setStatus(MessageConstant.RESPONSE_SUCCESS);
		} else {
			jsonResponse.setStatus(MessageConstant.RESPONSE_FAILED);
		}

		jsonResponse.setMessage(message);
		jsonResponse.setData(data);
		return jsonResponse;
	}
	public static JSONResponse setJSONResonse(String message, boolean status,Object data, Object map) {

		JSONResponse jsonResponse = new JSONResponse();
		if (status) {
			jsonResponse.setStatus(MessageConstant.RESPONSE_SUCCESS);
		} else {
			jsonResponse.setStatus(MessageConstant.RESPONSE_FAILED);
		}

		jsonResponse.setMessage(message);
		jsonResponse.setData(data);
		return jsonResponse;
	}
	
	/*
	 * public static JSONResponse setJSONResonse(String string, boolean b, String
	 * token, MstUserDetail user) { JSONResponse jsonResponse = new JSONResponse();
	 * jsonResponse.setStatus(MessageConstant.RESPONSE_SUCCESS);
	 * jsonResponse.setMessage(string); jsonResponse.setData(token); return
	 * jsonResponse; }
	 */
	
	/*public static JSONResponse setJSONResonse(String dataLable,
			List<?> dataList, Map<?, ?> dataList2) {

		JSONResponse jsonResponse = new JSONResponse();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if (dataList != null) {
			dataMap.put(dataLable, dataList);
		} else {
			dataMap.put(dataLable, dataList2);
		}
		jsonResponse.setStatus(IrctcConstants.RESPONSE_SUCCESS);
		jsonResponse.setData(dataMap);
		jsonResponse.setMessage("");
		return jsonResponse;
	}

	public static UserDetailsJSONResponse setJSONResonse(String success, boolean b,
			String token, User user) {
		UserDetailsJSONResponse jsonResponse=new UserDetailsJSONResponse();
		jsonResponse.setMessage(IrctcConstants.success);
		jsonResponse.setStatus(IrctcConstants.success);
		jsonResponse.setData(token);
		jsonResponse.setUserDetails(user);
		return jsonResponse;
	}
	public static JSONResponse setJSONResonse(String message, boolean status,Object data) {

		JSONResponse jsonResponse = new JSONResponse();
		if (status) {
			jsonResponse.setStatus(IrctcConstants.RESPONSE_SUCCESS);
		} else {
			jsonResponse.setStatus(IrctcConstants.RESPONSE_FAILED);
		}

		jsonResponse.setMessage(message);
		jsonResponse.setData(data);
		return jsonResponse;
	}
	public static JSONResponse setJSONResonseWithWarning(String message, Object data) {

		JSONResponse jsonResponse = new JSONResponse();

		jsonResponse.setStatus(IrctcConstants.RESPONSE_WARNING);

		jsonResponse.setMessage(message);
		jsonResponse.setData(data);
		return jsonResponse;
	}

	public static JSONResponse setJSONResonseWithDuplicate(String message,boolean status,Object data) {
		JSONResponse jsonResponse = new JSONResponse();
		jsonResponse.setStatus(IrctcConstants.RESPONSE_DUPLICATE);
		jsonResponse.setMessage(message);
		jsonResponse.setData(data);
		return jsonResponse;	
	}


	public static JSONResponse setJSONResonse(String string, boolean b,
			String token, IrUser user) {
		JSONResponse jsonResponse = new JSONResponse();
		jsonResponse.setStatus(IrctcConstants.RESPONSE_SUCCESS);
		jsonResponse.setMessage(string);
		jsonResponse.setData(token);
		//jsonResponse.setUserDetails(user);
		return jsonResponse;
	}
	public static JSONResponse setJSONResonse(String string, boolean b,
			String token,GuestUser user) {
		JSONResponse jsonResponse = new JSONResponse();
		jsonResponse.setStatus(IrctcConstants.RESPONSE_SUCCESS);
		jsonResponse.setMessage(string);
		jsonResponse.setData(token);
		//jsonResponse.setUserDetails(user);
		return jsonResponse;
	}

	public static JSONResponse getGenericJSonResponse(
			Object jsonlist) {
		JSONResponse jsonResponse=null;
		if (jsonlist != null ) {
			jsonResponse = JSONUtil.setJSONResonse("", true,jsonlist);
		} 
		else {
			jsonResponse = JSONUtil.setJSONResonse("blank",false, "");
		}
		return jsonResponse;
	}
	
	public static UserDetailsJSONResponse setUserJSONResonse(String message, boolean status,Object data) {

		UserDetailsJSONResponse jsonResponse = new UserDetailsJSONResponse();
		if (status) {
			jsonResponse.setStatus("SUCCESS");
		} else {
			jsonResponse.setStatus("FAILURE");
		}

		jsonResponse.setMessage(message);
		jsonResponse.setData(data);
		return jsonResponse;
	}*/
}