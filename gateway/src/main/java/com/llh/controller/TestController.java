package com.llh.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {
	
	@RequestMapping(value="/test.do")
	public void test(HttpServletRequest request,HttpServletResponse response) throws Exception {
		//request.getrequestdispatcher("/web-inf/a.htm").forward(request,response);
		request.setAttribute("name", "llh");
		request.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(request,response);
		//request.getRequestDispatcher("/WEB-INF/views/index.html").forward(request,response);
		//return "redirect:/WEB-INF/views/index.jsp";
	}
	
	@RequestMapping(value="/test1.do")
	public String test1(HttpServletRequest request,HttpServletResponse response) throws Exception {
		return "redirect:/test.do";
	}
}
