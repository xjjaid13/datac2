package com.service;

import java.util.List;

import com.po.Rss;
import com.po.RssSubscribe;

public interface RssSubscribeMapperService extends BaseService<RssSubscribe>{
	
	public List<Rss> selectTypeSubscribe(RssSubscribe rssSubscribe);
	
	public List<Rss> returnTopRssList(RssSubscribe rssSubscribe);
	
}
