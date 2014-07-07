package com.action.rss;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.action.BaseAction;
import com.entity.RssType;
import com.entity.User;
import com.service.RssTypeMapperService;
import com.util.DataHandle;

@Controller
@RequestMapping("rss")
public class RssController extends BaseAction{

	@Autowired
	RssTypeMapperService rssTypeMapperService;
	
	@RequestMapping("myIndex")
	public String myIndex(HttpSession session, Model model,
			HttpServletRequest request){
		return "rss/index";
	}
	
	@RequestMapping("returnRssTypeTree")
	public void returnRssTypeTree(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException{
		String id = DataHandle.returnValue(request, "id");
		User user = returnUser(session);
		JSONObject jsonObject = createJosnObject();
		RssType rssType = new RssType();
		JSONArray jsonArray = new JSONArray();
		response.setContentType("application/json; charset=utf-8");
		if("#".equals(id)){
			jsonObject.put("id", "0");
			jsonObject.put("parent", "#");
			jsonObject.put("text", "订阅");
			if(rssTypeMapperService.isChildren(-1, user.getUserId())){
				jsonObject.put("children", true);
			}
			jsonArray.add(jsonObject);
			response.getWriter().write(jsonArray.toString());
		}else{
			rssType.setUserId(user.getUserId());
			if("root".equals(id)){
				rssType.setParentId(0);
			}else{
				rssType.setParentId(Integer.parseInt(id));
			}
			List<RssType> rssTypeList = rssTypeMapperService.selectList(rssType);
			if(rssTypeList != null && rssTypeList.size() > 0){
				for(RssType rssTypeNew : rssTypeList){
					jsonObject = new JSONObject();
					jsonObject.put("id", rssTypeNew.getRssTypeId() + "");
					jsonObject.put("parent", rssTypeNew.getParentId() + "");
					jsonObject.put("text", rssTypeNew.getTypeName());
					if(rssTypeMapperService.isChildren(rssTypeNew.getRssTypeId(), user.getUserId())){
						jsonObject.put("children", true);
					}
					jsonArray.add(jsonObject);
				}
			}
			response.getWriter().write(jsonArray.toString());
		}
	}
	
	@RequestMapping("myAddRssType")
	public void myAddRssType(RssType rssType,HttpServletResponse response,HttpSession session) throws IOException{
		User user = returnUser(session);
		rssType.setUserId(user.getUserId());
		int rssTypeId = rssTypeMapperService.insertAndReturnId(rssType);
		JSONObject jsonObject = createJosnObject();
		jsonObject.put("rssTypeId", rssTypeId);
		writeResult(response, jsonObject);
	}
	
	@RequestMapping("myUpdateRssType")
	public void myUpdateRssType(RssType rssType,HttpServletResponse response) throws IOException{
		rssTypeMapperService.update(rssType);
		writeResult(response, createJosnObject());
	}
	
	@RequestMapping("myDleteRssType")
	public void myDleteRssType(RssType rssType,HttpServletResponse response) throws IOException{
		rssTypeMapperService.delete(rssType);
		writeResult(response, createJosnObject());
	}
	
}
