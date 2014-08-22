package com.util;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class InitServlet extends HttpServlet implements Servlet{

	private static final long serialVersionUID = 1L;
	
	private static Logger log = Logger.getLogger(Init.class);
	
	public static Set<Integer> changeList = new HashSet<Integer>();

	public void init(ServletConfig servletConfig){
		String realPath = servletConfig.getServletContext().getRealPath("")+"/";
		Constant.REALPATH = realPath;
		/** 初始化配置文件 */
		new Init().init(realPath);
		
	}
	
}
