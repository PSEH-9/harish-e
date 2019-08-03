package com.example.demo.service;

import java.text.ParseException;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.example.demo.util.BitMapObj;
import com.example.demo.util.GetHttpResponse;

@Service
public class ComputeResult {
	
	public String getResult(String fromDate, String toDate, String currency) {
		Date frmDte = null;
		Date tooDate = null;
		JSONObject resultJson = new JSONObject();
		
		try {
			frmDte = GetHttpResponse.formatter.parse(fromDate);
		
		} catch (ParseException e) {
			return "Please enter Valid from Date";
		}
		
		
		try {
			tooDate = GetHttpResponse.formatter.parse(toDate);
		
		} catch (ParseException e) {
			return "Please enter Valid To Date";
		}
		
		if(fromDate.compareTo(toDate) > 0) {
			return "Date range is not valid";
		}
		
		for(BitMapObj bitMap : GetHttpResponse.bpiLSt ) {
			if(bitMap.getDate().compareTo(frmDte) >= 0) {
				try {
					resultJson.put(bitMap.getDate().toString(),  bitMap.getCount());
				
				
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if( bitMap.getDate().compareTo(tooDate) >= 0 )
				break;
		}
		return resultJson.toString();
	}
}
