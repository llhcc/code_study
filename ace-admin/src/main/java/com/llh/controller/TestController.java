package com.llh.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {
	private static final Logger log = LogManager.getLogger(TestController.class);
	
	@RequestMapping(value="index.htm")
	public String index(HttpServletRequest request) throws Exception {
		request.setAttribute("active", "1");
		return "index";
	}
	
	@RequestMapping(value="typography.htm")
	public String typography(HttpServletRequest request) throws Exception{
		request.setAttribute("active", "2");
		return "typography";
	}
	
	@RequestMapping(value="elements.htm")
	public String elements(HttpServletRequest request) throws Exception {
		request.setAttribute("active", "3_1");
		return "elements";
	}
	
	@RequestMapping(value="buttons.htm")
	public String buttons(HttpServletRequest request) throws  Exception {
		request.setAttribute("active", "3_2");
		return "buttons";
	}
	
	@RequestMapping(value="treeview.htm")
	public String treeview(HttpServletRequest request) throws  Exception {
		request.setAttribute("active", "3_3");
		return "treeview";
	}
	
	@RequestMapping(value="jqueryUi.htm")
	public String jqueryUi(HttpServletRequest request) throws  Exception {
		request.setAttribute("active", "3_4");
		return "jqueryUi";
	}
	
	@RequestMapping(value="tables.htm")
	public String tables(HttpServletRequest request) throws  Exception {
		request.setAttribute("active", "4_1");
		return "tables";
	}
}
