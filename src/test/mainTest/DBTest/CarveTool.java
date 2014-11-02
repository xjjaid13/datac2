package test.mainTest.DBTest;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.util.CrawlHandle;
import com.util.DataHandle;
import com.util.HttpClientHandle;
import com.util.Md5Util;

public class CarveTool {

	public static void main(String[] args) {
		
		Document d = Jsoup.parse(HttpClientHandle.returnHtmlContent("http://www.oschina.net"));
		System.out.println(d.select("#ProjectNews .p1"));
//		
//		ArrayList<SelectorVO> selectArrayList = new ArrayList<SelectorVO>();
//		
//		List<String> targetList = new ArrayList<String>();
//		targetList.add("");
//		
//		String content = "<ul class=\"p1\">"+
//    					    		"<li class=\"today\"><span class=\"date\">10/29</span><a target=\"_blank\" title=\"CrossApp 0.3.9 发布，强化文字系统，细节优化\" href=\"/news/56561/crossapp-0-3-9\">CrossApp 0.3.9 发布，强化文字系统，细节优化</a></li>"+
//			    					    		"<li class=\"today\"><span class=\"date\">10/29</span><a target=\"_blank\" title=\"ocserv 0.8.7 发布，OpenConnect VPN 服务器\" href=\"/news/56560/ocserv-0-8-7\">ocserv 0.8.7 发布，OpenConnect VPN 服务器</a></li>"+
//			    					    		"<li><span class=\"date\">10/29</span><a target=\"_blank\" title=\"然之协同1.5beta版发布，新增任务看板和大纲功能\" href=\"/news/56558/ranzhi-1-5\">然之协同1.5beta版发布，新增任务看板和大纲功能</a></li>"+
//			    					    		"<li><span class=\"date\">10/29</span><a target=\"_blank\" title=\"Web应用防火墙 FreeWAF-1.2.2 版本发布\" href=\"/news/56557/freewaf-1-2-2\">Web应用防火墙 FreeWAF-1.2.2 版本发布</a></li>"+
//			    					    		"<li><span class=\"date\">10/29</span><a target=\"_blank\" title=\"PHPBB 正式推出具有变革性的新版本 3.1\" href=\"/news/56556/phpbb-3-1\">PHPBB 正式推出具有变革性的新版本 3.1</a></li>"+
//			    					    		"<li><span class=\"date\">10/29</span><a target=\"_blank\" title=\"EntboostChat 0.9（越狱预览版）发布，IOS开源IM\" href=\"/news/56555/entboostchat-0-9\">EntboostChat 0.9（越狱预览版）发布，IOS开源IM</a></li>"+
//			    					    		"<li><span class=\"date\">10/29</span><a target=\"_blank\" title=\"Puppy Linux 6.0 &quot;Tahrpup&quot; 发布\" href=\"/news/56553/puppy-linux-6-0-tahrpup\">Puppy Linux 6.0 \"Tahrpup\" 发布</a></li>"+
//			    					    		"<li><span class=\"date\">10/29</span><a target=\"_blank\" title=\"POWA 1.2 发布，PostgreSQL 数据负载分析工具\" href=\"/news/56552/powa-1-2\">POWA 1.2 发布，PostgreSQL 数据负载分析工具</a></li>"+
//			    					    		"<li><span class=\"date\">10/29</span><a target=\"_blank\" title=\"Nginx 1.7.7 发布\" href=\"/news/56551/nginx-1-7-7\">Nginx 1.7.7 发布</a></li>"+
//			    					    		"<li><span class=\"date\">10/29</span><a target=\"_blank\" title=\"React v0.12 发布，构建用户界面的 JavaScript 库\" href=\"/news/56550/react-v0-12\">React v0.12 发布，构建用户界面的 JavaScript 库</a></li>"+
//			    					    		    		"</ul>";
//		
//		String url = "http://www.oschina.net";
//		String templateString = "(?i)<a[^>]*?href=(['\"\"]?)(?<href>[^'\"\"]*?)\\1[^>]*?>(.*?)</a>";
//
////		Pattern pattern = Pattern.compile(templateString);
////		Matcher matcher = pattern.matcher(content);
////		while(matcher.find()){
////			System.out.println(matcher.group(3));
////		}
//		
//		String selector = HtmlSelectorHandle.returnFirstSelector(content);
//		
//		String htmlContent = HttpClientHandle.returnHtmlContent(url);
//		
//		Document doc = Jsoup.parse(htmlContent);
//		Elements elements = doc.select(selector);
//		
//		Element currentE = null;
//		
//		if(!DataHandle.isNullOrEmpty(elements)){
//			String feature = featureMd5(Jsoup.parse(content).body().child(0));
//			for(Element e : elements){
//				String eContent = featureMd5(e);
//				if(eContent.equals(feature)){
//					currentE = e;
//				}
//			}
//		}
//		
//		if(currentE == null){
//			System.out.println("null");
//			return;
//		}
//		
//		SelectorVO selectorVO = HtmlSelectorHandle.returnOnlySelector(currentE, doc);
//		System.out.println(selectorVO.toString());
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
	
	static class SelectorVO {
		
		private String selectString;
		
		private Integer seqNum;

		public String getSelectString() {
			return selectString;
		}

		public void setSelectString(String selectString) {
			this.selectString = selectString;
		}

		public Integer getSeqNum() {
			return seqNum;
		}

		public void setSeqNum(Integer seqNum) {
			this.seqNum = seqNum;
		}
		
		public String toString(){
			return "selectString=(" + selectString + ");seqNum=" + seqNum;
		}
		
	}
	
}
