package com.jt.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.pojo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.service.UserService;
import redis.clients.jedis.JedisCluster;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private JedisCluster jedisCluster;

	@RequestMapping("/check/{param}/{type}")
	public JSONPObject findCheckUser(@PathVariable String param, @PathVariable Integer type, String callback) {
		//true 表示用户已经存在  false 用户可以使用
		Boolean flag = userService.findCheckUser(param, type);
		return new JSONPObject(callback, SysResult.ok(flag));
	}
//单点登陆中:根据token数据获取用户信息
	@RequestMapping("/query/{token}")
	public JSONPObject findUserByToken(@PathVariable String token,
									   String callback) {
		String userJSON = jedisCluster.get(token);
		return new JSONPObject(callback, SysResult.ok(userJSON));
	}

	@RequestMapping("/test1")
	public String findCheckUser1() {
		return "111111111";
	}
	
	
	
	
}
