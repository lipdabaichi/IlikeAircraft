package com.jt.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @RequestMapping("/test/hello/addUser")
    public String addUser(){
        return "新增数据成功";
    }
    @RequestMapping("/test/hello/findUser")
    public  String findUser(){
        return "查询数据!!";
    }
}
