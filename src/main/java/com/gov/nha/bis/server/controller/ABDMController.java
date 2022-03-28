package com.gov.nha.bis.server.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gov.nha.bis.server.dao.ABDMDao;
import com.gov.nha.bis.server.dto.abdmResp;
import com.gov.nha.bis.server.dto.abhalinkedresp;
import com.gov.nha.bis.server.dto.healthidcreatedstatewise;
import com.gov.nha.bis.server.dto.healthres;
import com.gov.nha.bis.server.dto.partner_resp;
import com.gov.nha.bis.server.dto.partnerlinkResp;
import com.gov.nha.bis.server.dto.partnerlinkedReq;
import com.gov.nha.bis.server.dto.statecodeReq;
import com.gov.nha.bis.server.dto.statedistReq;
import com.gov.nha.bis.server.response.ABDMResponse;



@RestController
@RequestMapping("/api")
@CrossOrigin
public class ABDMController extends NhaBisBaseController {
	
	@Autowired
	public ABDMDao abdmdao;
	private static final Logger logger = LoggerFactory.getLogger(ABDMController.class);
	
	@ResponseBody
	@PostMapping(value ="/dashboard/healthdata/1.0")
	public String GetHealthData(@RequestBody  statedistReq  AccumParam) {
		logger.error("In Beneficiary API Get Data  of erupiService Controller");
		try {
			//System.out.println(AccumParam.getType());
			String strType=AccumParam.getType();
			 if(!strType.equals(""))
			{
				healthres donList= abdmdao.getHealthData(AccumParam);
				
				return ABDMResponse.getABDMDataResponse(donList, "true");				
			}
			
			else
			{
				return  ABDMResponse.getABDMResponse("", "false");	
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("kycUpdateProcessData====="+e);
			System.out.println(e);
			return  ABDMResponse.getABDMResponse("", "false");
		}
	}
	
	@ResponseBody
	@PostMapping(value ="/dashboard/statedist/1.0")
	public String GetStateDist(@RequestBody  statedistReq  Req) {
		logger.error("In Beneficiary API Get Data  of erupiService Controller");
		try {
			//System.out.println(Req.getType());
			String strType=Req.getType();
			if(strType.equals("SM"))
			{
			 List<abdmResp> donList= abdmdao.getStateDistMaster(Req);
				
				return ABDMResponse.getABDMServiceResponse(donList, "true");				
			}
			else if(strType.equals("DM") )
			{
				 List<abdmResp> donList= abdmdao.getStateDistMaster(Req);
					
					return ABDMResponse.getABDMServiceResponse(donList, "true");				
			}
				
			else
			{
				return  ABDMResponse.getABDMResponse("", "false");	
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("kycUpdateProcessData====="+e);
			System.out.println(e);
			return  ABDMResponse.getABDMResponse("", "false");
		}
	}
	
	@ResponseBody
	@PostMapping(value ="/dashboard/healthh/1.0")
	public String GetHealthTrendWise(@RequestBody  statedistReq  AccumParam) {
		logger.error("In Beneficiary API Get Data  of erupiService Controller");
		try {
			//System.out.println(AccumParam.getType());
			String strType=AccumParam.getType();
			if(strType.equals("HG") ||strType.equals("HCT") ||strType.equals("FO") ||strType.equals("HFRS") ||strType.equals("HPRS") ||strType.equals("HFRAF")||strType.equals("HPRAF") ||strType.equals("FM") ||strType.equals("PM") ||strType.equals("PE"))
			{
                List<abdmResp> donList= abdmdao.getHealthDataList(AccumParam);
				
				return ABDMResponse.getABDMServiceResponse(donList, "true");
			
			}
			
			else if(strType.equals("P"))
			{
                List<partner_resp> donList= abdmdao.getPartnerData(AccumParam);
				
				return ABDMResponse.getPartnerResponse(donList, "true");
			}
			else if(strType.equals("S"))
			{
			List<healthidcreatedstatewise> donList= abdmdao.getHealthIdCreatedStateWise(AccumParam);
			
			return ABDMResponse.getHealthIDCreatedStateResp(donList, "true");
			}
			else if(strType.equals("HA"))
			{
                List<abdmResp> donList= abdmdao.getHealthAgeWise(AccumParam);
				
				return ABDMResponse.getABDMServiceResponse(donList, "true");
			
			}
			else if(strType.equals("ABHALT"))
			{
                List<abhalinkedresp> donList= abdmdao.getABHALinkedTrend(AccumParam);
				
				return ABDMResponse.getABHALinkResponse(donList, "true");
			}
			else if(strType.equals("ABHAL")) 
			{
				
                List<partnerlinkResp> donList= abdmdao.getPartnerLinked(AccumParam);
				
				return ABDMResponse.getPartnerLinkedResponse(donList, "true");
			}
					else
			{
				return  ABDMResponse.getABDMResponse("", "false");	
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("kycUpdateProcessData====="+e);
			System.out.println(e);
			return  ABDMResponse.getABDMResponse("", "false");
		}
	}
	@ResponseBody
	@PostMapping(value ="/dashboard/healthstatewise/1.0")
	public String GetHealthStateWiseData(@RequestBody  statedistReq  AccumParam) {
		logger.error("In Beneficiary API Get Data  of erupiService Controller");
		try {
			//System.out.println(AccumParam.getType());
			String strType=AccumParam.getType();
			 if(strType!="")
			{
			List<abdmResp> donList= abdmdao.getHealthStateWiseData(AccumParam);
				
				return ABDMResponse.getABDMServiceResponse(donList, "true");				
			}
			
			else
			{
				return  ABDMResponse.getABDMResponse("", "false");	
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("kycUpdateProcessData====="+e);
			System.out.println(e);
			return  ABDMResponse.getABDMResponse("", "false");
		}
	}
	
	@ResponseBody
	@PostMapping(value ="/dashboard/hospitallinked/1.0")
	public String GetHospitalLinked(@RequestBody  partnerlinkedReq  AccumParam) {
		logger.error("In Beneficiary API Get Data  of erupiService Controller");
		try {
			//System.out.println(AccumParam.getType());
			String strType=AccumParam.getOrigin();
			 if(strType!="")
			{
			List<abdmResp> donList= abdmdao.getHospitalLinked(AccumParam);
				
			return ABDMResponse.getABDMServiceResponse(donList, "true");				
			}
			
			else
			{
				return  ABDMResponse.getABDMResponse("", "false");	
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("kycUpdateProcessData====="+e);
			System.out.println(e);
			return  ABDMResponse.getABDMResponse("", "false");
		}
	}
	
	@ResponseBody
	@GetMapping(value ="/dashboard/getupdatedate/1.0")
	public String Getupdatedate() {
		logger.error("In Beneficiary API Get Data  of erupiService Controller");
		try {		
			
			String donList= abdmdao.Getupdatedate();	
			if(donList!=null)
				return ABDMResponse.getUpdateDateResponse(donList, "true");	
			else
				return  ABDMResponse.getABDMResponse("", "false");
	
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("kycUpdateProcessData====="+e);
			System.out.println(e);
			return  ABDMResponse.getABDMResponse("", "false");
		}
	}
	@ResponseBody
	@PostMapping(value ="/dashboard/stcode/1.0")
	public String GetStateCode(@RequestBody  statecodeReq  state_name) {
		logger.error("In Beneficiary API Get Data  of erupiService Controller");
		try {		
			//System.out.println(state_name);
			String donList= abdmdao.GetStateCode(state_name);	
			if(donList!=null)
				return ABDMResponse.getStateCodeResponse(donList, "true");	
			else
				return  ABDMResponse.getABDMResponse("", "false");
	
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("kycUpdateProcessData====="+e);
			System.out.println(e);
			return  ABDMResponse.getABDMResponse("", "false");
		}
	}
}
