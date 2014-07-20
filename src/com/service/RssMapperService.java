package com.service;

import java.util.List;

import com.po.Rss;
import com.vo.RssDetailVO;

public interface RssMapperService extends BaseService<Rss>{
	
	public Rss insertRss(Rss rss,int parentId);
	
	public List<RssDetailVO> returnRssDetailList(Rss rss);
	
	public void fetchNewRss(Rss rss);
	
}
