package test.mainTest;

import java.sql.Timestamp;
import java.util.Random;

import com.util.DBHandle;

public class DBDataTest {

	private static Random ran = new Random();
    private final static int delta = 0x9fa5 - 0x4e00 + 1;
     
    public static char getRandomHan() {
        return (char)(0x4e00 + ran.nextInt(delta)); 
    }
	
    public static String returnText(){
    	int id = new Random().nextInt(100) + 10;
    	String content = "";
    	for(int i = 0; i < id; i++){
    		content += getRandomHan();
    	}
    	return content;
    }
    
	public static void main(String[] args) {
		int id = new Random().nextInt(25) + 24;
		DBHandle db = new DBHandle();
		db.openConnMysql();
		for(;;){
			String sql = "insert into rss_crawl (rssId,resourceTitle,resourceUrl,updateTime,resourceDesc) "
					+ "values ("+id+",'"+returnText()+"','http://www.wxjj.com.cn','"+new Timestamp(System.currentTimeMillis())+"','"+returnText()+"')";
			db.executeUpdate(sql);
		}
	}
	
}
