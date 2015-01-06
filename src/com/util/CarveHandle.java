package com.util;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.vo.SelectorVO;

@Component
public class CarveHandle {

	public SelectorVO returnTemplatePattern(String content,String url){
		
		List<String> targetList = new ArrayList<String>();
		targetList.add("");
		
		String selector = HtmlSelectorHandle.returnFirstSelector(content);
		
		String htmlContent = HttpClientHandle.returnHtmlContent(url);
		
		Document doc = Jsoup.parse(htmlContent);
		Elements elements = doc.select(selector);
		
		Element currentE = null;
		
		if(!DataHandle.isNullOrEmpty(elements)){
			String feature = featureMd5(Jsoup.parse(content).body().child(0));
			for(Element e : elements){
				String eContent = featureMd5(e);
				if(eContent.equals(feature)){
					currentE = e;
				}
			}
		}
		
		if(currentE == null){
			System.out.println("null");
			return null;
		}
		
		SelectorVO selectorVO = HtmlSelectorHandle.returnOnlySelector(currentE, doc);
		return selectorVO;
	}
	
	public static String featureMd5(Element e){
		String text = e.text();
		List<String> linkList = CrawlHandle.returnExtract(e.html());
		StringBuilder stringBuilder = new StringBuilder(text);
		for(String s : linkList){
			stringBuilder.append(s);
		}
		return Md5Util.getMD5(stringBuilder.toString().getBytes());
	}
	
	static class HtmlSelectorHandle{
		public static String returnSelector(String html){
			String result = "";
			Document doc = Jsoup.parse(html);
			return recursion(doc.body().child(0),result);
		}
		
		public static String returnFirstSelector(String html){
			Document doc = Jsoup.parse(html);
			return returnCurrentSelector(doc.body().child(0));
		}
		
		public static String returnCurrentSelector(Element element){
			String result = "";
			if(!DataHandle.isNullOrEmpty(element.attr("id"))){
				result += "#" + element.attr("id");
			}else if(!DataHandle.isNullOrEmpty(element.attr("class"))){
				result += "." + element.attr("class");
			}else{
				result += element.tagName();
			}
			return result;
		}
		
		public static SelectorVO returnOnlySelector(Element element,Document doc){
			int seqNum = 0;
			Element elementParentParent = element.parent();
			Elements elements = elementParentParent.children();
			for(int i = 0; i < elements.size(); i++){
				if(elements.get(i) == element.parent()){
					seqNum = i;
				}
			}
			SelectorVO selectorVO = new SelectorVO();
			String selector = "";
			for(;;){
				selector = returnCurrentSelector(element) + " " + selector;
				Element elementParent = element.parent();
				if(elementParent.tag().equals("body")){
					selectorVO.setSelectString(selector);
					selectorVO.setSeqNum(seqNum);
					return selectorVO;
				}else if(doc.select(selector).size() == 1){
					selectorVO.setSelectString(selector);
					selectorVO.setSeqNum(0);
					return selectorVO;
				}
				element = element.parent();
			}
		}
		
		static String recursion(Element element,String result){
			result += returnCurrentSelector(element);
			Elements elementList = element.children();
			if(DataHandle.isNullOrEmpty(elementList)){
				return result;
			}else{
				result += " ";
				Element elementChild = elementList.get(0);
				if(elementList.size() > 1){
					return result + elementChild.tagName();
				}else{
					return recursion(elementChild,result);
				}
			}
		}
	}
	
}
