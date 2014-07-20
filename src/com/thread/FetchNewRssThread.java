package com.thread;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.po.Rss;
import com.service.RssCrawlMapperService;
import com.service.RssMapperService;
import com.util.FileHandle;
import com.util.Log;

@Component("fetchNewRssThread")
public class FetchNewRssThread {
	
	@Autowired
	RssMapperService rssMapperService;
	
	@Autowired
	RssCrawlMapperService rssCrawlMapperService;
	
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
    	},10, 100, TimeUnit.SECONDS);
    }
    
    public static void main(String[] args) {
    	//-801604547
		String content = FileHandle.readFile("D:/test.txt");
		System.out.println(content.hashCode());
	}
    
}
