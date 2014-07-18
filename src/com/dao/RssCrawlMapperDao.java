package com.dao;

import java.util.List;

import com.entity.RssCrawl;

public interface RssCrawlMapperDao extends BaseDao<RssCrawl>{
	
	public List<RssCrawl> selectView(RssCrawl rssCrawl);
	
}
