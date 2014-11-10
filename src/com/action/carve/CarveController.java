package com.action.carve;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.po.rss.Rss;
import com.service.rss.RssMapperService;
import com.util.DataHandle;

@Controller
@RequestMapping("carve")
public class CarveController {

	@Autowired
	RssMapperService rssMapperService;
	
	@RequestMapping("index")
	public String index(){
		return "carve/index";
	}
	
	@RequestMapping("test")
	public void test(HttpServletRequest request,HttpServletResponse response){
		try{
			/** 开始页 */
	        int sPage = DataHandle.returnValueInt(request, "iDisplayStart");
	        /** 每页数量 */
	        int sRows = DataHandle.returnValueInt(request, "iDisplayLength");
	        /** 排序列位置 */
	        String iSortCol = DataHandle.returnValue(request, "iSortCol_0");
	        /** 排序方式 */
	        String sOrderType = DataHandle.returnValue(request, "sSortDir_0");
	        /** 排序列 */
	        String sOrderCol = "";
	        /** 所有列code */
	        String sColumns = DataHandle.returnValue(request, "sColumns");
	        String[] cols = new String[] {};
	        if (sColumns != null) {
	            cols = sColumns.split(",");
	        }
	        if (!DataHandle.isNullOrEmpty(iSortCol)) {
	        	/** 获取当前排序列 */
	            sOrderCol = cols[Integer.valueOf(iSortCol)];
	        }
	        
	        String orderType = "";
	        String orderCol = "";
	        /** 设置排序参数 */
	        if (!DataHandle.isNullOrEmpty(sOrderCol)) {
	            orderCol = " order by " + sOrderCol;
	            if (!DataHandle.isNullOrEmpty(sOrderType)) {
	                orderType = sOrderType;
	            }
	            orderCol += " " + orderType;
	        }
	        
			Rss rss = new Rss();
			rss.setStartPage(sPage);
			rss.setPage(sRows);
			rss.setCondition(orderCol);
			List<Rss> menuList = rssMapperService.selectList(rss);
			int count = rssMapperService.selectCount(rss);
			JSONObject json = new JSONObject();
			json = new JSONObject();
			json.put("iTotalRecords", 10);  //本次查询记录数
			json.put("iTotalDisplayRecords", count); //记录总数
			json.put("aaData", menuList); 
			response.getWriter().write(json.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
