package com.service.rss;

import java.util.List;

import com.po.rss.Rss;
import com.po.rss.RssType;
import com.vo.RssDetailVO;

public interface RssMapperService{
	
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
	
	/**
	 * 获得最新的rss
	 * @param rssType
	 * @return
	 */
	public List<Rss> returnNewRss(RssType rssType);
	
	public List<Rss> selectList(Rss rss);
	
	public int selectCount(Rss rss);
	
}
