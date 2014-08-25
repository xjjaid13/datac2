package com.service.rss.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.rss.RssCrawlMapperDao;
import com.exception.common.ServiceException;
import com.po.rss.RssCrawl;
import com.service.rss.RssCrawlMapperService;

@Transactional
@Service("rssCrawlMapperService")
public class RssCrawlMapperServiceImpl implements RssCrawlMapperService{

	@Autowired
	RssCrawlMapperDao rssCrawlMapperDao;

	@Override
	public List<RssCrawl> selectView(RssCrawl rssCrawl) {
		try{
			return rssCrawlMapperDao.selectView(rssCrawl);
		} catch(Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<RssCrawl> selectList(RssCrawl rssCrawl) {
		try{
			return rssCrawlMapperDao.selectList(rssCrawl);
		} catch(Exception e) {
			throw new ServiceException(e);
		}
	}
	
}
