package com.action.intercept;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.exception.common.TipException;

public class MyExceptionHandler implements HandlerExceptionResolver {  
    
	private static Logger log = Logger.getLogger(MyExceptionHandler.class);
	
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,  
            Exception ex) {
        try{
        	response.setContentType("application/json;charset=UTF-8");
        	String errMsg = "系统异常";
        	if(ex instanceof TipException){
        		String msg = ex.getMessage();
        		errMsg = msg.substring(msg.lastIndexOf(":") + 1);
        	}
            if(isAjax(request)) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("result", "error");
                jsonObject.put("message", errMsg);
                
                PrintWriter writer = response.getWriter();
                writer.write(jsonObject.toString());  
                writer.flush();
                return null;
            }else{
                Map<String,Object> map = new HashMap<String,Object>();
                map.put("message", errMsg);
                ex.printStackTrace();
                return new ModelAndView("error", map);
            }
        }catch(Exception e){
        	log.error(e);
        }
        return null;
    }
    
    private boolean isAjax(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }
    
}  