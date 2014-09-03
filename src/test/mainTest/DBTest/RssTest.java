package test.mainTest.DBTest;

import com.util.DBHandle;
import com.util.RssUtil;
import com.vo.RssVO;

public class RssTest {

	public static void main(String[] args) {
		DBHandle dbHandle = new DBHandle();
		dbHandle.openConnMysql();
		String sql = "select rssUrl from rss";
		String[][] sqlList = dbHandle.executeQuery(sql);
		if(sqlList != null && sqlList.length > 0){
			for(String[] arr : sqlList){
				RssVO q = RssUtil.getRSSInfo(arr[0]);
				if(q != null){
					System.out.println(RssUtil.getRSSInfo(arr[0]).getTitle());
				}
			}
		}
		dbHandle.closeConn();
	}
	
}
