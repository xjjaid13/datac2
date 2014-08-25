package com.service.rss.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.rss.RssMapperDao;
import com.dao.rss.RssSubscribeMapperDao;
import com.exception.common.ServiceException;
import com.po.rss.Rss;
import com.po.rss.RssSubscribe;
import com.po.rss.RssType;
import com.service.rss.RssSubscribeMapperService;

@Transactional
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
		try{
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
		} catch(Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void delete(RssSubscribe rssSubscribe) {
		try{
			rssSubscribeMapperDao.delete(rssSubscribe);
		} catch(Exception e) {
			throw new ServiceException(e);
		}
	}

}
