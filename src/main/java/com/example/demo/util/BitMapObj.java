package com.example.demo.util;

import java.util.Date;

public class BitMapObj implements Comparable<BitMapObj>{

	Date date;
	Double count;
	
	
	@Override
	public int compareTo(BitMapObj o) {
		return 0;
				
	}

	public BitMapObj(Date date, Double count) {
		super();
		this.date = date;
		this.count = count;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getCount() {
		return count;
	}

	public void setCount(Double count) {
		this.count = count;
	}
	
	
}
