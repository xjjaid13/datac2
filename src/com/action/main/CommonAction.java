package com.action.main;

import org.springframework.web.bind.annotation.RequestMapping;

import com.action.BaseAction;

@RequestMapping("common")
public class CommonAction extends BaseAction{

	@RequestMapping("top")
	public String top(){
		return "top";
	}
	
}
