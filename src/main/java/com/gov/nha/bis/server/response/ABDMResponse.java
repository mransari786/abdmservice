package com.gov.nha.bis.server.response;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;
import java.util.List;

import com.gov.nha.bis.server.dto.AccumlateResp;
import com.gov.nha.bis.server.dto.HealthID_Gender;
import com.gov.nha.bis.server.dto.HealthIdHResp;
import com.gov.nha.bis.server.dto.abdmResp;
import com.gov.nha.bis.server.dto.abhalinkedresp;
import com.gov.nha.bis.server.dto.healthidcreatedstatewise;
import com.gov.nha.bis.server.dto.healthidpartnerwiseres;
import com.gov.nha.bis.server.dto.healthres;
import com.gov.nha.bis.server.dto.healthstatewiseresp;
import com.gov.nha.bis.server.dto.partner_resp;
import com.gov.nha.bis.server.dto.partnerlinkResp;


public class ABDMResponse {
public static SimpleDateFormat mdyFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX:ss");
	
	public static String getABDMResponse(String txtno,String status) {
		JSONObject sRturnResponse = new JSONObject();
		
		
		sRturnResponse.put("ts", mdyFormat.format(new Date()));
		sRturnResponse.put("status", status);
		sRturnResponse.put("txnno", txtno);
		
		
		
		return sRturnResponse.toString();
	}
	public static String getStateCodeResponse(String resp,String status)
	{

		JSONObject sRturnResponse = new JSONObject();
		
		
		sRturnResponse.put("ts", mdyFormat.format(new Date()));
		sRturnResponse.put("status", status);
		sRturnResponse.put("state_code", resp);
		
		return sRturnResponse.toString();
	}
	public static String getABDMDataResponse(healthres resp,String status)
	{

		JSONObject sRturnResponse = new JSONObject();
		
		
		sRturnResponse.put("ts", mdyFormat.format(new Date()));
		sRturnResponse.put("status", status);
		sRturnResponse.put("today", resp.getToday());
		sRturnResponse.put("total1", resp.getTotal1());
		sRturnResponse.put("total2", resp.getTotal2());
		return sRturnResponse.toString();
	}
	
	public static String getABDMServiceResponse(List<abdmResp> resp,String status)
	{

		JSONObject sRturnResponse = new JSONObject();
		
		
		sRturnResponse.put("ts", mdyFormat.format(new Date()));
		sRturnResponse.put("status", status);
		sRturnResponse.put("list", resp);
		
		return sRturnResponse.toString();
	}
	public static String getPartnerResponse(List<partner_resp> resp,String status)
	{

		JSONObject sRturnResponse = new JSONObject();
		
		
		sRturnResponse.put("ts", mdyFormat.format(new Date()));
		sRturnResponse.put("status", status);
		sRturnResponse.put("list", resp);
		
		return sRturnResponse.toString();
	}
	public static String getABHALinkResponse(List<abhalinkedresp> resp,String status)
	{

		JSONObject sRturnResponse = new JSONObject();
		
		
		sRturnResponse.put("ts", mdyFormat.format(new Date()));
		sRturnResponse.put("status", status);
		sRturnResponse.put("list", resp);
		
		return sRturnResponse.toString();
	}
	public static String getPartnerLinkedResponse(List<partnerlinkResp> resp,String status)
	{

		JSONObject sRturnResponse = new JSONObject();
		
		
		sRturnResponse.put("ts", mdyFormat.format(new Date()));
		sRturnResponse.put("status", status);
		sRturnResponse.put("list", resp);
		
		return sRturnResponse.toString();
	}
	public static String getUpdateDateResponse(String resp,String status)
	{

		JSONObject sRturnResponse = new JSONObject();
		
		
		sRturnResponse.put("ts", mdyFormat.format(new Date()));
		sRturnResponse.put("status", status);
		sRturnResponse.put("date", resp);
		
		return sRturnResponse.toString();
	}
	public static String getAccumulateResponse(List<AccumlateResp> resp,String status)
	{

		JSONObject sRturnResponse = new JSONObject();
		
		
		sRturnResponse.put("ts", mdyFormat.format(new Date()));
		sRturnResponse.put("status", status);
		sRturnResponse.put("list", resp);
		
		return sRturnResponse.toString();
	}
	public static String getHealthIDHResponse(List<HealthIdHResp> resp,String status)
	{

		JSONObject sRturnResponse = new JSONObject();
		
		
		sRturnResponse.put("ts", mdyFormat.format(new Date()));
		sRturnResponse.put("status", status);
		sRturnResponse.put("list", resp);
		
		return sRturnResponse.toString();
	}
	
	public static String getHealthIDPartnerResp(List<healthidpartnerwiseres> resp,String status)
	{

		JSONObject sRturnResponse = new JSONObject();
		
		
		sRturnResponse.put("ts", mdyFormat.format(new Date()));
		sRturnResponse.put("status", status);
		sRturnResponse.put("list", resp);
		
		return sRturnResponse.toString();
	}
	public static String getHealthIDCreatedStateResp(List<healthidcreatedstatewise> resp,String status)
	{

		JSONObject sRturnResponse = new JSONObject();
		
		
		sRturnResponse.put("ts", mdyFormat.format(new Date()));
		sRturnResponse.put("status", status);
		sRturnResponse.put("list", resp);
		
		return sRturnResponse.toString();
	}
	public static String getHealthIDGenderResponse(HealthID_Gender resp,String status)
	{

		JSONObject sRturnResponse = new JSONObject();
		
		
		sRturnResponse.put("ts", mdyFormat.format(new Date()));
		sRturnResponse.put("status", status);
		sRturnResponse.put("text", resp.getText());
		sRturnResponse.put("total", resp.getTotal());
		
		return sRturnResponse.toString();
	}
	public static String getHealthDataResponse(healthres resp,String status)
	{

		JSONObject sRturnResponse = new JSONObject();
		
		
		sRturnResponse.put("ts", mdyFormat.format(new Date()));
		sRturnResponse.put("status", status);
		sRturnResponse.put("today", resp.getToday());
		sRturnResponse.put("total1", resp.getTotal1());
		sRturnResponse.put("total2", resp.getTotal2());
		return sRturnResponse.toString();
	}
	public static String getHealthStateWiseDataResponse(healthstatewiseresp resp,String status)
	{

		JSONObject sRturnResponse = new JSONObject();
		
		
		sRturnResponse.put("ts", mdyFormat.format(new Date()));
		sRturnResponse.put("status", status);
		sRturnResponse.put("statename", resp.getState_name());
		sRturnResponse.put("h_total", resp.getH_total());
		sRturnResponse.put("f_total", resp.getF_total());
		sRturnResponse.put("p_total", resp.getP_total());
		return sRturnResponse.toString();
	}
}
