package com.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.RssTypeMapperDao;
import com.po.RssType;
import com.service.RssTypeMapperService;

@Service("rssTypeMapperService")
public class RssTypeMapperServiceImpl implements RssTypeMapperService{

	@Autowired
	RssTypeMapperDao rssTypeMapperDao;

	@Override
	public boolean isChildren(int parentId,int userId) {
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
	}

	@Override
	public List<RssType> selectList(RssType rssType) {
		return rssTypeMapperDao.selectList(rssType);
	}

	@Override
	public RssType select(RssType rssType) {
		return rssTypeMapperDao.select(rssType);
	}

	@Override
	public void update(RssType rssType) {
		rssTypeMapperDao.update(rssType);
	}

	@Override
	public void delete(RssType rssType) {
		rssTypeMapperDao.delete(rssType);
	}

	@Override
	public void insert(RssType rssType) {
		rssTypeMapperDao.insert(rssType);
	}

}
