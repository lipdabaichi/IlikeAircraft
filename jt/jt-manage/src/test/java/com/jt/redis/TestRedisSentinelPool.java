package com.jt.redis;


import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Set;

public class TestRedisSentinelPool {
    @Test
    public  void test01()
    {
        String msg = new HostAndPort("192.168.211.130", 26379).toString();
        System.out.println("工具api的结果"+msg);
        Set<String> sentinels = new HashSet<>();
        sentinels.add("192.168.211.130:26379");
        JedisSentinelPool jedisSentinelPool = new JedisSentinelPool("mymaster", sentinels);
        //只能操作主机
        Jedis jedis = jedisSentinelPool.getResource();
        jedis.set("1812", "哨兵测试完成!!!");
        System.out.println("获取结果:"
                + jedis.get("1812"));
        jedis.close();
    }
}
