package test.rss;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.entity.Rss;
import com.service.RssMapperService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring.xml")
public class RssTest {

	
	@Autowired
	RssMapperService rssMapperService;
	
	@Test
	public void addRss(){
		Rss rss = new Rss();
		rss.setRssIcon("sss");
		rss.setRssUrl("111");
		rss.setRssTitle("title");
		rssMapperService.insert(rss);
	}
	
	@Test
	public void updateRss(){
		Rss rss = createRss();
		rss.setCondition(" rssTitle = '11'");
		rss.setRssIcon("update2");
		rssMapperService.update(rss);
	}
	
	@Test
	public void deleteRss(){
		Rss rss = createRss();
		Rss obj = new Rss();
		obj.setRssId(17);
		rssMapperService.delete(obj);
	}
	
	@Test
	public void selectRssList(){
		Rss rss = new Rss();
		rss.setRssTitle("11");
		List<Rss> rssList = rssMapperService.selectList(rss);
		System.out.println("rssList.size()" + rssList.size());
	}
	
	public Rss createRss(){
		Rss rss = new Rss();
		rss.setRssIcon("test");
		rss.setRssUrl("test");
		rss.setRssTitle("test");
		rss.setRssId(rssMapperService.insertAndReturnId(rss));
		return rss;
	}
	
}