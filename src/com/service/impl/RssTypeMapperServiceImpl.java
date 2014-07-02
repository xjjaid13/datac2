package com.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.service.impl.BaseServiceImpl;
import com.dao.RssTypeMapperDao;
import com.entity.RssType;
import com.service.RssTypeMapperService;

@Service("rssTypeMapperService")
public class RssTypeMapperServiceImpl extends BaseServiceImpl<RssType> implements RssTypeMapperService{

	@Autowired
	RssTypeMapperDao rssTypeMapperDao;

}
