package com.service;

import com.entity.Rss;

public interface RssMapperService extends BaseService<Rss>{
	
	public Rss insertRss(Rss rss,int parentId);
	
}
