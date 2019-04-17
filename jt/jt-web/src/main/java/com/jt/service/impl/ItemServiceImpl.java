package com.jt.service.impl;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;
import com.jt.util.HttpClientService;
import com.jt.util.ObjectMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private HttpClientService httpClient;
    /**
     * 该操作是前台service请求jt-manage中的数据
     * 需要使用httpClient技术
     */
    @Override
    public Item findItemById(Long itemId) {
        String url = "http://manage.jt.com/web/item/findItemById/"+itemId;
        String resultJSON = httpClient.doGet(url);
        //将json转化为对象
        return ObjectMapperUtil.toObject(resultJSON,Item.class);
    }

    @Override
    public ItemDesc findItemDescById(Long itemId) {

        String url = "http://manage.jt.com/web/item/findItemDescById/"+itemId;
        String resultJSON = httpClient.doGet(url);
        //将json转化为对象
        return ObjectMapperUtil.toObject(resultJSON,ItemDesc.class);
    }

}
