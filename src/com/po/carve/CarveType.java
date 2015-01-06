package com.po.carve;

import com.po.BasePO;

public class CarveType extends BasePO{

	private static final long serialVersionUID = 1L;

	private Integer carveTypeId;

	private String url;

	private String typeName;

	private String content;

	private Integer seqNum;

	private String pattern;

	private String hash;


	public Integer getCarveTypeId() {
		return carveTypeId;
	}
	
	public String getUrl() {
		return url;
	}
	
	public String getTypeName() {
		return typeName;
	}
	
	public String getContent() {
		return content;
	}
	
	public Integer getSeqNum() {
		return seqNum;
	}
	
	public String getPattern() {
		return pattern;
	}
	
	public String getHash() {
		return hash;
	}
	
	
	public void setCarveTypeId(Integer carveTypeId) {
		this.carveTypeId = carveTypeId;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

}