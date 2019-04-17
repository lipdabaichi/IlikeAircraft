package com.jt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class IndexController {
    /**
     * 思路:能够接收用户请求参数,实现页面的动态跳转
     *
     * RESTFUL
     * 1.参数必须使用"/" 分割,如果有多个参数则写多个/
     * 2.参数必须使用{} 包裹,并且命名名称
     * 3.必须添加注解@PathVariable
     * 要求:传参时名称必须统一
     * 例子:localhost:8091/addUser?id=1&name=tomcat
     *      localhost:8091/addUser/1/tomcat
     * 优势:节省了url编辑的次数
     * @return
     */
    @RequestMapping("/page/{moduleName}")
    public String module(@PathVariable String moduleName) {
        return moduleName;
    }
}
