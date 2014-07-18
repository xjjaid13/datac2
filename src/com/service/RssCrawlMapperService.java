package com.service;

import java.util.List;

import com.entity.RssCrawl;

public interface RssCrawlMapperService extends BaseService<RssCrawl>{
	
	public List<RssCrawl> selectView(RssCrawl rssCrawl);
	
}
