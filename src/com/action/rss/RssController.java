package com.action.rss;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.action.BaseAction;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.exception.common.ControllerException;
import com.exception.common.TipException;
import com.po.rss.Rss;
import com.po.rss.RssCrawl;
import com.po.rss.RssSubscribe;
import com.po.rss.RssType;
import com.po.user.User;
import com.service.rss.RssCrawlMapperService;
import com.service.rss.RssMapperService;
import com.service.rss.RssSubscribeMapperService;
import com.service.rss.RssTypeMapperService;
import com.util.Constant;
import com.util.DataHandle;

@Controller
@RequestMapping("rss")
public class RssController extends BaseAction{

	@Autowired
	RssTypeMapperService rssTypeMapperService;
	
	@Autowired
	RssMapperService rssMapperService;
	
	@Autowired
	RssSubscribeMapperService rssSubscribeMapperService;
	
	@Autowired
	RssCrawlMapperService rssCrawlMapperService;
	
	@RequestMapping("myIndex")
	public String myIndex(HttpSession session, Model model,
			HttpServletRequest request){
		return "rss/index";
	}
	
	@RequestMapping("myReturnRssTypeTree")
	public void myReturnRssTypeTree(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try{
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
		}catch(Exception e){
			throw new ControllerException(e);
		}
	}
	
	@RequestMapping("myAddRssType")
	public void myAddRssType(RssType rssType,HttpServletResponse response,HttpSession session){
		try{
			User user = returnUser(session);
			rssType.setUserId(user.getUserId());
			
			int parentId = rssType.getParentId();
			if(parentId == 0){
				rssType.setParentString(";0;");
			}else{
				RssType rssTypeParent = new RssType();
				rssTypeParent.setRssTypeId(parentId);
				rssTypeParent = rssTypeMapperService.select(rssType);
				rssType.setParentString(rssTypeParent.getParentString() + rssTypeParent.getRssTypeId() + ";");
			}
			rssTypeMapperService.insert(rssType);
			JSONObject jsonObject = createJosnObject();
			jsonObject.put("rssTypeId", rssType.getRssTypeId());
			writeResult(response, jsonObject);
		}catch(Exception e){
			throw new ControllerException(e);
		}
	}
	
	@RequestMapping("myUpdateRssType")
	public void myUpdateRssType(RssType rssType,HttpServletResponse response){
		try{
			rssTypeMapperService.update(rssType);
			writeResult(response, createJosnObject());
		}catch(Exception e){
			throw new ControllerException(e);
		}
		
	}
	
	@RequestMapping("myDleteRssType")
	public void myDleteRssType(RssType rssType,HttpServletResponse response){
		try{
			rssTypeMapperService.delete(rssType);
			writeResult(response, createJosnObject());
		}catch(Exception e){
			throw new ControllerException(e);
		}
	}
	
	@RequestMapping("myAddRssAdnSubscribe")
	public void myAddRssAdnSubscribe(Rss rss,int parentId,HttpServletResponse response){
		try{
			rss = rssMapperService.insertRss(rss, parentId);
			JSONObject jsonObject = createJosnObject();
			jsonObject.put("rss", rss);
			writeResult(response, jsonObject);
		}catch(TipException e){
			throw new TipException(e);
		}catch(Exception e){
			throw new ControllerException(e);
		}
	}
	
	@RequestMapping("myCancelSubscribe")
	public void myCancelSubscribe(RssSubscribe rssSubscribe,HttpServletResponse response){
		try{
			rssSubscribeMapperService.delete(rssSubscribe);
			writeResult(response, createJosnObject());
		}catch(Exception e){
			throw new ControllerException(e);
		}
	}
	
	@RequestMapping("myRssView")
	public String myRssView(RssType rssType,Model model,HttpSession session){
		try{
			if(rssType.getRssTypeId() == null){
				User user = returnUser(session);
				rssType.setUserId(user.getUserId());
			}
			rssType.setStartPage(rssType.getStartPage() * Constant.RSSVIEWPAGE);
			rssType.setPage(Constant.RSSVIEWPAGE);
			
			List<Rss> rssList = rssMapperService.returnNewRss(rssType);
			model.addAttribute("rssList",rssList);
			return "rss/rssView";
		}catch(Exception e){
			throw new ControllerException(e);
		}
	}
	
	@RequestMapping("myLoadMoreRss")
	public void myLoadMoreRss(Rss rss,HttpServletResponse response){
		try{
			RssCrawl rssCrawl = new RssCrawl();
			rssCrawl.setPage(3);
			rssCrawl.setRssId(rss.getRssId());
			rssCrawl.setStartPage(3 * rss.getPage());
			List<RssCrawl> rssCrawlList = rssCrawlMapperService.selectList(rssCrawl);
			JSONObject jsonObject = createJosnObject();
			jsonObject.put("rssCrawlList", rssCrawlList);
			writeResult(response, jsonObject);
		}catch(Exception e){
			throw new ControllerException(e);
		}
	}
	
	@RequestMapping("myBlogView")
	public String myBlogView(){
		return "blog/subject";
	}
	
}
