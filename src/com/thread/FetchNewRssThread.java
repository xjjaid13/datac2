package com.thread;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.entity.Rss;
import com.entity.RssCrawl;
import com.service.RssCrawlMapperService;
import com.service.RssMapperService;
import com.util.FileHandle;
import com.util.RssUtil;

@Component("fetchNewRssThread")
public class FetchNewRssThread {
	
	@Autowired
	RssMapperService rssMapperService;
	
	@Autowired
	RssCrawlMapperService rssCrawlMapperService;
	
    public void init() {      
    	Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(new Runnable(){
    		public void run() {
    			Rss rssEntity = new Rss();
    			List<Rss> rssList = rssMapperService.selectList(rssEntity);
    			if(rssList != null && rssList.size() > 0){
    				for(Rss rss : rssList){
    					Map<String, Object> rssMap = RssUtil.getRSSInfo(rss.getRssUrl());
    					if(!rssMap.get("fingerPrint").toString().equals(rss.getFingePrint())){
    						List<Map<String,String>> rssDetailList = (List<Map<String,String>>)rssMap.get("list");
    						if(rssDetailList == null){
    							continue;
    						}
    						int record = 0;
    						RssCrawl rssCrawl = new RssCrawl();
    						rssCrawl.setRssId(rss.getRssId());
    						rssCrawl.setStartPage(0);
    						rssCrawl.setPage(1);
    						rssCrawl.setCondition("order by rssCrawlId desc");
    						List<RssCrawl> rssCrawlList = rssCrawlMapperService.selectList(rssCrawl);
    						if(rssCrawlList != null && rssCrawlList.size() > 0){
    							RssCrawl rssCrawlSingle = rssCrawlList.get(0);
    							for(Map<String,String> map : rssDetailList){
    								if(map.get("link").equals(rssCrawlSingle.getResourceUrl())){
    									record = Integer.parseInt(map.get("itemNo"));
    								} 
    							}
    						}
    						int size = rssDetailList.size();
    						if(record == 0){
        						rssCrawl.setRssId(rss.getRssId());
        						rssCrawl.setStartPage(0);
        						rssCrawl.setPage(size);
        						rssCrawl.setCondition("order by rssCrawlId desc");
        						rssCrawlList = rssCrawlMapperService.selectList(rssCrawl);
        						if(rssCrawlList != null && rssCrawlList.size() > 0){
        							for(RssCrawl rssCrawlNew : rssCrawlList){
        								for(Map<String,String> map : rssDetailList){
            								if(map.get("link").equals(rssCrawlNew.getResourceUrl())){
            									record = Integer.parseInt(map.get("itemNo"));
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
    							Map map = rssDetailList.get(i);
    							rssCrawl = new RssCrawl();
    							rssCrawl.setRssId(rss.getRssId());
    							rssCrawl.setResourceDesc(map.get("description").toString());
    							rssCrawl.setResourceTitle(map.get("title").toString());
    							rssCrawl.setResourceUrl(map.get("url").toString());
    							rssCrawlMapperService.insert(rssCrawl);
    						}
    					}
    				}
    			}
            }  
    	},5, 5, TimeUnit.SECONDS);
    }
    
    public static void main(String[] args) {
    	//-801604547
		String content = FileHandle.readFile("D:/test.txt");
		System.out.println(content.hashCode());
	}
    
}
