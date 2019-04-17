package com.jt.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import redis.clients.jedis.*;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//为配置类 代理之前的配置文件 web.xml 和 spring配置文件
//@Configuration  //标识配置类
//@ImportResource( {"classpath:/spring/application-redis.xml"}) //

@Configuration
@PropertySource("classpath:/properties/redis.properties")
public class RedisConfig {
//    @Value( "${redis.host}")
//    private String host;
//    @Value("${redis.port}")
//    private Integer port;

//    @Bean
//    public Jedis jedis(){
//        return new Jedis(host, port);
//    }

    //2.分片
//    @Value("${redis.shards}")
//    private String redisShards;
//    @Bean
//    public ShardedJedis shardedJedis(){
//        List<JedisShardInfo> shards = new ArrayList<>();
//
//        String []hostAndPosts= redisShards.split(",");
//        //node:IP:端口号
//        for (String node:hostAndPosts
//             ) {
//            String[] host_port = node.split(":");
//            String host = host_port[0];
//            Integer port = Integer.parseInt(host_port[1]);
//            shards.add(new JedisShardInfo(host, port));
//        }
//        return new ShardedJedis(shards);
//    }

    //3.哨兵模式
//    @Value("${redis.nodes}")
//    private String nodes;
//    @Value("${redis.masterName}")
//    private String masterName;
//    @Bean
//    public JedisSentinelPool jedisSentinelPool (){
//        Set<String> sentinels = new HashSet<>();
//        sentinels.add(nodes);
//        JedisSentinelPool jedisSentinelPool = new JedisSentinelPool(masterName, sentinels);
//        return jedisSentinelPool;
//    }


    //实现redis集群引入
    @Value("${redis.nodes}")
    private String nodes; //ip:port,iP:port....

    @Bean
    public JedisCluster jedisCluster() {
        Set<HostAndPort> nodesSet = new HashSet<>();
        String[] node = nodes.split(",");
        for (String h_pNode : node) {
            //ip:port
            String[] args = h_pNode.split(":");
            int port = Integer.parseInt(args[1]);
            HostAndPort hostAndPort
                    = new HostAndPort(args[0], port);
            nodesSet.add(hostAndPort);
        }
        return new JedisCluster(nodesSet);
    }




}
