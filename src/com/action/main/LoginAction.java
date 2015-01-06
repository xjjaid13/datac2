package com.action.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.action.BaseAction;
import com.alibaba.fastjson.JSONObject;
import com.exception.common.ControllerException;
import com.po.user.User;
import com.service.user.UserMapperService;
import com.util.Constant;

/**
 * 登陆action
 * taylor 2014-7-23下午11:32:37
 */
@Controller
@RequestMapping("login")
public class LoginAction extends BaseAction{

	@Autowired
	UserMapperService userMapperService;
	
	@RequestMapping
	public String login(HttpServletRequest request){
		return "login";
	}
	
	@RequestMapping("validLogin")
	public void validLogin(User user,HttpServletResponse response,HttpSession session){
		try{
			user = userMapperService.validUser(user);
			boolean result = false;
			if(user != null){
				result = true;
				session.setAttribute(Constant.USER, user);
			}
			JSONObject jsonObject = createJosnObject();
			if(!result){
				errorJsonObject(jsonObject, "账号或密码错误");
			}
			writeResult(response, jsonObject);
		}catch(Exception e){
			throw new ControllerException(e);
		}
	}
	
	@RequestMapping("out")
	public String out(HttpSession session){
		session.removeAttribute(Constant.USER);
		return "login";
	}
	
}
