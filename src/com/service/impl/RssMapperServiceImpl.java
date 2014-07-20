package com.service.impl;

import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.RssCrawlMapperDao;
import com.dao.RssMapperDao;
import com.dao.RssSubscribeMapperDao;
import com.po.Rss;
import com.po.RssCrawl;
import com.po.RssSubscribe;
import com.exception.ServiceException;
import com.service.RssMapperService;
import com.util.Log;
import com.util.RssUtil;
import com.vo.RssDetailVO;
import com.vo.RssVO;

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
		try{
			String rssUrl = rss.getRssUrl();
			rssUrl = URLDecoder.decode(rss.getRssUrl(), "utf-8");
			rss = rssMapperDao.select(rss);
			RssSubscribe rssSubscribe = new RssSubscribe();
			rssSubscribe.setRssTypeId(parentId);
			if(rss != null){
				rssSubscribe.setRssId(rss.getRssId());
			}else{
				rss = new Rss();
				RssVO rssVO = RssUtil.getRSSInfo(rssUrl);
				rss.setRssIcon(rssVO.getIcon());
				rss.setRssTitle(rssVO.getTitle());
				rss.setRssUrl(rssUrl);
				rss.setFingePrint(rssVO.getFingerPrint());
				int rssId = rssMapperDao.insertAndReturnId(rss);
				rss.setRssId(rssId);
				rssSubscribe.setRssId(rssId);
			}
			rssSubscribeMapperDao.insert(rssSubscribe);
			return rss;
		}catch(Exception e){
			Log.Error(e);
			throw new ServiceException(e);
		}
	}

	@Override
	public List<RssDetailVO> returnRssDetailList(Rss rss) {
		try{
			List<RssDetailVO> rssDetailVOList = RssUtil.getRSSInfo(rss.getRssUrl()).getRssDetailVOList();
			return rssDetailVOList;
		}catch(Exception e){
			Log.Error(e);
			throw new ServiceException(e);
		}
	}

	@Override
	public void fetchNewRss(Rss rss) {
		RssVO rssVO = RssUtil.getRSSInfo(rss.getRssUrl());
		if(!rssVO.getFingerPrint().equals(rss.getFingePrint())){
			List<RssDetailVO> rssDetailList = rssVO.getRssDetailVOList();
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
				for(RssDetailVO rssDetailVO : rssDetailList){
					if(rssDetailVO.getLink().equals(rssCrawlSingle.getResourceUrl())){
						record = Integer.parseInt(rssDetailVO.getItemNo());
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
						for(RssDetailVO rssDetailVO : rssDetailList){
							if(rssDetailVO.getLink().equals(rssCrawlNew.getResourceUrl())){
								record = Integer.parseInt(rssDetailVO.getItemNo());
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
				RssDetailVO rssDetailVO = rssDetailList.get(i);
				rssCrawl = new RssCrawl();
				rssCrawl.setRssId(rss.getRssId());
				rssCrawl.setResourceDesc(rssDetailVO.getDescription());
				rssCrawl.setResourceTitle(rssDetailVO.getTitle());
				rssCrawl.setResourceUrl(rssDetailVO.getLink());
				rssCrawl.setUpdateTime(rssDetailVO.getPubDate());
				rssCrawlMapperDao.insert(rssCrawl);
			}
			rss.setFingePrint(rssVO.getFingerPrint());
			
			rssMapperDao.update(rss);
		}
	}
	
}
