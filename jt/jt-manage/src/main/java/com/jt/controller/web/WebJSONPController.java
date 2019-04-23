package com.jt.controller.web;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.pojo.Item;
import com.jt.pojo.ItemCat;
import com.jt.util.ObjectMapperUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebJSONPController {
    //跨域数据封装格式:callback(json)
    //获取数据之后   转化为json串
    @RequestMapping("/web/testJSONP")
    public JSONPObject getJSONP(String callback) {
        ItemCat itemCat = new ItemCat();
        itemCat.setId(1000L);
        //function:代表回调函数
        //value:代表返回数据对象
        return  new JSONPObject(callback,itemCat);
    }


}
