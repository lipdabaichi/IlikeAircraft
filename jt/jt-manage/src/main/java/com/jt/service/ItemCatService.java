package com.jt.service;

import com.jt.vo.EasyUITree;

import java.util.List;

public interface ItemCatService {


    String findItemCatNameById(Long itemCatId);

    List<EasyUITree> findItemCatList(Long parentId);
    //添加redis缓存的处理
    List<EasyUITree> findItemCatCacheList(Long parentId);
}
