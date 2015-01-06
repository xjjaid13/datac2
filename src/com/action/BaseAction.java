package com.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.po.user.User;
import com.util.Constant;
import com.util.DataHandle;
import com.vo.PageInfoVO;

public class BaseAction {

	public User returnUser(HttpSession session){
		return (User) session.getAttribute(Constant.USER);
	}
	
	public JSONObject createJosnObject(){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", "success");
		return jsonObject;
	}
	
	public JSONObject errorJsonObject(JSONObject jsonObject,String message){
		jsonObject.put("result", "error");
		jsonObject.put("message", message);
		return jsonObject;
	}
	
	public void writeResult(HttpServletResponse response,JSONObject jsonObject) throws IOException{
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(jsonObject.toJSONString());
	}
	
	public void writeResult(HttpServletResponse response,net.sf.json.JSONObject jsonObject) throws IOException{
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(jsonObject.toString());
	}
	
	public PageInfoVO returnPageInfo(HttpServletRequest request){
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
        PageInfoVO pageInfoVO = new PageInfoVO();
        pageInfoVO.setsOrderType(sOrderType);
        pageInfoVO.setStartPage(sPage);
        pageInfoVO.setPage(sRows);
        pageInfoVO.setOrderCol(orderCol);
        return pageInfoVO;
	}
	
}
