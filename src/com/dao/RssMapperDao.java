package com.dao;

import java.util.List;

import com.po.Rss;
import com.po.RssType;

public interface RssMapperDao extends BaseDao<Rss>{
	
	public Rss selectRssTopCrawl(Rss rss);
	
	public List<Rss> selectRssByUser(RssType rssType);
	
}
