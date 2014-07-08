package com.service;

import com.entity.Rss;

public interface RssMapperService extends BaseService<Rss>{
	
	public void insertRss(Rss rss,int parentId);
	
}
