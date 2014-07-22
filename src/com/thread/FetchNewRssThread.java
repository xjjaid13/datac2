package com.thread;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.po.Rss;
import com.service.RssCrawlMapperService;
import com.service.RssMapperService;
import com.util.Log;

/**  
 * 初始化更新rss线程
 * <p>2014年7月21日上午10:45:58 xijiajia</p>
 */
@Component("fetchNewRssThread")
public class FetchNewRssThread {
	
	@Autowired
	RssMapperService rssMapperService;
	
	@Autowired
	RssCrawlMapperService rssCrawlMapperService;
	
    /**
     * rss 更新线程初始化
     */
    public void init() {      
    	Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(new Runnable(){
    		public void run() {
    			try{
	    			Rss rssEntity = new Rss();
	    			List<Rss> rssList = rssMapperService.selectList(rssEntity);
	    			if(rssList != null && rssList.size() > 0){
	    				for(Rss rss : rssList){
	    					rssMapperService.fetchNewRss(rss);
	    				}
	    			}
    			}catch(Exception e){
    				Log.Error(e);
    			}
            }  
    	},100, 500, TimeUnit.SECONDS);
    }
    
}
