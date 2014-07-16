package com.entity;

public class RssCrawl extends BaseEntity{
	
	private Integer rssCrawlId;
	
	private Integer rssId;
	
	private String resourceTitle;
	
	private String resourceUrl;
	
	private String updateTime;
	
	private String resourceDesc;
	
	private String fingerPrint;
	
	
	public Integer getRssCrawlId() {
		return rssCrawlId;
	}
	
	public Integer getRssId() {
		return rssId;
	}
	
	public String getResourceTitle() {
		return resourceTitle;
	}
	
	public String getResourceUrl() {
		return resourceUrl;
	}
	
	public String getUpdateTime() {
		return updateTime;
	}
	
	public String getResourceDesc() {
		return resourceDesc;
	}
	
	public String getFingerPrint() {
		return fingerPrint;
	}
	
	
	public void setRssCrawlId(Integer rssCrawlId) {
		this.rssCrawlId = rssCrawlId;
	}
	
	public void setRssId(Integer rssId) {
		this.rssId = rssId;
	}
	
	public void setResourceTitle(String resourceTitle) {
		this.resourceTitle = resourceTitle;
	}
	
	public void setResourceUrl(String resourceUrl) {
		this.resourceUrl = resourceUrl;
	}
	
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
	public void setResourceDesc(String resourceDesc) {
		this.resourceDesc = resourceDesc;
	}
	
	public void setFingerPrint(String fingerPrint) {
		this.fingerPrint = fingerPrint;
	}
	
	public String toString(){
		return "RssCrawl";
	}
}
