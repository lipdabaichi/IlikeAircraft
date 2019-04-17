package com.jt.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.ItemCatMapper;
import com.jt.pojo.ItemCat;
import com.jt.util.ObjectMapperUtil;
import com.jt.vo.EasyUITree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemCatServiceImpl implements ItemCatService {
    @Autowired
    private ItemCatMapper itemCatMapper;

    @Autowired(required = false)
    private ShardedJedis jedis;

    @Override
    public String findItemCatNameById(Long itemCatId) {

        ItemCat itemCat = itemCatMapper.selectById(itemCatId);/*selectById(itemCatId);*/
        return itemCat.getName();
    }

    @Override
    public List<EasyUITree> findItemCatList(Long parentId) {
        QueryWrapper<ItemCat> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", parentId);
        List<ItemCat> itemCatList = itemCatMapper.selectList(queryWrapper);
        //需要返回VOlist集合信息,则遍历itemCatList
        List<EasyUITree> treeList = new ArrayList<>();
        for (ItemCat itemCat:itemCatList
             ) {
            EasyUITree uiTree = new EasyUITree();
            uiTree.setId(itemCat.getId())
                    .setText(itemCat.getName());

            String status=itemCat.getIsParent()?"closed":"open";
            uiTree.setState(status);
            treeList.add(uiTree);
        }
        return treeList;
    }


    /**
     *
     * @param parentId
     * @return
     */
    @Override
    public List<EasyUITree> findItemCatCacheList(Long parentId) {
        List<EasyUITree> treelist = new ArrayList<>();
        //key的定义 类名_变量
        String key =  "ITEM_CAT_"+parentId;
        String result = jedis.get(key);
        if (StringUtils.isEmpty(result)) {
            //缓存中没有数据,查询真实数据库信息
            treelist = findItemCatList(parentId);
            //将数据保存到缓存中
            String json = ObjectMapperUtil.toJSON(treelist);
            jedis.set(key, json);
            System.out.println("查询数据库!!!!!!");
        }else {
            treelist = ObjectMapperUtil.toObject(result,treelist.getClass());
            System.out.println("查询redis缓存!!!!!");
        }
        return treelist;
    }


}
