package com.dao.rss;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.dao.BaseDao;
import com.po.rss.RssCrawl;

public interface RssCrawlMapperDao extends BaseDao<RssCrawl>{
	
	public List<RssCrawl> selectView(RssCrawl rssCrawl) throws DataAccessException;
	
	public int selectCount(RssCrawl rssCrawl) throws DataAccessException;
	
}
