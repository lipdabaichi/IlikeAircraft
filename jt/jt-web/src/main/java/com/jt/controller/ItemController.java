package com.jt.controller;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    //跳转页面  ${itemDesc.itemDesc }
    @RequestMapping("/{itemId}")
    public String findItemById(@PathVariable Long itemId, Model model) {
        //获取商品信息
        //System.out.println("?????????????????/"+itemId);
        Item item = itemService.findItemById(itemId);
        //获取商品详情信息
        ItemDesc itemDesc = itemService.findItemDescById(itemId);
        model.addAttribute("item", item);
        model.addAttribute("itemDesc", itemDesc);
        return "item"; //跳转到商品展现页面
    }
}
