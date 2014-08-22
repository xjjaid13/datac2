package com.dao.rss;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.dao.BaseDao;
import com.po.rss.RssSubscribe;
import com.po.rss.RssType;

public interface RssSubscribeMapperDao extends BaseDao<RssSubscribe>{
	
	public List<RssSubscribe> selectListJoin(RssType rssType) throws DataAccessException;
	
}
