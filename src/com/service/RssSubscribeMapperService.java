package com.service;

import java.util.List;

import com.entity.Rss;
import com.entity.RssSubscribe;

public interface RssSubscribeMapperService extends BaseService<RssSubscribe>{
	
	public List<Rss> selectTypeSubscribe(RssSubscribe rssSubscribe);
	
	public List<RssSubscribe> selectView(RssSubscribe rssSubscribe);
	
}
