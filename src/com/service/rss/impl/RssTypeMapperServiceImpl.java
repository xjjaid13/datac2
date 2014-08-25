package com.service.rss.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.rss.RssTypeMapperDao;
import com.exception.common.ServiceException;
import com.po.rss.RssType;
import com.service.rss.RssTypeMapperService;

@Transactional
@Service("rssTypeMapperService")
public class RssTypeMapperServiceImpl implements RssTypeMapperService{

	@Autowired
	RssTypeMapperDao rssTypeMapperDao;

	@Override
	public boolean isChildren(int parentId,int userId) {
		try{
			RssType rssType = new RssType();
			if(parentId != -1){
				rssType.setParentId(parentId);
			}
			if(userId != -1){
				rssType.setUserId(userId);
			}
			int count = rssTypeMapperDao.selectCount(rssType);
			if(count > 0){
				return true;
			}else{
				return false;
			}
		} catch(Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<RssType> selectList(RssType rssType) {
		try{
			return rssTypeMapperDao.selectList(rssType);
		} catch(Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public RssType select(RssType rssType) {
		try{
			return rssTypeMapperDao.select(rssType);
		} catch(Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void update(RssType rssType) {
		try{
			rssTypeMapperDao.update(rssType);
		} catch(Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void delete(RssType rssType) {
		try{
			rssTypeMapperDao.delete(rssType);
		} catch(Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void insert(RssType rssType) {
		try{
			rssTypeMapperDao.insert(rssType);
		} catch(Exception e) {
			throw new ServiceException(e);
		}	
	}

}
