package com.jt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.jt.service.ItemService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ItemController {
	
	@Autowired
	private ItemService itemService;

//	@RequestMapping("user")   //test
////	@ResponseBody
//	public String doUser(){
//		return "index";
//	}
	
	
	
	
	
	
	
}
