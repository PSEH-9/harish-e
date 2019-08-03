package com.example.demo.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class GetHttpResponse {

	private final String USER_AGENT = "Mozilla/5.0";

	public static ArrayList<BitMapObj> bpiLSt = new ArrayList<BitMapObj>();
	public static String disclaimer = null;
	public static String dateAsOn = null;
	public static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
	
	public static final Comparator<BitMapObj> DateComparator = new Comparator<BitMapObj>(){

		@Override
		public int compare(BitMapObj o1, BitMapObj o2) {
			 return o1.date.compareTo(o2.date);
		}
    };

	public void fetchFullResult() throws Exception {

		String url = "https://api.coindesk.com/v1/bpi/historical/close.json";

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("GET");

		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		JSONObject jsonObject = new JSONObject(response.toString());
		in.close();

		ArrayList<BitMapObj> tempBPILst = convertResult(jsonObject);
		Collections.sort(tempBPILst, DateComparator);
		bpiLSt = tempBPILst;
		
		for(BitMapObj bitMap : tempBPILst) {
			System.out.println(bitMap.getDate());
		}

	}

	private ArrayList<BitMapObj> convertResult(JSONObject jsonObj) {

		ArrayList<BitMapObj> bitObj = new ArrayList<BitMapObj>();
		
		try {
			JSONObject bpiJson = (JSONObject) jsonObj.get("bpi");
			String bpiData = bpiJson.toString();
			bpiData = returnStrippedStr(bpiData);
			
			String[] bpiList = bpiData.split(",");
			for (int i = 0; i < bpiList.length; i++) {
				String bpiString = (String) bpiList[i];
				String dateStr = bpiString.split(":")[0];
				dateStr = returnStrippedStr(dateStr);
				Date date = formatter.parse(dateStr);
				Double value = Double.parseDouble(bpiString.split(":")[1]);
				BitMapObj bp = new BitMapObj(date, value);
				bitObj.add(bp);
			}
	
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return bitObj;
	}
	
	
	private String returnStrippedStr(String str) {
		str = str.substring(1,str.length()-1);
		str.replaceAll("\\{", "");
		str.replaceAll("\\}", "");
		str.replaceAll("\"", "");
		
		return str;
	}
	
	
	public static void main(String ar[]) {
		
		GetHttpResponse getvalue = new GetHttpResponse();
		
		try {
			getvalue.fetchFullResult();
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
