package com.example.spring.ehcache.service.impl;

import com.example.spring.ehcache.service.EhCacheTestService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author sunboyu
 * @date 2018/1/25
 */
@Service
public class EhCacheTestServiceImpl implements EhCacheTestService {

    @Cacheable(value = "cacheTest", key = "#param")
    @Override
    public String getTimestamp(String param) {
        Long timestamp = System.currentTimeMillis();
        return timestamp.toString();
    }
}
