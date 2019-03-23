package com.siaron.example;

import com.google.common.collect.Lists;
import com.siaron.controller.RedisCacheType;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author xielongwang
 * @create 2019-03-128:20 PM
 * @email xielong.wang@nvr-china.com
 * @description
 */
@Component
public class HscanDemo {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    private static final int MAX_SEND = 300;


    protected List<List<String>> getSyncRedisSplitKeys() {
        Set<String> hotKeys = redisTemplate.execute((RedisCallback<Set<String>>) connection -> {
            Set<String> binaryKeys = new HashSet<>();
            Cursor<byte[]> cursor = connection.scan(new ScanOptions.ScanOptionsBuilder().match(RedisCacheType.DEVICE_PREFIX + "*").count(1000).build());
            while (cursor.hasNext()) {
                binaryKeys.add(new String(cursor.next()));
            }
            return binaryKeys;
        });

        if (CollectionUtils.isEmpty(hotKeys)) {
            return Collections.emptyList();
        }

        //分批处理
        return Lists.partition(new ArrayList<>(hotKeys), MAX_SEND);
    }

    public void hscanDemo() {
        List<List<String>> lists = getSyncRedisSplitKeys();

        lists.parallelStream().forEach(list -> {
            list.forEach(key -> {
                Object va = redisTemplate.opsForHash().get(key, "warningCode");
                if (va != null) {
                    System.out.println(key);
                }
            });
        });

    }

    public void scan() {
        Cursor<Map.Entry<Object, Object>> maps = redisTemplate.opsForHash().scan("device:hot:1120000A", ScanOptions.scanOptions().count(1).match("warningCode").build());
        while (maps.hasNext()) {
            Map.Entry<Object, Object> va = maps.next();
            System.out.println("key: " + va.getKey() + "  va :" + va.getValue());
        }
    }

}