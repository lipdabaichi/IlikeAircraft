package com.jt.service;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.EasyUIList;

public interface ItemService {

    EasyUIList findItemByPage(Integer page, Integer rows);

    void saveItem(Item item, ItemDesc itemDesc);

    void updateStatus(Long[] ids, int status);

    void updateItem(Item item, ItemDesc itemDesc);

    void deleteItems(Long[] ids);

    ItemDesc findItemDescById(Long itemId);
}
