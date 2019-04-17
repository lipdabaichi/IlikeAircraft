package com.jt.controller;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.pojo.SysResult;
import com.jt.vo.EasyUIList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.jt.service.ItemService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController        //前提:保证返回的数据都是JSON
@RequestMapping("item")
public class ItemController {
	
	@Autowired
	private ItemService itemService;

	//实现商品分页查询
	@RequestMapping("/query")
	public EasyUIList findItemByPage(Integer page, Integer rows) {
		return itemService.findItemByPage(page, rows);
	}

	//实现商品信息新增
	//ItemDesc 标识商品详情信息
	@RequestMapping("/save")
	public SysResult saveItem(Item item, ItemDesc itemDesc) {
		try {
			itemService.saveItem(item,itemDesc);
			return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
	}
	//实现商品下架  1,2,3,4,5串
	@RequestMapping("/instock")
	public SysResult instock(Long[] ids) {
		try {
			int status = 2;
			itemService.updateStatus(ids,status);
			return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
	}

	//上架操作
	@RequestMapping("/reshelf")
	public SysResult reshelf(Long[] ids) {
		try {
			int status = 1;
			itemService.updateStatus(ids,status);
			return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
	}
	//实现商品修改操作
	@RequestMapping("/update")
	public SysResult updateItem(Item item,ItemDesc itemDesc) {
		try {
			itemService.updateItem(item, itemDesc);
			return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
	}
	//全局异常处理机制,作业自己完成
	@RequestMapping("/delete")
	public SysResult deleteItems(Long[] ids) {
		try {
			itemService.deleteItems(ids);
			return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
	}

	//根据itemid查询商品详情信息
	@RequestMapping("/query/item/desc/{itemId}")
	public SysResult findItemDescById(@PathVariable Long itemId) {
		try {
			ItemDesc itemDesc = itemService.findItemDescById(itemId);
			return SysResult.ok(itemDesc);
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
	}


}
