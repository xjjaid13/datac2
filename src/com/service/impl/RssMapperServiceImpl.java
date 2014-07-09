package com.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.service.impl.BaseServiceImpl;
import com.dao.RssMapperDao;
import com.dao.RssSubscribeMapperDao;
import com.entity.Rss;
import com.entity.RssSubscribe;
import com.service.RssMapperService;
import com.util.RssUtil;

@Service("rssMapperService")
public class RssMapperServiceImpl extends BaseServiceImpl<Rss> implements RssMapperService{

	@Autowired
	RssMapperDao rssMapperDao;
	
	@Autowired
	RssSubscribeMapperDao rssSubscribeMapperDao;

	@Override
	public Rss insertRss(Rss rss, int parentId) {
		String rssUrl = rss.getRssUrl();
		rss = rssMapperDao.select(rss);
		RssSubscribe rssSubscribe = new RssSubscribe();
		rssSubscribe.setRssTypeId(parentId);
		if(rss != null){
			rssSubscribe.setRssId(rss.getRssId());
		}else{
			rss = new Rss();
			Map<String, Object> rssMap = RssUtil.getRSSInfo(rssUrl);
			rss.setRssIcon((String)rssMap.get("icon"));
			rss.setRssTitle((String)rssMap.get("title"));
			rss.setRssUrl(rssUrl);
			int rssId = rssMapperDao.insertAndReturnId(rss);
			rss.setRssId(rssId);
			rssSubscribe.setRssId(rssId);
		}
		rssSubscribeMapperDao.insert(rssSubscribe);
		return rss;
	}

}
