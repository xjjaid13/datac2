package com.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.service.impl.BaseServiceImpl;
import com.dao.RssCrawlMapperDao;
import com.dao.RssMapperDao;
import com.dao.RssSubscribeMapperDao;
import com.entity.Rss;
import com.entity.RssCrawl;
import com.entity.RssSubscribe;
import com.service.RssMapperService;
import com.util.RssUtil;

@Service("rssMapperService")
public class RssMapperServiceImpl extends BaseServiceImpl<Rss> implements RssMapperService{

	@Autowired
	RssMapperDao rssMapperDao;
	
	@Autowired
	RssSubscribeMapperDao rssSubscribeMapperDao;
	
	@Autowired
	RssCrawlMapperDao rssCrawlMapperDao;

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
			rss.setFingePrint(rssMap.get("fingerPrint") + "");
			int rssId = rssMapperDao.insertAndReturnId(rss);
			rss.setRssId(rssId);
			rssSubscribe.setRssId(rssId);
		}
		rssSubscribeMapperDao.insert(rssSubscribe);
		return rss;
	}

	@Override
	public List<Map<String, String>> returnRssDetailList(Rss rss) {
		try{
			@SuppressWarnings("unchecked")
			List<Map<String,String>> rssDetailList = (List<Map<String, String>>) RssUtil.getRSSInfo(rss.getRssUrl()).get("list");
			return rssDetailList;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void fetchNewRss(Rss rss) {
		Map<String, Object> rssMap = RssUtil.getRSSInfo(rss.getRssUrl());
		if(!rssMap.get("fingerPrint").equals(rss.getFingePrint())){
			@SuppressWarnings("unchecked")
			List<Map<String,String>> rssDetailList = (List<Map<String,String>>)rssMap.get("list");
			if(rssDetailList == null){
				return;
			}
			int record = 0;
			RssCrawl rssCrawl = new RssCrawl();
			rssCrawl.setRssId(rss.getRssId());
			rssCrawl.setStartPage(0);
			rssCrawl.setPage(1);
			rssCrawl.setCondition("order by rssCrawlId desc");
			List<RssCrawl> rssCrawlList = rssCrawlMapperDao.selectList(rssCrawl);
			if(rssCrawlList != null && rssCrawlList.size() > 0){
				RssCrawl rssCrawlSingle = rssCrawlList.get(0);
				for(Map<String,String> map : rssDetailList){
					if(map.get("link").equals(rssCrawlSingle.getResourceUrl())){
						record = Integer.parseInt(map.get("itemNo"));
					} 
				}
			}
			int size = rssDetailList.size();
			if(record == 0){
				rssCrawl.setRssId(rss.getRssId());
				rssCrawl.setStartPage(0);
				rssCrawl.setPage(size);
				rssCrawl.setCondition("order by rssCrawlId desc");
				rssCrawlList = rssCrawlMapperDao.selectList(rssCrawl);
				if(rssCrawlList != null && rssCrawlList.size() > 0){
					for(RssCrawl rssCrawlNew : rssCrawlList){
						for(Map<String,String> map : rssDetailList){
							if(map.get("link").equals(rssCrawlNew.getResourceUrl())){
								record = Integer.parseInt(map.get("itemNo"));
							} 
						}
						if(record != 0){
							break;
						}
					}
				}
			}
			if(record == 0){
				record = size;
			}
			for(int i = 0; i < record; i++){
				Map<String,String> map = rssDetailList.get(i);
				rssCrawl = new RssCrawl();
				rssCrawl.setRssId(rss.getRssId());
				rssCrawl.setResourceDesc(map.get("description"));
				rssCrawl.setResourceTitle(map.get("title"));
				rssCrawl.setResourceUrl(map.get("link"));
				rssCrawl.setUpdateTime(new Timestamp(System.currentTimeMillis()).toString());
				rssCrawlMapperDao.insert(rssCrawl);
			}
			rss.setFingePrint(rssMap.get("fingerPrint") + "");
			rssMapperDao.update(rss);
		}
	}
}
