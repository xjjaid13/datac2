package com.dao;

import java.util.List;

import com.po.Rss;
import com.po.RssSubscribe;

public interface RssSubscribeMapperDao extends BaseDao<RssSubscribe>{
	
	public List<Rss> selectTypeSubscribe(RssSubscribe rssSubscribe);
	
	public List<Rss> returnTopRssList(RssSubscribe rssSubscribe);
	
}
