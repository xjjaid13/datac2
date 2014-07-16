package com.entity;

public class Rss extends BaseEntity{
	
	private Integer rssId;
	
	private String rssTitle;
	
	private String rssUrl;
	
	private String rssIcon;
	
	private String fingePrint;
	
	private Integer isSubscribe;
	
	
	public Integer getRssId() {
		return rssId;
	}
	
	public String getRssTitle() {
		return rssTitle;
	}
	
	public String getRssUrl() {
		return rssUrl;
	}
	
	public String getRssIcon() {
		return rssIcon;
	}
	
	public String getFingePrint() {
		return fingePrint;
	}
	
	public Integer getIsSubscribe() {
		return isSubscribe;
	}
	
	
	public void setRssId(Integer rssId) {
		this.rssId = rssId;
	}
	
	public void setRssTitle(String rssTitle) {
		this.rssTitle = rssTitle;
	}
	
	public void setRssUrl(String rssUrl) {
		this.rssUrl = rssUrl;
	}
	
	public void setRssIcon(String rssIcon) {
		this.rssIcon = rssIcon;
	}
	
	public void setFingePrint(String fingePrint) {
		this.fingePrint = fingePrint;
	}
	
	public void setIsSubscribe(Integer isSubscribe) {
		this.isSubscribe = isSubscribe;
	}
	
	public String toString(){
		return "Rss";
	}
}
