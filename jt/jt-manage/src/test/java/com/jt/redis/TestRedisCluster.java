package com.jt.redis;

import org.junit.Test;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

public class TestRedisCluster {
    @Test
    public void test01(){
        Set<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort("192.168.211.130", 7000));
        nodes.add(new HostAndPort("192.168.211.130", 7001));
        nodes.add(new HostAndPort("192.168.211.130", 7002));
        nodes.add(new HostAndPort("192.168.211.130", 7003));
        nodes.add(new HostAndPort("192.168.211.130", 7004));
        nodes.add(new HostAndPort("192.168.211.130", 7005));
        nodes.add(new HostAndPort("192.168.211.130", 7006));
        nodes.add(new HostAndPort("192.168.211.130", 7007));
        nodes.add(new HostAndPort("192.168.211.130", 7008));
        JedisCluster jedisCluster =
                new JedisCluster(nodes);
        jedisCluster.set("1812", "恭喜你搭建cluster集群成功");
        System.out.println(jedisCluster.get("1812"));

    }


}
