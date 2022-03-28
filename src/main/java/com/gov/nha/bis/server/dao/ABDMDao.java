package com.gov.nha.bis.server.dao;

import java.util.List;


import com.gov.nha.bis.server.dto.HealthIdReq;
import com.gov.nha.bis.server.dto.abdmResp;
import com.gov.nha.bis.server.dto.abhalinkedresp;
import com.gov.nha.bis.server.dto.healthidcreatedstatewise;
import com.gov.nha.bis.server.dto.healthidpartnerwiseres;
import com.gov.nha.bis.server.dto.healthres;
import com.gov.nha.bis.server.dto.partner_resp;
import com.gov.nha.bis.server.dto.partnerlinkResp;
import com.gov.nha.bis.server.dto.partnerlinkedReq;
import com.gov.nha.bis.server.dto.statecodeReq;
import com.gov.nha.bis.server.dto.statedistReq;




public interface ABDMDao{
	
	public healthres getHealthData(statedistReq req);
	
	public List<abdmResp> getStateDistMaster(statedistReq Req);
	
	
	
	
	public List<abdmResp> getHealthDataList(statedistReq req);
	
	public List<abdmResp> getHealthStateWiseData(statedistReq req);
    public List<healthidpartnerwiseres> getHealthIdPartnerWise(HealthIdReq healthreq);
    public List<healthidcreatedstatewise> getHealthIdCreatedStateWise(statedistReq accumParam);
    public List<abdmResp> getHealthAgeWise(statedistReq req);	
    public String Getupdatedate();
    public String GetStateCode(statecodeReq statename);
    public List<partner_resp> getPartnerData(statedistReq req);
    public List<abhalinkedresp> getABHALinkedTrend(statedistReq req);
    
    public List<abdmResp> getHospitalLinked(partnerlinkedReq req);
    public List<partnerlinkResp> getPartnerLinked(statedistReq req);

}
