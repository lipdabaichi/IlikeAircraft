package com.jt;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.List;
import java.util.Map;
import java.util.Set;

/*@SpringBootTest
@RunWith(SpringRunner.class)*/
public class test {
    @Test
    public void testRedis() throws InterruptedException {
        Jedis jedis = new Jedis("192.168.211.130", 6379);
        /*jedis.set("瑞文", "弟弟");
        System.out.println(jedis.get("瑞文"));
        jedis.del("aa");
        //设置超时时间
        jedis.expire("瑞文", 40);
        Thread.sleep(2000);
        System.out.println("key还能存活几秒"+jedis.ttl("瑞文"));*/

        //2.hash相关
       /* jedis.hset("user", "username", "瑞文");
        boolean flag = jedis.hexists("user", "username");
        System.out.println(flag);
        Map<String, String> map = jedis.hgetAll("user");
        System.out.println(map);
        List<String> list =
                jedis.hvals("user");
        System.out.println(list);
        Set<String> set =
                jedis.hkeys("user");
        System.out.println(set);*/

       //当做队列使用
      /*  jedis.lpush("list", "1,2,3,4,5");//"1,2,3,4,5"
        String list = jedis.rpop("list");
        System.out.println(list);*/

      //开启事务
        Transaction transaction =
                jedis.multi();
        try {
            transaction.set("aaa", "8999");
            transaction.set("aa1", "8991");
            transaction.exec();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.discard();
        }


    }
}
