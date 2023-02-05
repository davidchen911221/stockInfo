package com.tzuchuan.app;
import java.io.Serializable;
import java.lang.*;
public class StockInfo implements Serializable{
	private static final long serialVersionUID=1L;
	private String stockCode;
	private String stockName;
	private long tradeTime;
	private float closeprice;
	
	public void setAllInfo(String code,String name,long time,float price) {
		stockCode=code;
		stockName=name;
		tradeTime=time;
		closeprice=price;
		
	}
	
	public String infoToString() {
		return this.stockCode+" | "+this.stockName+" | "+this.tradeTime+" | "+this.closeprice;
	}

}
