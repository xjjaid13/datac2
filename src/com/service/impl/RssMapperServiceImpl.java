package com.service.impl;

import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
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
import com.util.Md5Util;
import com.util.RssUtil;
import com.vo.RssDetailVO;
import com.vo.RssVO;

@Service("rssMapperService")
public class RssMapperServiceImpl extends BaseServiceImpl<Rss> implements RssMapperService{

	//log
	private static Logger log = Logger.getLogger(RssMapperServiceImpl.class);
	
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
			rss = rssMapperDao.selectRssTopCrawl(rss);
			RssSubscribe rssSubscribe = new RssSubscribe();
			rssSubscribe.setRssTypeId(parentId);
			if(rss != null){
				rssSubscribe.setRssId(rss.getRssId());
				//判断是不是已经存在
				RssSubscribe rssSubscribeNew = rssSubscribeMapperDao.select(rssSubscribe);
				if(rssSubscribeNew != null){
					throw new Exception("该列别下存在此订阅");
				}
			}else{
				rss = new Rss();
				RssVO rssVO = RssUtil.getRSSInfo(rssUrl);
				if(rssVO == null){
					log.error(rssUrl + "获取rss失败");
					return null;
				}
				rss.setRssIcon(rssVO.getIcon());
				rss.setRssTitle(rssVO.getTitle());
				rss.setRssUrl(rssUrl);
				rss.setFingePrint(rssVO.getFingerPrint());
				int rssId = rssMapperDao.insertAndReturnId(rss);
				rss.setRssId(rssId);
				rssSubscribe.setRssId(rssId);
				List<RssCrawl> rssCrawlList = new ArrayList<RssCrawl>();
				if(rssVO.getRssDetailVOList() != null && rssVO.getRssDetailVOList().size() > 0){
					int i = 0;
					for(RssDetailVO rssDetailVO : rssVO.getRssDetailVOList()){
						i++;
						RssCrawl rssCrawl = new RssCrawl();
						rssCrawl.setResourceDesc(rssDetailVO.getDescription());
						rssCrawl.setResourceTitle(rssDetailVO.getTitle());
						rssCrawl.setResourceUrl(rssDetailVO.getLink());
						rssCrawl.setUpdateTime(rssDetailVO.getPubDate());
						rssCrawl.setRssId(rssId);
						rssCrawlMapperDao.insert(rssCrawl);
						rssCrawlList.add(rssCrawl);
						if(i == 4){
							break;
						}
					}
					rss.setRssCrawlList(rssCrawlList);
				}
			}
			rssSubscribeMapperDao.insert(rssSubscribe);
			return rss;
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}

	@Override
	public List<RssDetailVO> returnRssDetailList(Rss rss) {
		try{
			List<RssDetailVO> rssDetailVOList = RssUtil.getRSSInfo(rss.getRssUrl()).getRssDetailVOList();
			return rssDetailVOList;
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}

	@Override
	public void fetchNewRss(Rss rss) {
		RssVO rssVO = RssUtil.getRSSInfo(rss.getRssUrl());
		if(rssVO != null && !rssVO.getFingerPrint().equals(rss.getFingePrint())){
			log.info(rssVO.getLink() + "变动2，变动前fingerPrint=" + rss.getFingePrint() + ",变动后fingerPrint=" + rssVO.getFingerPrint());
			List<RssDetailVO> rssDetailList = rssVO.getRssDetailVOList();
			if(rssDetailList == null){
				return;
			}
			int record = 0;
			int size = rssDetailList.size();
			
			if(rss.getFingePrint() == null){
				record = size;
			}else{
				//先判断之前的第一个rss文章在新的rss文章中的位置
				for(RssDetailVO rssDetailVO : rssDetailList){
					if(rss.getFingePrint().equals(Md5Util.getMD5(rssDetailVO.getLink().getBytes()))){
						record = Integer.parseInt(rssDetailVO.getItemNo());
					}
				}
			}
			
			//若都未匹配，则全部放入数据库
			if(record == 0){
				record = size;
			}
			for(int i = record - 1; i >= 0; i--){
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
