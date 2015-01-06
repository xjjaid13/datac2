package com.vo;

public class PageInfoVO {

	private Integer startPage;
	
	private Integer page;
	
	private String sOrderType;
	
	private String orderCol;

	public String getsOrderType() {
		return sOrderType;
	}

	public void setsOrderType(String sOrderType) {
		this.sOrderType = sOrderType;
	}

	public String getOrderCol() {
		return orderCol;
	}

	public void setOrderCol(String orderCol) {
		this.orderCol = orderCol;
	}

	public Integer getStartPage() {
		return startPage;
	}

	public void setStartPage(Integer startPage) {
		this.startPage = startPage;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}
	
}
