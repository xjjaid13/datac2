package com.service.rss;

import java.util.List;
import com.po.rss.Rss;
import com.po.rss.RssSubscribe;
import com.po.rss.RssType;

public interface RssSubscribeMapperService{
	
	public List<Rss> selectTypeSubscribe(RssSubscribe rssSubscribe);
	
	/**
	 * 获得用户或类别下的rss列表
	 * @param rssType 使用rssTypeId和userId过滤
	 * @return
	 */
	public List<Rss> selectRssCrawlList(RssType rssType);
	
	public void delete(RssSubscribe rssSubscribe);
	
}
