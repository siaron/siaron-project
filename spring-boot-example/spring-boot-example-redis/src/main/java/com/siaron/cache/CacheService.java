package com.siaron.cache;

import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisKeyValueTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xielongwang
 * @create 2018-11-2810:28 PM
 * @email xielong.wang@nvr-china.com
 * @description
 */
@Component
public class CacheService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    public void test() {
        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("deviceNum", "C20222");
        map1.put("versionNum", "1111");
        map1.put("functionCode", "A002");

        List<Map<String, Object>> lm = new ArrayList<Map<String, Object>>();
        lm.add(map1);

//        lm.forEach(s -> {
//            redisTemplate.opsForHash().putAll("C20222", s);
//        });

        lm.forEach(s -> {
            redissonClient.getMap("C20222").putAll(s);
        });

        System.out.println("value --> :" + redisTemplate.opsForValue().get("1"));
        System.out.println("ok");
    }
}