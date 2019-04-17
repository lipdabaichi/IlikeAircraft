package com.jt.controller;

import com.jt.anno.CaCheAnnotation;
import com.jt.service.ItemCatService;
import com.jt.service.ItemService;
import com.jt.vo.EasyUIList;
import com.jt.vo.EasyUITree;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("item/cat")
public class ItemCatController {
	
	@Autowired
	private ItemCatService itemCatService;

	/**
	 * 关于框架编码的说明
	 * 使用旧的ssm框架时3-4版本:
	 * 1.如果返回数为String.则将数据
	 * 通过@ResponseBody
	 *
	 *
	 *,produces = "text/html;charset=utf-8"
	 */
	//实现商品分类信息查询
	@RequestMapping(value = "/queryItemName")
	public String findItemCatNameById(Long itemCatId) {
		return itemCatService.findItemCatNameById(itemCatId);
	}

	/**
	 * @RequestParam     666666
	 * defaultValue:如果没有传递参数,则设定默认值
	 * name:参数名称
	 * required:true/false   是否必须传递的参数
	 * value:表示参数的名称
	 *
	 * @param parentId
	 * @return
	 */
	@RequestMapping("/list")
	@CaCheAnnotation(index=0,cacheType= CaCheAnnotation.CACHE_TYPE.FIND)
	public List<EasyUITree> findItemCatList
	(@RequestParam(value="id",defaultValue = "0") Long parentId) {
		//1.获取1及商品分类信息
		return itemCatService.findItemCatList(parentId);
	}

}
