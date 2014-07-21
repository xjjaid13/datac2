package com.util;

import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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

	/**
	 * 获得rss结果
	 * @param xmlRemotePath rssurl
	 * @return
	 */
	public static RssVO getRSSInfo(String xmlRemotePath) {
		try {
			
			URL feedUrl = new URL(xmlRemotePath);
			
	        SyndFeedInput input = new SyndFeedInput();
	        XmlReader xmlReader = new XmlReader(feedUrl);
	        SyndFeed feed = input.build(xmlReader);
	        
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
					String detailDescription = DataHandle.handleValue(feedDetail.getDescription().toString());
					rssDetailVO.setDescription(detailDescription);
					
					//连接
					String detailLink = DataHandle.handleValue(feedDetail.getLink());
					rssDetailVO.setLink(detailLink);
					
					//解决默认时间的问题
					String detailPubDate =  new Timestamp(feedDetail.getPublishedDate().getTime()) + "";
					rssDetailVO.setPubDate(detailPubDate);
					
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
			Log.Error(xmlRemotePath + "异常",e);
		}
		return null;
	}

}
