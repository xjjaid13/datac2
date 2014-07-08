package com.dao;

import java.util.List;

import com.entity.Rss;
import com.entity.RssSubscribe;

public interface RssSubscribeMapperDao extends BaseDao<RssSubscribe>{
	
	public List<Rss> selectTypeSubscribe(RssSubscribe rssSubscribe);
	
}
