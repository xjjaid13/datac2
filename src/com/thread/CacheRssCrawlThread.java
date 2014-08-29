package com.thread;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.po.rss.Rss;
import com.po.rss.RssCrawl;
import com.service.rss.RssCrawlMapperService;
import com.service.rss.RssMapperService;
import com.util.Constant;
import com.util.FixQueue;

/**  
 * 初始化rss链接缓存
 * <p>2014年8月9日下午2:06:26 xijiajia</p>
 */
@Component("cacheRssCrawl")
public class CacheRssCrawlThread {

	//log
	private static Logger log = Logger.getLogger(CacheRssCrawlThread.class);
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	@Autowired
	private RssMapperService rssMapperService;
	
	@Autowired
	private RssCrawlMapperService rssCrawlMapperService;
	
	public void init(){
		List<Rss> rssList = rssMapperService.selectList(new Rss());
		if(rssList != null && rssList.size() > 0){
			BoundHashOperations<String, String, FixQueue<RssCrawl>> hashMap = redisTemplate.boundHashOps(Constant.RSSCRAWL);
			for(Rss rss : rssList){
				RssCrawl rssCrawl = new RssCrawl();
				rssCrawl.setRssId(rss.getRssId());
				rssCrawl.setStartPage(0);
				rssCrawl.setPage(3);
				List<RssCrawl> rssCrawlList = rssCrawlMapperService.selectList(rssCrawl);
				FixQueue<RssCrawl> queue = null;
				if(rssCrawlList != null && rssCrawlList.size() > 0){
					queue = new FixQueue<RssCrawl>();
					for(RssCrawl crawl : rssCrawlList){
						queue.add(crawl);
					}
				}
				hashMap.put(Constant.RSSCRAWL + "-" + rss.getRssId(), queue);
				log.info(Constant.RSSCRAWL + "-" + rss.getRssId() + " done.");
			}
		}
	}
	
}
