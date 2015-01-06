package com.vo;

public class SelectorVO {

	private String selectString;
	
	private Integer seqNum;

	public String getSelectString() {
		return selectString;
	}

	public void setSelectString(String selectString) {
		this.selectString = selectString;
	}

	public Integer getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}
	
	public String toString(){
		return "selectString=(" + selectString + ");seqNum=" + seqNum;
	}
	
}
