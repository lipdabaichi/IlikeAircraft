package com.jt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

@Service  //交给spring管理
public class RedisService {
    @Autowired(required = false)
    private JedisSentinelPool pool;
    //添加set/get方法
    public void set(String key, String value) {
        Jedis jedis = pool.getResource();
        jedis.set(key, value);
        jedis.close();
    }

    public String get(String key) {
        Jedis jedis = pool.getResource();
        String result = jedis.get(key);
        jedis.close();
        return result;
    }

    public void del(String key) {
        Jedis jedis = pool.getResource();
        jedis.del(key);
    }

}
