package com.gov.nha.bis.server.dao.impl;

import java.util.ArrayList;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;


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
import com.gov.nha.bis.server.dao.ABDMDao;
@Repository
public class ABDMDaoImpl implements ABDMDao{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public healthres getHealthData(statedistReq req){
		healthres benDetailsEntity=new healthres();
		StringBuilder today = new StringBuilder();
		StringBuilder total1 = new StringBuilder();
		StringBuilder total2 = new StringBuilder();
		try {
			String sql="";
			String where="";
			
			//System.out.println("Rizwan"+req.getState_code());	
			
			if(req.getState_code()!="")
			{
				where =" where state_code='"+req.getState_code()+"'";		
			}
			
			if(req.getDistrict_code()!="")
			{
				//where +=" and district_code='"+req.getDistrict_code()+"'";		
			}
			
			//System.out.println(where);	
			/*
			if(!req.getDistrict_code().equals(""))
			{
				where +=" and district_code="+req.getDistrict_code()+"";	
			}*/
			if(req.getType().equals("HI") && req.getState_code()=="")
			{
			sql="select dashboard_data.IndianFormat_Number(cast(today_count as bigint)) as today_count,dashboard_data.IndianFormat_Number(cast(overall_count as bigint)) as overall_count,ROUND(overall_count*100/(select sum(population) from dashboard_data.state_district_master ),2) as Popu_per from(\r\n"
					+ "select sum(today_count) as today_count,sum(overall_count) as overall_count\r\n"
					+ "from dashboard_data.healthid_top_indicators ) a";
			}
			else if(req.getType().equals("HI") && req.getState_code()!="")
			{
				sql="select dashboard_data.IndianFormat_Number(cast(today_count as bigint)) as today_count,dashboard_data.IndianFormat_Number(cast(overall_count as bigint)) as overall_count,ROUND(overall_count*100/(select sum(population) from dashboard_data.state_district_master where district_code in (select district_code from dashboard_data.state_district_master "+where+")),2) as Popu_per from(\r\n"
						+ "select sum(today_count) as today_count,sum(overall_count) as overall_count\r\n"
						+ "from dashboard_data.healthid_top_indicators where district_code in (select district_code from dashboard_data.state_district_master "+where+")) a";	
			}
			
			else if(req.getType().equals("HF"))
			{
				sql="select dashboard_data.IndianFormat_Number(cast(sum(today_count) as bigint))  as today_count,sum(application_count) as application_count,dashboard_data.IndianFormat_Number(cast(sum(registered_count) as bigint)) as registered_count  from health_facility_registry "+where+"";				
			}
			else if(req.getType().equals("HP"))
			{
			sql="select dashboard_data.IndianFormat_Number(cast(sum(today_count) as bigint))  as today_count,sum(application_count) as application_count,dashboard_data.IndianFormat_Number(cast(sum(registered_count) as bigint)) as registered_count  from health_professionals_registry "+where+"";
			}
			//System.out.println(sql);		
			List<Map<String , Object>> list=jdbcTemplate.queryForList(sql);			
			
			//System.out.println(list);
			if(list!=null && !list.isEmpty()) {				
				for (Map<String,Object> map: list) {	
					if(req.getType().equals("HI"))
					{
						today.append(map.get("today_count")!=null?map.get("today_count").toString():"0");
						total1.append(map.get("overall_count")!=null?map.get("overall_count").toString():"0");
						total2.append(map.get("Popu_per")!=null?map.get("Popu_per").toString():"0");
						
					}
					if(req.getType().equals("HF"))
					{
						today.append(map.get("today_count")!=null?map.get("today_count").toString():"0");
						total1.append(map.get("application_count")!=null?map.get("application_count").toString():"0");
						total2.append(map.get("registered_count")!=null?map.get("registered_count").toString():"0");
						
					}
					if(req.getType().equals("HP"))
					{
						today.append(map.get("today_count")!=null?map.get("today_count").toString():"0");
						total1.append(map.get("application_count")!=null?map.get("application_count").toString():"0");
						total2.append(map.get("registered_count")!=null?map.get("registered_count").toString():"0");
						
					}
					}
				}
			
			benDetailsEntity.setToday(today.toString());
			benDetailsEntity.setTotal1(total1.toString());
			benDetailsEntity.setTotal2(total2.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return benDetailsEntity;
		}
	
	@Override
	public List<abdmResp> getStateDistMaster(statedistReq req)
	{
		List<abdmResp> doList=new ArrayList<abdmResp>();
		try {
			String sql="";
			if(req.getType().equals("SM"))
			{
			sql="select distinct state_code as value,state_name as text from state_district_master order by state_name";
			}
			else if (req.getType().equals("DM") && !req.getState_code().equals(""))
			{
				sql="select distinct district_code as value,district_name as text from state_district_master where state_code='"+req.getState_code()+"' order by district_name";
			}
			
			//System.out.println(sql);		
			List<Map<String , Object>> list=jdbcTemplate.queryForList(sql);
			//System.out.println(list);
			if(list!=null && !list.isEmpty()) {				
				for (Map<String,Object> map: list) {	
					abdmResp benDetailsEntity=new abdmResp();	
					
					benDetailsEntity.setValue(map.get("value")!=null?map.get("value").toString():"");
					benDetailsEntity.setText(map.get("text")!=null?map.get("text").toString():"");
						
							doList.add(benDetailsEntity);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doList;
		}
	
	
	@Override
	public List<abdmResp> getHealthAgeWise(statedistReq req)
	{
		List<abdmResp> doList=new ArrayList<abdmResp>();
		try {
			String sql="";
			String strQuery="";
			 String	where="";
				if(req.getState_code()!="")
				{
					where =" where state_code='"+req.getState_code()+"'";		
				}
				
				if(req.getDistrict_code()!="")
				{
					//where +=" and district_code='"+req.getDistrict_code()+"'";		
				}
				
			
			sql="SELECT 'SELECT unnest(''{0-18Yrs, 19-45Yrs, 46-60Yrs, 60Yrs-Or-More,Not-Yet-Updated}''::text[] ) as text\r\n"
					+ "     , ' || string_agg('unnest('\r\n"
					+ "                    || quote_literal(ARRAY[Yrs0_18::text, Yrs19_45::text, Yrs46_60::text, Yrs60_Greater::text,Not_Yet_Updated::text])\r\n"
					+ "                    || '::text[]) AS value' , E'\\n     , ') AS sql\r\n"
					+ "\r\n"
					+ "FROM (\r\n"
					+ "select sum(case when a.Age>=0 and a.Age<=18 then total else 0 end ) as Yrs0_18 \r\n"
					+ ",sum(case when a.Age>=19 and a.Age<=45 then total else 0 end ) as Yrs19_45 \r\n"
					+ ",sum(case when a.Age>=46 and a.Age<=60 then total else 0 end ) as Yrs46_60 \r\n"
					+ ",sum(case when a.Age>=61 then total else 0 end ) as Yrs60_Greater \r\n"
					+",sum(case when a.Age=-1 then total else 0 end ) as Not_Yet_Updated \r\n"
					+ "from (select sum(healthid_count) as total,age from healthid_age_wise_trend "+where+" group by age) a) b ";
			
			//System.out.println(sql);		
			List<Map<String , Object>> list=jdbcTemplate.queryForList(sql);
			
			if(list!=null && !list.isEmpty()) {				
				for (Map<String,Object> map: list) {
					
					strQuery=map.get("sql")!=null?map.get("sql").toString():"";
					//System.out.println(strQuery);	
				}
			}
			if(strQuery!="")
			{
				List<Map<String , Object>> list2=jdbcTemplate.queryForList(strQuery);
				if(list2!=null && !list2.isEmpty()) {				
					for (Map<String,Object> map1: list2) {
						
						abdmResp benDetailsEntity=new abdmResp();	
						
						benDetailsEntity.setValue(map1.get("value")!=null?map1.get("value").toString():"");
						benDetailsEntity.setText(map1.get("text")!=null?map1.get("text").toString():"");
							
								doList.add(benDetailsEntity);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doList;
		}
	
	@Override
	public List<healthidpartnerwiseres> getHealthIdPartnerWise(HealthIdReq accumParam){
		List<healthidpartnerwiseres> doList=new ArrayList<healthidpartnerwiseres>();
		try {
			String sql="select * from healthid_partner_wise ";
			
			//System.out.println(sql);		
			List<Map<String , Object>> list=jdbcTemplate.queryForList(sql);
			//System.out.println(list);
			if(list!=null && !list.isEmpty()) {				
				for (Map<String,Object> map: list) {	
					healthidpartnerwiseres benDetailsEntity=new healthidpartnerwiseres();
					
					benDetailsEntity.setPartner_name(map.get("Integrator_name")!=null?map.get("Integrator_name").toString():"");
					benDetailsEntity.setTotal(map.get("healthid_count")!=null?map.get("healthid_count").toString():"0");					
							doList.add(benDetailsEntity);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doList;
		}
	
	@Override
	public List<healthidcreatedstatewise> getHealthIdCreatedStateWise(statedistReq req){
		List<healthidcreatedstatewise> doList=new ArrayList<healthidcreatedstatewise>();
		try {
			
		 String	where="";
		 String sql="";
			if(req.getState_code()!="")
			{
				where =" where state_code='"+req.getState_code()+"'";	
				 sql="select distinct b.district_name as text,dashboard_data.IndianFormat_Number(cast(a.today_count as bigint)) as today_count,dashboard_data.IndianFormat_Number(cast(a.overall_count as bigint)) as overall_count,round(a.aadhaar_per*100/a.overall_count,1) as aadhaar_per,a.population_per from (\r\n"
						+ "select district_code , sum(today_count) as today_count,sum(overall_count) as overall_count,sum(cast(aadhaar_per as numeric(10,2))) as aadhaar_per ,round(sum(cast(population_per as numeric(10,2)))/(select count(1) from dashboard_data.state_district_master where district_code=a.district_code ),1) as population_per from dashboard_data.healthid_created_state_wise  a where district_code in (select district_code from dashboard_data.state_district_master "+where+") group by district_code) a,dashboard_data.state_district_master b\r\n"
						+ "where a.district_code=b.district_code order by b.district_name asc";
			}
			
			//if(req.getDistrict_code()!="")
			//{
			//	where +=" and a.district_code='"+req.getDistrict_code()+"'";		
			//}
			else
			{
			 sql="select distinct b.state_name as text,dashboard_data.IndianFormat_Number(cast(a.today_count as bigint)) as today_count,dashboard_data.IndianFormat_Number(cast(a.overall_count as bigint)) as overall_count,round(a.aadhaar_per*100/a.overall_count,1) as aadhaar_per,a.population_per from (\r\n"
					+ "select state_code , sum(today_count) as today_count,sum(overall_count) as overall_count,sum(cast(aadhaar_per as numeric(10,2))) as aadhaar_per ,round(sum(cast(population_per as numeric(10,2)))/(select count(1) from dashboard_data.state_district_master where state_code=cast(a.state_code as integer)),1) as population_per from dashboard_data.healthid_created_state_wise  a  group by state_code) a,dashboard_data.state_district_master b\r\n"
					+ "where cast(a.state_code as integer)=cast(b.state_code as integer) order by b.state_name asc";
			}
			
			System.out.println(sql);		
			List<Map<String , Object>> list=jdbcTemplate.queryForList(sql);
			//System.out.println(list);
			if(list!=null && !list.isEmpty()) {				
				for (Map<String,Object> map: list) {	
					healthidcreatedstatewise benDetailsEntity=new healthidcreatedstatewise();
					
					benDetailsEntity.setState_name(map.get("text")!=null?map.get("text").toString():"");
					benDetailsEntity.setToday(map.get("today_count")!=null?map.get("today_count").toString():"0");
					benDetailsEntity.setOverall(map.get("overall_count")!=null?map.get("overall_count").toString():"0");	
					benDetailsEntity.setAadhaarper(map.get("aadhaar_per")!=null?map.get("aadhaar_per").toString():"0");
					benDetailsEntity.setPopulationper(map.get("population_per")!=null?map.get("population_per").toString():"0");	
							doList.add(benDetailsEntity);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doList;
		}
	
	@Override
	public List<partner_resp> getPartnerData(statedistReq req){
		List<partner_resp> benDetailsEntity=new ArrayList<partner_resp>();	
		
		//System.out.println(accumParam.getRpttype());	
		try {
			String sql="";
            String where="";
			
			//System.out.println("Rizwan"+req.getState_code());	
			
			if(req.getState_code()!="")
			{
				sql="select a.name,case when a.total_linked is null then '0' else a.total_linked end as total_linked,a.total from(\r\n"
				 		+ "select case when partner_name='NHA Face Auth' then 'PMJAY ( BIS2.0)' else partner_name end as name,\r\n"
				 		+ "dashboard_data.IndianFormat_Number(cast((select sum(hid_linked_count) from dashboard_data.healthid_linked where origin=b.origin and state_code='"+req.getState_code()+"') as bigint)) as total_linked,\r\n"
				 		+ "dashboard_data.IndianFormat_Number(cast(sum(healthid_count) as bigint)) as total,origin \r\n"
				 		+ "from dashboard_data.healthid_partner_wise b where state_code='"+req.getState_code()+"' \r\n"
				 		+ "group by partner_name,origin  order by partner_name asc) a";		
			}
			
			if(req.getState_code()=="")
			{
				sql="select a.name,case when a.total_linked is null then '0' else a.total_linked end as total_linked,a.total from(\r\n"
				 		+ "select case when partner_name='NHA Face Auth' then 'PMJAY ( BIS2.0)' else partner_name end as name,\r\n"
				 		+ "dashboard_data.IndianFormat_Number(cast((select sum(hid_linked_count) from dashboard_data.healthid_linked where origin=b.origin) as bigint)) as total_linked,\r\n"
				 		+ "dashboard_data.IndianFormat_Number(cast(sum(healthid_count) as bigint)) as total,origin \r\n"
				 		+ "from dashboard_data.healthid_partner_wise b \r\n"
				 		+ "group by partner_name,origin  order by partner_name asc) a";	
			}
			 
				 
					System.out.println(sql);		
			List<Map<String , Object>> list=jdbcTemplate.queryForList(sql);	
			
			//System.out.println(list);
			if(list!=null && !list.isEmpty()) {				
				for (Map<String,Object> map: list) {	
					partner_resp benDetailsEntity1=new partner_resp();
					
					benDetailsEntity1.setName(map.get("name")!=null?map.get("name").toString():"");
					benDetailsEntity1.setTotal(map.get("total")!=null?map.get("total").toString():"");
					benDetailsEntity1.setTotal_linked(map.get("total_linked")!=null?map.get("total_linked").toString():"");
					benDetailsEntity.add(benDetailsEntity1);
									}
				}
			
			//benDetailsEntity.setText(text.toString());
			//benDetailsEntity.setTotal(total.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return benDetailsEntity;
		}
	
	/*
	@Override
	public List<abhalinkedresp> getABHALinkCount(statedistReq req){
		List<abhalinkedresp> benDetailsEntity=new ArrayList<abhalinkedresp>();	
		
		//System.out.println(accumParam.getRpttype());	
		try {
			String sql="";
            String where="";
			
			//System.out.println("Rizwan"+req.getState_code());	
			
			if(req.getType().equals("ABHALCS") && req.getState_code()!="")
			{
				sql="select a.name,case when a.total_linked is null then '0' else a.total_linked end as total_linked,a.total from(\r\n"
				 		+ "select case when partner_name='NHA Face Auth' then 'PMJAY ( BIS2.0)' else partner_name end as name,\r\n"
				 		+ "dashboard_data.IndianFormat_Number(cast((select sum(hid_linked_count) from dashboard_data.healthid_linked where origin=origin and state_code='"+req.getState_code()+"') as bigint)) as total_linked,\r\n"
				 		+ "dashboard_data.IndianFormat_Number(cast(sum(healthid_count) as bigint)) as total \r\n"
				 		+ "from dashboard_data.healthid_partner_wise where state_code='"+req.getState_code()+"' \r\n"
				 		+ "group by partner_name  order by partner_name asc) a";		
			}
			
			if(req.getState_code()=="")
			{
				sql="select a.name,case when a.total_linked is null then '0' else a.total_linked end as total_linked,a.total from(\r\n"
				 		+ "select case when partner_name='NHA Face Auth' then 'PMJAY ( BIS2.0)' else partner_name end as name,\r\n"
				 		+ "dashboard_data.IndianFormat_Number(cast((select sum(hid_linked_count) from dashboard_data.healthid_linked where origin=origin) as bigint)) as total_linked,\r\n"
				 		+ "dashboard_data.IndianFormat_Number(cast(sum(healthid_count) as bigint)) as total \r\n"
				 		+ "from dashboard_data.healthid_partner_wise \r\n"
				 		+ "group by partner_name  order by partner_name asc) a";	
			}
			 
				 
					System.out.println(sql);		
			List<Map<String , Object>> list=jdbcTemplate.queryForList(sql);	
			
			//System.out.println(list);
			if(list!=null && !list.isEmpty()) {				
				for (Map<String,Object> map: list) {	
					abhalinkedresp benDetailsEntity1=new abhalinkedresp();
					
					benDetailsEntity1.setName(map.get("name")!=null?map.get("name").toString():"");
					benDetailsEntity1.setTotal(map.get("total")!=null?map.get("total").toString():"");
					benDetailsEntity1.setTotal_linked(map.get("total_linked")!=null?map.get("total_linked").toString():"");
					benDetailsEntity.add(benDetailsEntity1);
									}
				}
			
			//benDetailsEntity.setText(text.toString());
			//benDetailsEntity.setTotal(total.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return benDetailsEntity;
		}
	*/
	@Override
	public List<abdmResp> getHealthDataList(statedistReq req){
		List<abdmResp> benDetailsEntity=new ArrayList<abdmResp>();	
		
		//System.out.println(accumParam.getRpttype());	
		try {
			String sql="";
            String where="";
			
			//System.out.println("Rizwan"+req.getState_code());	
			
			if(req.getState_code()!="")
			{
				where =" where state_code='"+req.getState_code()+"'";		
			}
			
			if(req.getDistrict_code()!="")
			{
				//where +=" and district_code='"+req.getDistrict_code()+"'";		
			}
			if(req.getType().equals("HG"))
			{
				sql="select sum(value) as value,text from(\r\n"
						+ "select healthid_count as value,case when gender='M' then 'Male' when gender='F' then 'Female' else 'Others' end as text from(\r\n"
						+ "select  sum(healthid_count) as healthid_count,gender  from dashboard_data.healthid_gender_wise_trend "+where+"   group by gender ) a) b group by text";
				/*
			sql="select sum(value) as value,text from(\r\n"
					+ "select healthid_count as value,case when gender='M' then 'Male' when gender='F' then 'Female' else 'Others' end as text from(\r\n"
					+ "select  ROUND((sum(healthid_count)*100.00)/(select sum(healthid_count) from dashboard_data.healthid_gender_wise_trend \"+where+\"),2) as healthid_count,gender  from dashboard_data.healthid_gender_wise_trend "+where+"  group by gender ) a) b group by text";
					*/
			}
			else if(req.getType().equals("P"))
			{
				 sql="select case when partner_name='NHA Face Auth' then 'PMJAY ( BIS2.0)' else partner_name end as text, dashboard_data.IndianFormat_Number(cast(sum(healthid_count) as bigint)) as value from dashboard_data.healthid_partner_wise "+where+" group by partner_name  order by partner_name asc";
			}
			
			else if (req.getType().equals("HCT") && req.getRpttype().equals("T"))
			{
				where="";
				if(req.getState_code()!="")
				{
					where =" and state_code='"+req.getState_code()+"'";		
				}
				
				if(req.getDistrict_code()!="")
				{
					//where +=" and district_code='"+req.getDistrict_code()+"'";		
				}
				sql="select sum(healthid_count) as value,to_char(created_date,'DD-Mon-YYYY') as text,created_date from dashboard_data.healthid_date_wise_trend where created_date>=current_date-30 "+where+" group by to_char(created_date,'DD-Mon-YYYY'),created_date order by created_date asc";
			}
			else if (req.getType().equals("HCT") && req.getRpttype().equals("W"))
			{
				where="";
				if(req.getState_code()!="")
				{
					where =" and state_code='"+req.getState_code()+"'";		
				}
				
				if(req.getDistrict_code()!="")
				{
					//where +=" and district_code='"+req.getDistrict_code()+"'";		
				}
				
				sql="select sum(healthid_count) as value,to_char(created_date,'DD-Mon-YYYY') as text,created_date from dashboard_data.healthid_date_wise_trend where created_date>=current_date-7 "+where+"  group by to_char(created_date,'DD-Mon-YYYY'),created_date order by created_date asc";
			}
			else if (req.getType().equals("HCT") && req.getRpttype().equals("A"))
			{
				where="";
				if(req.getState_code()!="")
				{
					where =" where state_code='"+req.getState_code()+"'";		
				}
				
				if(req.getDistrict_code()!="")
				{
					//where +=" and district_code='"+req.getDistrict_code()+"'";		
				}
				
				sql="select to_char(week_to_date(a.yr, a.txt),'DD-Mon-YYYY') as text,total as value,week_to_date(a.yr, a.txt) as dt from(\r\n"
						+ "select cast(date_part('week', created_date) as INTEGER)  as txt,sum(healthid_count) as total ,cast(date_part('year', created_date) as integer) as yr\r\n"
						+ "from dashboard_data.healthid_date_wise_trend "+where+" \r\n"
						+ "group by date_part('week', created_date),date_part('year', created_date)\r\n"
						+ ") a where week_to_date(a.yr, a.txt) is not null and week_to_date(a.yr, a.txt)<=current_date order by week_to_date(a.yr, a.txt) asc";
			}
			
			else if (req.getType().equals("FO"))
			{
				where="";
				if(req.getState_code()!="")
				{
					where =" where state_code='"+req.getState_code()+"'";		
				}
				
				if(req.getDistrict_code()!="")
				{
					//where +=" and district_code='"+req.getDistrict_code()+"'";		
				}
				
				//sql="select case when facility_owner='' then '(Blank)' else facility_owner end as text,case when application_count!=0 then cast(registered_count*100.00/application_count as numeric(10,2)) else 0 end as value from (\r\n"
					//	+ "select facility_owner,sum(application_count) as application_count, sum(registered_count) as registered_count  from dashboard_data.health_facility_ownership "+where+" group by facility_owner) a where facility_owner!=''";
				
				sql="select * from (select facility_owner as text,sum(registered_count) as value  from dashboard_data.health_facility_ownership "+where+" group by facility_owner) a where a.text!='Not Yet Updated'";
			}
			
			else if (req.getType().equals("HFRS") && req.getState_code()!="")
			{
				sql="select distinct b.district_name as text,value from (\r\n"
						+ "select district_code,sum(application_count) as value from dashboard_data.health_facility_registry "+where+"  group by district_code ) a ,dashboard_data.state_district_master b\r\n"
						+ "where cast(b.district_code as character varying)=a.district_code order by b.district_name ";
			}
			else if (req.getType().equals("HFRS") && req.getState_code()=="")
			{
				sql="select distinct b.state_name as text,value from (\r\n"
						+ "select state_code,sum(application_count) as value from dashboard_data.health_facility_registry "+where+"  group by state_code ) a ,dashboard_data.state_district_master b\r\n"
						+ "where cast(b.state_code as character varying)=a.state_code order by b.state_name ";
			}
			else if (req.getType().equals("HPRS") && req.getState_code()!="")
			{
				sql="select distinct b.district_name as text,value from (\r\n"
						+ "select district_code,sum(application_count) as value from  dashboard_data.health_professionals_registry "+where+" group by district_code ) a ,dashboard_data.state_district_master b\r\n"
						+ "where cast(b.district_code as character varying)=a.district_code order by b.district_name ";
			}
			else if (req.getType().equals("HPRS") && req.getState_code()=="")
			{
				sql="select distinct b.state_name as text,value from (\r\n"
						+ "select state_code,sum(application_count) as value from  dashboard_data.health_professionals_registry "+where+" group by state_code ) a ,dashboard_data.state_district_master b\r\n"
						+ "where cast(b.state_code as character varying)=a.state_code order by b.state_name ";
			}
			
			else if (req.getType().equals("HFRAF") && req.getRpttype().equals("T"))
			{
				where="";
				if(req.getState_code()!="")
				{
					where =" and state_code='"+req.getState_code()+"'";		
				}
				
				if(req.getDistrict_code()!="")
				{
					//where +=" and district_code='"+req.getDistrict_code()+"'";		
				}
				sql="select sum(facility_registered) as value,to_char(created_date,'DD-Mon-YYYY') as text,created_date from dashboard_data.health_facility_date_wise_trend where created_date>=current_date-30  "+where+"  group by to_char(created_date,'DD-Mon-YYYY'),created_date order by created_date asc";
			}
			else if (req.getType().equals("HFRAF") && req.getRpttype().equals("W"))
			{
				where="";
				if(req.getState_code()!="")
				{
					where =" where state_code='"+req.getState_code()+"'";		
				}
				
				if(req.getDistrict_code()!="")
				{
					//where +=" and district_code='"+req.getDistrict_code()+"'";		
				}
				sql="select sum(facility_registered) as value,to_char(created_date,'DD-Mon-YYYY') as text,created_date from dashboard_data.health_facility_date_wise_trend where created_date>=current_date-7 "+where+"  group by to_char(created_date,'DD-Mon-YYYY'),created_date order by created_date asc";
			}
			else if (req.getType().equals("HFRAF") && req.getRpttype().equals("A"))
			{
				where="";
				if(req.getState_code()!="")
				{
					where =" where state_code='"+req.getState_code()+"'";		
				}
				
				if(req.getDistrict_code()!="")
				{
					//where +=" and district_code='"+req.getDistrict_code()+"'";		
				}
				sql="select to_char(week_to_date(a.yr, a.txt),'DD-Mon-YYYY') as text,total as value,week_to_date(a.yr, a.txt) as dt from(\r\n"
						+ "select cast(date_part('week', created_date) as INTEGER)  as txt,sum(facility_registered) as total ,cast(date_part('year', created_date) as integer) as yr\r\n"
						+ "from dashboard_data.health_facility_date_wise_trend "+where+" \r\n"
						+ "group by date_part('week', created_date),date_part('year', created_date)\r\n"
						+ ") a where week_to_date(a.yr, a.txt) is not null and date_part('year',week_to_date(a.yr, a.txt))<=date_part('year',current_date) order by week_to_date(a.yr, a.txt) asc";
			}
			
			
			else if (req.getType().equals("HPRAF") && req.getRpttype().equals("T"))
			{
				where="";
				if(req.getState_code()!="")
				{
					where =" and state_code='"+req.getState_code()+"'";		
				}
				
				if(req.getDistrict_code()!="")
				{
					//where +=" and district_code='"+req.getDistrict_code()+"'";		
				}
				sql="select sum(facility_registered) as value,to_char(created_date,'DD-Mon-YYYY') as text,created_date from dashboard_data.health_professional_date_wise_trend where created_date>=current_date-30  "+where+" group by to_char(created_date,'DD-Mon-YYYY'),created_date order by created_date asc";
			}
			else if (req.getType().equals("HPRAF") && req.getRpttype().equals("W"))
			{
				where="";
				if(req.getState_code()!="")
				{
					where =" where state_code='"+req.getState_code()+"'";		
				}
				
				if(req.getDistrict_code()!="")
				{
					//where +=" and district_code='"+req.getDistrict_code()+"'";		
				}
				sql="select sum(facility_registered) as value,to_char(created_date,'DD-Mon-YYYY') as text,created_date from dashboard_data.health_professional_date_wise_trend where created_date>=current_date-7 "+where+"  group by to_char(created_date,'DD-Mon-YYYY'),created_date order by created_date asc";
			}
			else if (req.getType().equals("HPRAF") && req.getRpttype().equals("A"))
			{
				where="";
				if(req.getState_code()!="")
				{
					where =" where state_code='"+req.getState_code()+"'";		
				}
				
				if(req.getDistrict_code()!="")
				{
					//where +=" and district_code='"+req.getDistrict_code()+"'";		
				}
				sql="select to_char(week_to_date(a.yr, a.txt),'DD-Mon-YYYY') as text,total as value,week_to_date(a.yr, a.txt) as dt from(\r\n"
						+ "select cast(date_part('week', created_date) as INTEGER)  as txt,sum(facility_registered) as total ,cast(date_part('year', created_date) as integer) as yr\r\n"
						+ "from dashboard_data.health_professional_date_wise_trend "+where+" \r\n"
						+ "group by date_part('week', created_date),date_part('year', created_date)\r\n"
						+ ") a where week_to_date(a.yr, a.txt) is not null and date_part('year',week_to_date(a.yr, a.txt))<=date_part('year',current_date) order by week_to_date(a.yr, a.txt) asc";
			}
			
			else if (req.getType().equals("FM"))
			{
				sql="select medicine_name as text,sum(applications_count) as value from dashboard_data.health_facility_medicine "+where+" group by medicine_name";
			}
			
			else if (req.getType().equals("PM"))
			{
				sql="select medicine_name as text,sum(applications_count) as value from dashboard_data.health_professtional_medicine "+where+" group by medicine_name";
			}
			
			else if (req.getType().equals("PE"))
			{
				sql="select * from (select professional_name as text,sum(application_count) as value from dashboard_data.health_professional_employementtype "+where+" group by professional_name) a where a.text!='Not Yet Specified'";
			}
			
			
			System.out.println(sql);		
			List<Map<String , Object>> list=jdbcTemplate.queryForList(sql);	
			
			//System.out.println(list);
			if(list!=null && !list.isEmpty()) {				
				for (Map<String,Object> map: list) {	
					abdmResp benDetailsEntity1=new abdmResp();
					
					benDetailsEntity1.setValue(map.get("value")!=null?map.get("value").toString():"");
					benDetailsEntity1.setText(map.get("text")!=null?map.get("text").toString():"");
						
					benDetailsEntity.add(benDetailsEntity1);
									}
				}
			
			//benDetailsEntity.setText(text.toString());
			//benDetailsEntity.setTotal(total.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return benDetailsEntity;
		}
	
	@Override
	public List<abdmResp> getHospitalLinked(partnerlinkedReq req){
		List<abdmResp> benDetailsEntity=new ArrayList<abdmResp>();	
		
		//System.out.println(accumParam.getRpttype());	
		try {
			String sql="";
            String where="";
			
			//System.out.println("Rizwan"+req.getState_code());	
			
			if(req.getState_code()!="")
			{
				where =" and a.state_code='"+req.getState_code()+"'";		
			}
			
			
			if(req.getOrigin()!="")
			{
				/*
				sql="select sum(a.hid_linked_count) as value,a.hospital_name as text from(\r\n"
						+ "select\r\n"
						+ "hid_linked_count,\r\n"
						+ "case when hospital_name is not null then hospital_name else hospitalid end as hospital_name\r\n"
						+ "from dashboard_data.healthid_hospital_linked where origin='"+req.getOrigin()+"'  "+where+")a  group by a.hospital_name order by sum(a.hid_linked_count) desc";
						*/
				sql="select hid_linked_count as value,\r\n"
						+ "case when d.state_code='0' then d.hospital_name \r\n"
						+ "else CONCAT(d.hospital_name,'\n (',d.state_name,' : ',d.district_name,')') end as text from(\r\n"
						+ "select sum(c.hid_linked_count) as hid_linked_count,c.hospital_name as hospital_name,c.state_name,c.district_name,c.state_code from(\r\n"
						+ "select\r\n"
						+ "a.hid_linked_count,\r\n"
						+ "case when a.hospital_name is not null then a.hospital_name else a.hospitalid end as hospital_name\r\n"
						+ ",b.state_name,b.district_name\r\n"
						+ ",a.state_code\r\n"
						+ "from dashboard_data.healthid_hospital_linked a,dashboard_data.state_district_master b\r\n"
						+ "where a.state_code::integer=b.state_code and a.district_code=b.district_code and  a.origin='"+req.getOrigin()+"' "+where+"\r\n"
						+ ")c group by c.hospital_name,c.state_name,c.district_name,c.state_code) d order by d.hid_linked_count desc\r\n"
						+ "";
				
			}
	
			
			System.out.println(sql);		
			List<Map<String , Object>> list=jdbcTemplate.queryForList(sql);	
			
			//System.out.println(list);
			if(list!=null && !list.isEmpty()) {				
				for (Map<String,Object> map: list) {	
					abdmResp benDetailsEntity1=new abdmResp();
					
					benDetailsEntity1.setValue(map.get("value")!=null?map.get("value").toString():"");
					benDetailsEntity1.setText(map.get("text")!=null?map.get("text").toString():"");
						
					benDetailsEntity.add(benDetailsEntity1);
									}
				}
			
			//benDetailsEntity.setText(text.toString());
			//benDetailsEntity.setTotal(total.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return benDetailsEntity;
		}
	
	@Override
	public List<partnerlinkResp> getPartnerLinked(statedistReq req){
		List<partnerlinkResp> benDetailsEntity=new ArrayList<partnerlinkResp>();	
		
		//System.out.println(accumParam.getRpttype());	
		try {
			String sql="";
            String where="";
			
			//System.out.println("Rizwan"+req.getState_code());	
			
			if(req.getState_code()!="")
			{
				where =" and state_code='"+req.getState_code()+"'";		
			}
			
			
			if(req.getType()!="")
			{
				sql="select case when a.name='NIC' then 'eHospital-NIC' else a.name end  as text,a.total as value,a.origin from(\r\n"
						+ "select partner_name as name,\r\n"
						+ "sum(hid_linked_count) as total\r\n"
						+ ",sum(hid_linked_count) as tt\r\n"
						+ ",origin\r\n"
						+ "from dashboard_data.healthid_linked_count where partner_name is not null "+where+" \r\n"
						+ "group by partner_name,origin ) a order by tt desc";
				
			}
	
			
			//System.out.println(sql);		
			List<Map<String , Object>> list=jdbcTemplate.queryForList(sql);	
			
			//System.out.println(list);
			if(list!=null && !list.isEmpty()) {				
				for (Map<String,Object> map: list) {	
					partnerlinkResp benDetailsEntity1=new partnerlinkResp();
					
					benDetailsEntity1.setValue(map.get("value")!=null?map.get("value").toString():"");
					benDetailsEntity1.setText(map.get("text")!=null?map.get("text").toString():"");
					benDetailsEntity1.setOrigin(map.get("origin")!=null?map.get("origin").toString():"");
					benDetailsEntity.add(benDetailsEntity1);
									}
				}
			
			//benDetailsEntity.setText(text.toString());
			//benDetailsEntity.setTotal(total.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return benDetailsEntity;
		}
	
	
	@Override
	public List<abhalinkedresp> getABHALinkedTrend(statedistReq req){
		List<abhalinkedresp> benDetailsEntity=new ArrayList<abhalinkedresp>();	
			
		try {
			String sql="";
            String where="";
			
			if(req.getState_code()!="")
			{
				where =" where state_code='"+req.getState_code()+"'";		
			}
			
			if(req.getDistrict_code()!="")
			{
				//where +=" and district_code='"+req.getDistrict_code()+"'";		
			}
			 if (req.getType().equals("ABHALT") && req.getRpttype().equals("T"))
			{
				where="";
				if(req.getState_code()!="")
				{
					where =" and state_code='"+req.getState_code()+"'";		
				}
				
				if(req.getDistrict_code()!="")
				{
					//where +=" and district_code='"+req.getDistrict_code()+"'";		
				}
				sql="select sum(hid_linked_count) as hid_count,sum(record_linked_count) as record_count,to_char(created_date,'DD-Mon-YYYY') as text,created_date from dashboard_data.healthid_linked_trend where created_date>=current_date-30 "+where+" group by to_char(created_date,'DD-Mon-YYYY'),created_date order by created_date asc";
			}
			else if (req.getType().equals("ABHALT") && req.getRpttype().equals("W"))
			{
				where="";
				if(req.getState_code()!="")
				{
					where =" and state_code='"+req.getState_code()+"'";		
				}
				
				if(req.getDistrict_code()!="")
				{
					//where +=" and district_code='"+req.getDistrict_code()+"'";		
				}
				
				sql="select sum(hid_linked_count) as hid_count,sum(record_linked_count) as record_count,to_char(created_date,'DD-Mon-YYYY') as text,created_date from dashboard_data.healthid_linked_trend where created_date>=current_date-7 "+where+"  group by to_char(created_date,'DD-Mon-YYYY'),created_date order by created_date asc";
			}
			else if (req.getType().equals("ABHALT") && req.getRpttype().equals("A"))
			{
				where="";
				if(req.getState_code()!="")
				{
					where =" where state_code='"+req.getState_code()+"'";		
				}
				
				if(req.getDistrict_code()!="")
				{
					//where +=" and district_code='"+req.getDistrict_code()+"'";		
				}
				
				sql="select to_char(week_to_date(a.yr, a.txt),'DD-Mon-YYYY') as text,hid_count,record_count,week_to_date(a.yr, a.txt) as dt from(\r\n"
						+ "select cast(date_part('week', created_date) as INTEGER)  as txt,sum(hid_linked_count) as hid_count,sum(record_linked_count) as record_count ,cast(date_part('year', created_date) as integer) as yr\r\n"
						+ "from dashboard_data.healthid_linked_trend "+where+" \r\n"
						+ "group by date_part('week', created_date),date_part('year', created_date)\r\n"
						+ ") a where week_to_date(a.yr, a.txt) is not null and week_to_date(a.yr, a.txt)<=current_date order by week_to_date(a.yr, a.txt) asc";
			}
			
			
			System.out.println(sql);		
			List<Map<String , Object>> list=jdbcTemplate.queryForList(sql);	
			
			//System.out.println(list);
			if(list!=null && !list.isEmpty()) {				
				for (Map<String,Object> map: list) {	
					abhalinkedresp benDetailsEntity1=new abhalinkedresp();
					
					benDetailsEntity1.setName(map.get("text")!=null?map.get("text").toString():"");
					benDetailsEntity1.setHid_count(map.get("hid_count")!=null?map.get("hid_count").toString():"0");
					benDetailsEntity1.setRecord_count(map.get("record_count")!=null?map.get("record_count").toString():"0");
					benDetailsEntity.add(benDetailsEntity1);
									}
				}
			
			//benDetailsEntity.setText(text.toString());
			//benDetailsEntity.setTotal(total.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return benDetailsEntity;
		}
	
	
	@Override
	public List<abdmResp> getHealthStateWiseData(statedistReq req){
		List<abdmResp> doList=new ArrayList<abdmResp>();
		try {
			String sql="";
			
            String where="";
			
			//System.out.println("Rizwan"+req.getState_code());	
			
			if(req.getState_code()!="")
			{
				where =" and a.state_code='"+req.getState_code()+"'";		
			}
			
			if(req.getDistrict_code()!="")
			{
				//where +=" and a.district_code='"+req.getDistrict_code()+"'";		
			}
			
			if(req.getType().equals("HITS"))
			{
				where="";
				
				if(req.getState_code()!="")
				{
					where =" where state_code='"+req.getState_code()+"'";	
					sql="select distinct b.district_name as text,a.healthid_count as value from (\r\n"
							+ "select district_code,sum(overall_count) as healthid_count from dashboard_data.healthid_top_indicators where district_code in (select district_code from dashboard_data.state_district_master "+where+")  group by district_code) a,dashboard_data.state_district_master b\r\n"
							+ "where cast(b.district_code as character varying)=a.district_code order by b.district_name";
				}
				else
				{
				
				//if(req.getDistrict_code()!="")
				//{
				//	where +=" and district_code='"+req.getDistrict_code()+"'";		
				//}
				
				
			sql="select distinct b.state_name as text,a.healthid_count as value from (\r\n"
					+ "select state_code,sum(overall_count) as healthid_count from dashboard_data.healthid_top_indicators  group by state_code) a,dashboard_data.state_district_master b\r\n"
					+ "where cast(b.state_code as character varying)=a.state_code order by b.state_name";
				}
			}
			
			if(req.getType().equals("HFTS"))
			{
				where="";
				if(req.getState_code()!="")
				{
					where =" where state_code='"+req.getState_code()+"'";	
					sql="select distinct b.district_name as text,value from (\r\n"
							+ "select district_code,sum(registered_count) as value from dashboard_data.health_facility_registry where district_code in (select district_code from dashboard_data.state_district_master "+where+") group by district_code ) a ,dashboard_data.state_district_master b\r\n"
							+ "where cast(b.district_code as character varying)=a.district_code order by b.district_name ";
				}
				
				//if(req.getDistrict_code()!="")
				//{
				//	where +=" and district_code='"+req.getDistrict_code()+"'";		
				//}
				else
				{
				
			sql="select distinct b.state_name as text,value from (\r\n"
					+ "select state_code,sum(registered_count) as value from dashboard_data.health_facility_registry  group by state_code ) a ,dashboard_data.state_district_master b\r\n"
					+ "where cast(b.state_code as character varying)=a.state_code order by b.state_name ";
				}
			}
			if(req.getType().equals("HPTS"))
			{
				where="";
				if(req.getState_code()!="")
				{
					where =" where state_code='"+req.getState_code()+"'";	
					sql="select distinct b.district_name as text,value from (\r\n"
							+ "select district_code,sum(registered_count) as value from dashboard_data.health_professionals_registry where district_code in (select district_code from dashboard_data.state_district_master "+where+") group by district_code ) a ,dashboard_data.state_district_master b\r\n"
							+ "where cast(b.district_code as character varying)=a.district_code order by b.district_name";
				}
				
				//if(req.getDistrict_code()!="")
				//{
				//	where +=" and district_code='"+req.getDistrict_code()+"'";		
				//}
				
				else
				{
			         sql="select distinct b.state_name as text,value from (\r\n"
					+ "select state_code,sum(registered_count) as value from dashboard_data.health_professionals_registry  group by state_code ) a ,dashboard_data.state_district_master b\r\n"
					+ "where cast(b.state_code as character varying)=a.state_code order by b.state_name";
				}
			}
			//System.out.println(sql);		
			List<Map<String , Object>> list=jdbcTemplate.queryForList(sql);
			//System.out.println(list);
			if(list!=null && !list.isEmpty()) {				
				for (Map<String,Object> map: list) {	
					abdmResp benDetailsEntity=new abdmResp();	
					
					benDetailsEntity.setValue(map.get("value")!=null?map.get("value").toString():"");
					benDetailsEntity.setText(map.get("text")!=null?map.get("text").toString():"");
						
							doList.add(benDetailsEntity);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doList;
	}
	
	
	@Override
	public String Getupdatedate(){	
		String dt=null;
		try {
			
			String sql="select TO_CHAR(max(upload_date), 'dd/mm/yyyy hh12:mi AM') as dt  from  dashboard_data.data_upload_date";
				
			//System.out.println(sql);		
			List<Map<String , Object>> list=jdbcTemplate.queryForList(sql);
			//System.out.println(list);
			if(list!=null && !list.isEmpty()) {				
				for (Map<String,Object> map: list) {	
                  dt=(map.get("dt")!=null?map.get("dt").toString():"");
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dt;
	}
	@Override
	public String GetStateCode(statecodeReq statename){	
		String dt=null;
		try {
			
			String sql="select state_code from dashboard_data.state_district_master where state_name='"+statename.getState_name()+"'";
				
			System.out.println(sql);		
			List<Map<String , Object>> list=jdbcTemplate.queryForList(sql);
			//System.out.println(list);
			if(list!=null && !list.isEmpty()) {				
				for (Map<String,Object> map: list) {	
                  dt=(map.get("state_code")!=null?map.get("state_code").toString():"");
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dt;
	}

	}
