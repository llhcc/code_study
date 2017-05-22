package com.llh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.llh.entity.Person;

@Controller
public class PersonController {
	
	/**
	 * @RequestMapping(value = "/person/profile/{id}/{name}/{status}", method = RequestMethod.GET)中的{id}/{name}/{status}与@PathVariable int id, @PathVariable String name,@PathVariable boolean status一一对应，按名匹配
	 * 这是restful式风格。 
	 * 如果映射名称有所不一，可以参考如下方式： 
	 * @RequestMapping(value = "/person/profile/{id}", method = RequestMethod.GET)  
		public @ResponseBody  
		Person porfile(@PathVariable("id") int uid) {  
		    return new Person(uid, name, status);  
		}  
	 * @param id
	 * @param name
	 * @param status
	 * @return
	 */
	@RequestMapping(value="/person/profile/{id}/{name}/{status}", method=RequestMethod.GET)
	@ResponseBody
	public Person profile(@PathVariable int id,@PathVariable String name,@PathVariable boolean status){
		return new Person(id,name,status);
	}
	
	//@RequestMapping(value="/person/login", method=RequestMethod.POST, consumes="application/json")
	@RequestMapping(value="/person/login", method=RequestMethod.POST)
	@ResponseBody
	public Person login(@RequestBody Person person){
		return person;
	}
	
	@RequestMapping(value="/getstr", method=RequestMethod.GET)
	@ResponseBody
	public String getStr(String str) {
		return str;
	}
}
