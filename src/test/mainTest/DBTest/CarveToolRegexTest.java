package test.mainTest.DBTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.util.DataHandle;

public class CarveToolRegexTest {

	public static void main(String[] args) {
		String content = "<ul class=\"p1\">"+
		"<li class=\"today\"><span class=\"date\">10/29</span><a target=\"_blank\" title=\"CrossApp 0.3.9 发布，强化文字系统，细节优化\" href=\"/news/56561/crossapp-0-3-9\">CrossApp 0.3.9 发布，强化文字系统，细节优化</a></li>"+
		    		"<li class=\"today\"><span class=\"date\">10/29</span><a target=\"_blank\" title=\"ocserv 0.8.7 发布，OpenConnect VPN 服务器\" href=\"/news/56560/ocserv-0-8-7\">ocserv 0.8.7 发布，OpenConnect VPN 服务器</a></li>"+
		    		"<li><span class=\"date\">10/29</span><a target=\"_blank\" title=\"然之协同1.5beta版发布，新增任务看板和大纲功能\" href=\"/news/56558/ranzhi-1-5\">然之协同1.5beta版发布，新增任务看板和大纲功能</a></li>"+
		    		"<li><span class=\"date\">10/29</span><a target=\"_blank\" title=\"Web应用防火墙 FreeWAF-1.2.2 版本发布\" href=\"/news/56557/freewaf-1-2-2\">Web应用防火墙 FreeWAF-1.2.2 版本发布</a></li>"+
		    		"<li><span class=\"date\">10/29</span><a target=\"_blank\" title=\"PHPBB 正式推出具有变革性的新版本 3.1\" href=\"/news/56556/phpbb-3-1\">PHPBB 正式推出具有变革性的新版本 3.1</a></li>"+
		    		"<li><span class=\"date\">10/29</span><a target=\"_blank\" title=\"EntboostChat 0.9（越狱预览版）发布，IOS开源IM\" href=\"/news/56555/entboostchat-0-9\">EntboostChat 0.9（越狱预览版）发布，IOS开源IM</a></li>"+
		    		"<li><span class=\"date\">10/29</span><a target=\"_blank\" title=\"Puppy Linux 6.0 &quot;Tahrpup&quot; 发布\" href=\"/news/56553/puppy-linux-6-0-tahrpup\">Puppy Linux 6.0 \"Tahrpup\" 发布</a></li>"+
		    		"<li><span class=\"date\">10/29</span><a target=\"_blank\" title=\"POWA 1.2 发布，PostgreSQL 数据负载分析工具\" href=\"/news/56552/powa-1-2\">POWA 1.2 发布，PostgreSQL 数据负载分析工具</a></li>"+
		    		"<li><span class=\"date\">10/29</span><a target=\"_blank\" title=\"Nginx 1.7.7 发布\" href=\"/news/56551/nginx-1-7-7\">Nginx 1.7.7 发布</a></li>"+
		    		"<li><span class=\"date\">10/29</span><a target=\"_blank\" title=\"React v0.12 发布，构建用户界面的 JavaScript 库\" href=\"/news/56550/react-v0-12\">React v0.12 发布，构建用户界面的 JavaScript 库</a></li>"+
		    		    		"</ul>";
		Document doc = Jsoup.parse(content);
		Element e = doc.body().children().get(0);
		
		String tag = e.tagName();
		
		Elements elements = null;
		
		Elements child = e.children();
		for(Element childE : child){
			Elements selectE = doc.select(tag + ">" + childE.tagName());
			if(selectE.size() > 1){
				elements = selectE;
				break;
			}
		}
		
		if(elements == null){
			return;
		}
		
		Pattern hrefPattern = Pattern.compile("<a(.+?)href=[\"|'](.+?)[\"|']>(.*?)</a>");
		Pattern imgPattern = Pattern.compile("<img(.*?)src=[\"|'](.*?)[\"|']");
		
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		
		for(Element element : elements){
			Map<String, String> map = new HashMap<String, String>();
			String href = "";
			String hrefText = "";
			String img = "";
			String text = "";
			
			String htmlContent = element.html();
			Matcher hrefMatcher = hrefPattern.matcher(htmlContent);
			if(hrefMatcher.find()){
				href = hrefMatcher.group(2);
				hrefText = hrefMatcher.group(3);
			}
			Matcher imgMatcher = imgPattern.matcher(htmlContent);
			if(imgMatcher.find()){
				img = imgMatcher.group(2);
			}
			if(DataHandle.isNullOrEmpty(href) && DataHandle.isNullOrEmpty(hrefText) && DataHandle.isNullOrEmpty(img)){
				text = element.text();
			}
			System.out.println("href="+href);
			System.out.println("hrefText="+hrefText);
			System.out.println("img="+img);
			System.out.println("text="+text);
			map.put("href", href);
			map.put("hrefText", hrefText);
			map.put("img", img);
			map.put("text", text);
			list.add(map);
		}
		
	}
	
}
