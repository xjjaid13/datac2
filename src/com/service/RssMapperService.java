package com.service;

import java.util.List;
import java.util.Map;

import com.entity.Rss;

public interface RssMapperService extends BaseService<Rss>{
	
	public Rss insertRss(Rss rss,int parentId);
	
	public List<Map<String, String>> returnRssDetailList(Rss rss);
	
}
