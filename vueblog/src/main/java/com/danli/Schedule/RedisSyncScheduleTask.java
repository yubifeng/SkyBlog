package com.danli.Schedule;

import com.danli.config.RedisKeyConfig;
import com.danli.entity.Blog;
import com.danli.service.BlogService;
import com.danli.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

/**
 * 定时任务
 * @author: fanfanli
 * @date: 2021/8/11
 */
@Component
@EnableScheduling
@EnableAsync
public class RedisSyncScheduleTask {

    @Autowired
    RedisService redisService;
    @Autowired
    BlogService blogService;

    Logger logger = LoggerFactory.getLogger(RedisSyncScheduleTask.class);

    /**
     * 从Redis同步博客文章浏览量到数据库
     */
    @Async
    @Scheduled(fixedDelay = 24*60*60*1000)  //间隔24小时秒
    public void syncBlogViewsToDatabase() {
        logger.info("执行定时任务");
        String redisKey = RedisKeyConfig.BLOG_VIEWS_MAP;
        Map blogViewsMap = redisService.getMapByHash(redisKey);
        Set<Integer> keys = blogViewsMap.keySet();
        for (Integer key : keys) {
            Integer views = (Integer) blogViewsMap.get(key);
            Blog blog = blogService.getById(key);
            blog.setViews(blog.getViews()+views);
            blogService.saveOrUpdate(blog);
        }
        deleteAllCache();
        logger.info("完成任务");
    }

    /**
     * 清除所有缓存
     */
//    @Async
//    @Scheduled(fixedDelay = 60*1000)
    public void deleteAllCache() {
        redisService.deleteAllKeys();
    }


}
