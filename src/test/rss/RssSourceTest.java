package test.rss;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import com.service.rss.impl.RssMapperServiceImpl;

public class RssSourceTest {

	private static Logger log = Logger.getLogger(RssSourceTest.class);
	
	@Test
	public void run() throws HttpException, IOException{
		
		String url = "http://www.csdn.net";
		String keyword = "专业人士解读《被投资人“送”入看守所》一案";
		String keyword1 = "一周极客热文：JavaScript的诞生与死亡";
		
		
		HttpClient httpClient = new HttpClient();
		HttpMethod httpMethod = new GetMethod(url);
		httpMethod.setRequestHeader("User-Agent",  
                "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:32.0) Gecko/20100101 Firefox/32.0");
		int result = httpClient.executeMethod(httpMethod);
		
		String htmlContent = httpMethod.getResponseBodyAsString();
		
		Document doc = Jsoup.parse(htmlContent);
		
		Elements links = doc.select("a");
		
		Element element1 = null;
		Element element2 = null;
		
		for(Element element : links){
			if(keyword.equals(element.text())){
				element1 = element;
			}else if(keyword1.equals(element.text())){
				element2 = element;
			}
		}
		
		if(element1 == null || element2 == null){
			log.error("未找到匹配");
			return;
		}
		
		Element elementParent = null;
		
		String compare = element1.tagName();
		
		while(element1.parent() != element2.parent()){
			element1 = element1.parent();
			element2 = element2.parent();
			elementParent = element1;
			compare = element1.tagName() + " " + compare;
		}
		elementParent = element1.parent();
		
		Elements listE = elementParent.select(compare);
		
		loop : for(Element element : listE){
			Elements childE = element.select("a");
			for(Element e : childE){
				if(e.html().equals(keyword)){
					break loop;
				}
			}
		}
		
		for(Element element : listE){
			System.out.println(element.attr("href"));
			System.out.println(element.html());
		}
		
		int position = 0;
		
		Elements allE = doc.select(elementParent.tagName());
		loop : for(Element element : allE){
			Elements childE = element.select(compare);
			for(Element e : childE){
				if(e.html().equals(keyword)){
					break loop;
				}
			}
			position++;
		}
		
		System.out.println(compare + "|||" + position);
		
	}
	
}
