package com.util;

import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndImage;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import com.vo.RssDetailVO;
import com.vo.RssVO;

/**
 * Rss工具类
 * taylor 2014-7-20下午11:08:31
 */
public class RssUtil {

	private static Logger log = Logger.getLogger(RssUtil.class);
	
	/**
	 * 获得rss结果
	 * @param xmlRemotePath rssurl
	 * @return
	 * @throws IOException 
	 */
	public static RssVO getRSSInfo(String xmlRemotePath) {
		try {
			SyndFeed feed = null;
			SyndFeedInput input = new SyndFeedInput();
			
			if(!xmlRemotePath.startsWith("http")){
				feed = input.build(new StringReader(xmlRemotePath));
			}else{
				URL feedUrl = new URL(xmlRemotePath);
		        XmlReader xmlReader = new XmlReader(feedUrl);
		        feed = input.build(xmlReader);
			}
			
	        //新建一个rssVO对象
	        RssVO rssVO = new RssVO();
	        
	        //rss标题
			String title = DataHandle.handleValue(feed.getTitle());
			rssVO.setTitle(title);
			
			//rss描述
			String description = DataHandle.handleValue(feed.getDescription());
			rssVO.setDescription(description);
			
			//rss语言
			String language = DataHandle.handleValue(feed.getLanguage());
			rssVO.setLanguage(language);
			
			//rss copyright
			String copyright = DataHandle.handleValue(feed.getCopyright());
			rssVO.setCopyright(copyright);
			
			rssVO.setLink(xmlRemotePath);
			rssVO.setFingerPrint("");
			
			//rss icon
			SyndImage syndImage = feed.getImage();
	        if(syndImage != null){
	        	rssVO.setIcon(DataHandle.handleValue(syndImage.getUrl()));
	        }else{
	        	rssVO.setIcon("");
	        }
			

	        List<RssDetailVO> rssDetailVOList = null;
	        @SuppressWarnings("unchecked")
			List<SyndEntry> list = feed.getEntries();
	        if(list != null && list.size() > 0){
	        	rssDetailVOList = new ArrayList<RssDetailVO>();
	        	int i = 0;
	        	for(SyndEntry feedDetail : list){
	        		//新建rss detail对象
	        		RssDetailVO rssDetailVO = new RssDetailVO();
	        		
	        		//编号
	        		int itemNo = i++;
	        		rssDetailVO.setItemNo(itemNo + "");
	        		
	        		//作者
					String detailAuthor = DataHandle.handleValue(feedDetail.getAuthor());
					rssDetailVO.setAuthor(detailAuthor);
					
					//标题
					String detailTitle = DataHandle.handleValue(feedDetail.getTitle());
					rssDetailVO.setTitle(detailTitle);
					
					//描述
					SyndContent syndContent = feedDetail.getDescription();
					if(syndContent != null){
						String detailDescription = DataHandle.handleValue(feedDetail.getDescription().toString());
						if(detailDescription.length() > 1000){
							detailDescription = detailDescription.substring(0,999);
						}
						rssDetailVO.setDescription(detailDescription);
					}
					
					//连接
					String detailLink = DataHandle.handleValue(feedDetail.getLink());
					rssDetailVO.setLink(detailLink);
					
					//日期
					Date date = feedDetail.getPublishedDate();
					if(date == null){
						rssDetailVO.setPubDate(new Timestamp(System.currentTimeMillis()) + "");
					}else{
						rssDetailVO.setPubDate(new Timestamp(feedDetail.getPublishedDate().getTime()) + "");
					}
					
					//获得最新的文章标题，用来判断是否与上次最新的文章相同
					if(i == 1){
						rssVO.setFingerPrint(Md5Util.getMD5(detailLink.getBytes()));
					}
					rssDetailVOList.add(rssDetailVO);
	        	}
	        	rssVO.setRssDetailVOList(rssDetailVOList);
	        }
			return rssVO;
		} catch (Exception e) {
			try{
				if(xmlRemotePath.startsWith("http")){
					e.printStackTrace();
					log.error(xmlRemotePath + "异常,替换成字符串再次解析: " + e.getMessage());
					String urlContent = new String(Jsoup.connect(xmlRemotePath).execute().bodyAsBytes());
					return getRSSInfo(urlContent);
				}else{
					log.error("再次rss解析异常");
				}
			}catch(Exception e1){
				e.printStackTrace();
				log.error(xmlRemotePath + "异常1: " + e.getMessage());
			}
		}
		return null;
	}
	
	public static void main(String[] args) {
		
		RssVO rssVo = RssUtil.getRSSInfo("http://rss.huanqiu.com/world/hot.xml");
		System.out.println(rssVo.getTitle());
		
	}
	
}
