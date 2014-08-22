package com.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.RssMapperDao;
import com.dao.RssSubscribeMapperDao;
import com.po.Rss;
import com.po.RssSubscribe;
import com.po.RssType;
import com.service.RssSubscribeMapperService;

@Service("rssSubscribeMapperService")
public class RssSubscribeMapperServiceImpl implements RssSubscribeMapperService{

	@Autowired
	RssSubscribeMapperDao rssSubscribeMapperDao;
	
	@Autowired
	RssMapperDao rssMapperDao;

	@Override
	public List<Rss> selectTypeSubscribe(RssSubscribe rssSubscribe) {
		return rssMapperDao.selectTypeSubscribe(rssSubscribe);
	}

	@Override
	public List<Rss> selectRssCrawlList(RssType rssType) {
		List<RssSubscribe> rssSubscribeList = rssSubscribeMapperDao.selectListJoin(rssType);
		String ids = "";
		if(rssSubscribeList != null && rssSubscribeList.size() > 0){
			for(RssSubscribe rssSubscribe : rssSubscribeList){
				ids += rssSubscribe.getRssId() + ",";
			}
			ids = ids.substring(0 , ids.length() -1 );
			RssSubscribe rssSubscribe = new RssSubscribe();
			rssSubscribe.setIds(ids);
			return rssMapperDao.selectListByIds(rssSubscribe);
		}
		return null;
	}

	@Override
	public void delete(RssSubscribe rssSubscribe) {
		rssSubscribeMapperDao.delete(rssSubscribe);
	}

}
