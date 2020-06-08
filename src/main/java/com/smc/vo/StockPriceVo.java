package com.smc.vo;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

public class StockPriceVo {

	private String stockCode;
	
	private String stockExchange;
	
	private BigDecimal pricePerShare;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Timestamp date;
	
	
	public StockPriceVo() {
	}


	public String getStockCode() {
		return stockCode;
	}


	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}


	public String getStockExchange() {
		return stockExchange;
	}


	public void setStockExchange(String stockExchange) {
		this.stockExchange = stockExchange;
	}


	public BigDecimal getPricePerShare() {
		return pricePerShare;
	}


	public void setPricePerShare(BigDecimal pricePerShare) {
		this.pricePerShare = pricePerShare;
	}


	public Timestamp getDate() {
		return date;
	}


	public void setDate(Timestamp date) {
		this.date = date;
	}
	
	

}
