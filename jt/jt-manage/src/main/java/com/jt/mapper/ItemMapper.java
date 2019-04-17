package com.jt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jt.pojo.Item;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ItemMapper extends BaseMapper<Item>{
    /***
     * 关于mybatis 取值传参问题
     * 规定:不允许多值传参.只能将多值转化为单值
         * 1.把参数封装为POJO
         * 2.封装为Map集合
         * 3.封装为array/list
     * @param start
     * @param rows
     * @return
     */
    List<Item> findItemListByPage(@Param("start") Integer start, @Param("rows") Integer rows);
}
