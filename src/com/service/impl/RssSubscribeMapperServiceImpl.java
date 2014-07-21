package com.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.service.impl.BaseServiceImpl;
import com.dao.RssSubscribeMapperDao;
import com.po.Rss;
import com.po.RssSubscribe;
import com.service.RssSubscribeMapperService;

@Service("rssSubscribeMapperService")
public class RssSubscribeMapperServiceImpl extends BaseServiceImpl<RssSubscribe> implements RssSubscribeMapperService{

	@Autowired
	RssSubscribeMapperDao rssSubscribeMapperDao;

	@Override
	public List<Rss> selectTypeSubscribe(RssSubscribe rssSubscribe) {
		return rssSubscribeMapperDao.selectTypeSubscribe(rssSubscribe);
	}

	@Override
	public List<Rss> returnTopRssList(RssSubscribe rssSubscribe) {
		return rssSubscribeMapperDao.returnTopRssList(rssSubscribe);
	}

}
