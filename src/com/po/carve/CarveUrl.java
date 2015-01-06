package com.po.carve;

import com.po.BasePO;

public class CarveUrl extends BasePO{

	private static final long serialVersionUID = 1L;
	
	private Integer carveUrlId;

	private String title;

	private String url;

	private Integer carveTypeId;

	private String createTime;


	public Integer getCarveUrlId() {
		return carveUrlId;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getUrl() {
		return url;
	}
	
	public Integer getCarveTypeId() {
		return carveTypeId;
	}
	
	public String getCreateTime() {
		return createTime;
	}
	
	
	public void setCarveUrlId(Integer carveUrlId) {
		this.carveUrlId = carveUrlId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setCarveTypeId(Integer carveTypeId) {
		this.carveTypeId = carveTypeId;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}