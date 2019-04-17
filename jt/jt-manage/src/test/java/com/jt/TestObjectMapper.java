package com.jt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.pojo.ItemCat;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestObjectMapper {
    //1.将java对象转化为json串
    @Test
    public void test2Json() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        ItemCat itemCat = new ItemCat();
        itemCat.setId(100L)
                .setName("测试")
                .setParentId(200L);
        //将对象转化为json串,必须调用对象的get/set方法
        String json = mapper.writeValueAsString(itemCat);
        System.out.println(json);

        //将json转化为对象
        ItemCat jsonItemCat1 =
                mapper.readValue(json, ItemCat.class);
        System.out.println(jsonItemCat1.getName());
    }

    //转化list集合数据
    @Test
    public void testList() throws IOException {
        List<ItemCat> catList = new ArrayList<ItemCat>();
        for(int i=0;i<5;i++) {
            ItemCat itemCat = new ItemCat();
            itemCat.setId(1000L+i)
                    .setName("测试"+i)
                    .setParentId(2000L+i);
            catList.add(itemCat);
        }
//        System.out.println("11"+catList);
        ObjectMapper objectMapper = new ObjectMapper();
        String json =
                objectMapper.writeValueAsString(catList);
        System.out.println(json);
        System.out.println(">>>>>>>>>>>>.");
        //将json转化为对象List
        List<ItemCat> jsonCatList =
                objectMapper.readValue(json,catList.getClass());
        System.out.println(jsonCatList);
    }

}
