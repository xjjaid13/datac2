package com.service;

import java.util.List;

import com.po.Rss;
import com.vo.RssDetailVO;

public interface RssMapperService extends BaseService<Rss>{
	
	public Rss insertRss(Rss rss,int parentId);
	
	/**
	 * 根据url获得rss的文章
	 * @param rss
	 * @return
	 */
	public List<RssDetailVO> returnRssDetailList(Rss rss);
	
	/**
	 * 获得rss的最新的订阅，并入库
	 * @param rss
	 */
	public void fetchNewRss(Rss rss);
	
}
