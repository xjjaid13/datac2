package com.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.service.impl.BaseServiceImpl;
import com.dao.RssCrawlMapperDao;
import com.entity.RssCrawl;
import com.service.RssCrawlMapperService;

@Service("rssCrawlMapperService")
public class RssCrawlMapperServiceImpl extends BaseServiceImpl<RssCrawl> implements RssCrawlMapperService{

	@Autowired
	RssCrawlMapperDao rssCrawlMapperDao;

}
