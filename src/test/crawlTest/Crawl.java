package test.crawlTest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.util.DBHandle;
import com.util.HttpClientHandle;

public class Crawl {

	public static void main(String[] args) {
		DBHandle db = new DBHandle();
		db.openConnMysql();
		String[][] result = db.executeQuery("select * from carve_type");
		if(result != null && result.length > 0){
			for(int i = 0; i < result.length; i++){
				String[] rowResult = result[i];
				System.out.println(rowResult[1]);
				String content = HttpClientHandle.returnHtmlContent(rowResult[1]);
				Document document = Jsoup.parse(content);
				
				String[] pattern = rowResult[4].split(";");
				
				Elements elements = document.select(pattern[0]);
				System.out.println(elements.get(Integer.parseInt(pattern[1])).html());
			}
		}
		db.closeConn();
	}
	
}
