package com.action.main;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.entity.Menu;
import com.service.MenuMapperService;
import com.util.DataHandle;

@Controller
@RequestMapping("main")
public class MainAction {

	@Autowired
	MenuMapperService menuMapperService;
	
	/**
	 * 首页
	 * @param model
	 * @return
	 */
	@RequestMapping("index")
	public String doIndex(Model model){
		Menu menu = new Menu();
		/** 获得根菜单 */
		menu.setCondition(" parentId = 0 ");
		List<Menu> rootMenuList = menuMapperService.selectList(menu);
		model.addAttribute("rootMenuList", rootMenuList);
		return "main/index";
	}
	
	/**
	 * 获取子菜单
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("returnChildMenu")
	public void doReturnChildMenu(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Menu menu = new Menu();
		menu.setCondition(" parentId != 0 ");
		List<Menu> menuList = menuMapperService.selectList(menu);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("list", menuList);
		response.getWriter().write(jsonObject.toString());
	}
	
	@RequestMapping("returnDataTables")
	public void doReturnDataTables(HttpServletResponse response,HttpServletRequest request) throws IOException{
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
        if (!DataHandle.isEmpty(iSortCol)) {
        	/** 获取当前排序列 */
            sOrderCol = cols[Integer.valueOf(iSortCol)];
        }
        
        String orderType = "";
        String orderCol = "";
        /** 设置排序参数 */
        if (!DataHandle.isEmpty(sOrderCol)) {
            orderCol = " order by " + sOrderCol;
            if (!DataHandle.isEmpty(sOrderType)) {
                orderType = sOrderType;
            }
            orderCol += " " + orderType;
        }
        
		Menu menu = new Menu();
		menu.setStartPage(sPage);
		menu.setPage(sRows);
		menu.setCondition(" 1 = 1 " + orderCol);
		List<Menu> menuList = menuMapperService.selectList(menu);
		int count = menuMapperService.count(menu);
		JSONObject json = new JSONObject();
		json = new JSONObject();
		json.put("iTotalRecords", 10);  //本次查询记录数
		json.put("iTotalDisplayRecords", count); //记录总数
		json.put("aaData", menuList); 
		response.getWriter().write(json.toString());
	}
	
}
