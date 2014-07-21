package com.service.impl;

import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.RssCrawlMapperDao;
import com.dao.RssMapperDao;
import com.dao.RssSubscribeMapperDao;
import com.exception.ServiceException;
import com.po.Rss;
import com.po.RssCrawl;
import com.po.RssSubscribe;
import com.service.RssMapperService;
import com.util.Log;
import com.util.Md5Util;
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
		Log.Info(rssVO.getLink() + "变动1，变动前fingerPrint=" + rssVO.getFingerPrint() + ",变动后fingerPrint="+rss.getFingePrint());
		if(!rssVO.getFingerPrint().equals(rss.getFingePrint())){
			Log.Info(rssVO.getLink() + "变动2，变动前fingerPrint=" + rss.getFingePrint() + ",变动后fingerPrint=" + rssVO.getFingerPrint());
			List<RssDetailVO> rssDetailList = rssVO.getRssDetailVOList();
			if(rssDetailList == null){
				return;
			}
			int record = 0;
			int size = rssDetailList.size();
			
			//先判断之前的第一个rss文章在新的rss文章中的位置
			for(RssDetailVO rssDetailVO : rssDetailList){
				if(rss.getFingePrint().equals(Md5Util.getMD5(rssDetailVO.getLink().getBytes()))){
					record = Integer.parseInt(rssDetailVO.getItemNo());
				}
			}
			
			//若都未匹配，则全部放入数据库
			if(record == 0){
				record = size;
			}
			for(int i = record; i < 1; i--){
				RssDetailVO rssDetailVO = rssDetailList.get(i);
				RssCrawl rssCrawl = new RssCrawl();
				rssCrawl.setRssId(rss.getRssId());
				rssCrawl.setResourceDesc(rssDetailVO.getDescription());
				rssCrawl.setResourceTitle(rssDetailVO.getTitle());
				rssCrawl.setResourceUrl(rssDetailVO.getLink());
				rssCrawl.setUpdateTime(rssDetailVO.getPubDate());
				rssCrawlMapperDao.insert(rssCrawl);
			}
			
			//更新rss的更新时间和指纹为最新
			rss.setUpdateTime(new Timestamp(System.currentTimeMillis()) + "");
			rss.setFingePrint(rssVO.getFingerPrint());
			rssMapperDao.update(rss);
		}
	}
	
}
