package com.util;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CrawlHandle {

	public static List<String> returnExtract(String htmlContent){
		List<String> list = new ArrayList<String>();
		Document doc = Jsoup.parse(htmlContent);
        Elements links = doc.select("a[href]");
        Elements media = doc.select("[src]");
        Elements imports = doc.select("link[href]");
        
        for (Element src : links) {
        	String href = src.attr("href");
        	if(!DataHandle.isNullOrEmpty(href)){
        		list.add(href);
        	}
        }
        
        for (Element src : media) {
        	String href = src.attr("src");
        	if(!DataHandle.isNullOrEmpty(href)){
        		list.add(href);
        	}
        }

		for (Element src : imports) {
			String href = src.attr("href");
			if(!DataHandle.isNullOrEmpty(href)){
				list.add(href);
			}
	    }
		return list;
	}
	
	public static List<String> returnTitle(String htmlContent){
		List<String> list = new ArrayList<String>();
		Document doc = Jsoup.parse(htmlContent);
		Elements links = doc.select("a");
		if(DataHandle.isNotNullOrEmpty(links)){
			for(Element e : links){
				list.add(e.text());
			}
		}
		return list;
	}
	
	public static String returnPathUrl(String url){
		return url.replaceAll("http(.*?)//(.+?)/(.*)", "$2/$3").replace(":", "-");
	}
	
}
