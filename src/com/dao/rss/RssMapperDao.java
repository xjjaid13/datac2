package com.dao.rss;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.dao.BaseDao;
import com.po.rss.Rss;
import com.po.rss.RssSubscribe;
import com.po.rss.RssType;

public interface RssMapperDao extends BaseDao<Rss>{
	
	public Rss selectRssTopCrawl(Rss rss) throws DataAccessException;
	
	public List<Rss> selectRssByUser(RssType rssType) throws DataAccessException;
	
	public List<Rss> selectTypeSubscribe(RssSubscribe rssSubscribe) throws DataAccessException;

	public List<Rss> selectListByIds(RssSubscribe rssSubscribe) throws DataAccessException;
	
}
