package com.action.rss;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("rss")
public class RssController {

	@RequestMapping("index")
	public String index(HttpSession session, Model model,
			HttpServletRequest request){
		return "rss/index";
	}
	
}
