package test.rss;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.po.Rss;
import com.po.RssSubscribe;
import com.service.RssMapperService;
import com.service.RssSubscribeMapperService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring.xml")
public class RssTest {

	
	@Autowired
	RssMapperService rssMapperService;
	
	@Autowired
	RssSubscribeMapperService rssSubscribeMapperService;
	
	public void addRss(){
		Rss rss = new Rss();
		rss.setRssIcon("sss");
		rss.setRssUrl("111");
		rss.setRssTitle("title");
		rssMapperService.insert(rss);
	}
	
	public void updateRss(){
		Rss rss = createRss();
		rss.setCondition(" and rssTitle = '11'");
		rss.setRssIcon("update2");
		rssMapperService.update(rss);
	}
	
	public void deleteRss(){
		Rss rss = createRss();
		Rss obj = new Rss();
		obj.setRssId(17);
		rssMapperService.delete(obj);
	}
	
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
	
	@Test
	public void returnTopRss(){
		RssSubscribe rssSubscribe = new RssSubscribe();
		rssSubscribe.setRssTypeId(10);
		List<Rss> rssList = rssSubscribeMapperService.returnTopRssList(rssSubscribe);
		System.out.println(rssList.size());
	}
	
}