package com.example.spring.ehcache.controller;

import com.example.spring.ehcache.service.EhCacheTestService;
import lombok.extern.slf4j.Slf4j;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author sunboyu
 * @date 2018/1/25
 */
@RestController
@RequestMapping("/test/cache")
@Slf4j
public class TestCacheController {

    @Autowired
    private EhCacheTestService ehCacheTestService;

    @RequestMapping("/annotation/ehcache/get")
    public String annotationEhcacheGet() throws InterruptedException {
        log.info("第一次调用：{}" + ehCacheTestService.getTimestamp("param"));
        Thread.sleep(2000);
        log.info("2秒之后调用：{}" + ehCacheTestService.getTimestamp("param"));
        Thread.sleep(11000);
        log.info("再过11秒之后调用：{}" + ehCacheTestService.getTimestamp("param"));
        return "success";
    }

    @RequestMapping("/api/ehcache/get")
    public String apiEhcacheGet() {

        // EhCache的缓存，是通过CacheManager来进行管理的
        CacheManager cacheManager = CacheManager.getInstance();
        // 缓存的配置，也可以通过xml文件进行
        CacheConfiguration conf = new CacheConfiguration();
        // 设置名字
        conf.setName("ehcache" + System.currentTimeMillis());
        // 最大的缓存数量
        conf.setMaxEntriesLocalHeap(1 << 10);
        // 设置失效策略
        conf.memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.LRU);

        // 创建一个缓存对象，并把设置的信息传入进去
        final Cache cache = new Cache(conf);
        // 将缓存对象添加到管理器中
        cacheManager.addCache(cache);

        for (int i = 0; i < 1 << 11; i++)
            cache.put(new Element("ehcacheKey" + i, new Date()));

        log.info("cache size: {}", cache.getSize());
        log.info("cache name: {}", cache.getName());

        cache.getKeys().forEach(key -> log.info("cache name: {}", cache.get(key)));

        return "ehcache api get";
    }

    @RequestMapping("/api/guava/get")
    public String apiGuavaGet() {


        return "guava api get";
    }
}
