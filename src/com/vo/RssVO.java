package com.vo;

import java.util.List;

public class RssVO {

	private String title;
	
	private String link;
	
	private String description;
	
	private String language;
	
	private String copyright;
	
	private String fingerPrint;
	
	private String icon;
	
	private List<RssDetailVO> rssDetailVOList;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCopyright() {
		return copyright;
	}

	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}

	public String getFingerPrint() {
		return fingerPrint;
	}

	public void setFingerPrint(String fingerPrint) {
		this.fingerPrint = fingerPrint;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public List<RssDetailVO> getRssDetailVOList() {
		return rssDetailVOList;
	}

	public void setRssDetailVOList(List<RssDetailVO> rssDetailVOList) {
		this.rssDetailVOList = rssDetailVOList;
	}
	
}
