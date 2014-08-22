package com.service;

import java.util.List;

import com.po.RssCrawl;

public interface RssCrawlMapperService{
	
	public List<RssCrawl> selectView(RssCrawl rssCrawl);
	
	public List<RssCrawl> selectList(RssCrawl rssCrawl);
	
}
