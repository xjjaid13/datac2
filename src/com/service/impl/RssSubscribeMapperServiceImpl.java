package com.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.service.impl.BaseServiceImpl;
import com.dao.RssSubscribeMapperDao;
import com.entity.RssSubscribe;
import com.service.RssSubscribeMapperService;

@Service("rssSubscribeMapperService")
public class RssSubscribeMapperServiceImpl extends BaseServiceImpl<RssSubscribe> implements RssSubscribeMapperService{

	@Autowired
	RssSubscribeMapperDao rssSubscribeMapperDao;

}
