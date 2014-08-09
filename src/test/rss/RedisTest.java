package test.rss;

import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sun.jmx.remote.internal.ArrayQueue;
import com.util.FixQueue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring.xml")
public class RedisTest {

	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	@Test
	public void test(){
		BoundHashOperations<String, String, FixQueue<String>> list = redisTemplate.boundHashOps("rssCrawl");
		FixQueue<String> queue = (FixQueue<String>) list.get("rssCrawl-24");
		if(queue != null && queue.size() > 0){
			for(String str : queue){
				System.out.println(str);
			}
		}
		System.out.println("done");
		
	}
	
}
