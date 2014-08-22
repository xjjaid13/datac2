package com.service.rss;

import java.util.List;

import com.po.rss.RssCrawl;

public interface RssCrawlMapperService{
	
	public List<RssCrawl> selectView(RssCrawl rssCrawl);
	
	public List<RssCrawl> selectList(RssCrawl rssCrawl);
	
}
