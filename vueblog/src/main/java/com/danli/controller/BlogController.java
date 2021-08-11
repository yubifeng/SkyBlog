package com.danli.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.danli.annotation.VisitLogger;
import com.danli.common.lang.Result;
import com.danli.common.lang.vo.BlogInfo;
import com.danli.config.RedisKeyConfig;
import com.danli.entity.Blog;
import com.danli.service.BlogService;
import com.danli.service.RedisService;
import com.danli.util.ShiroUtil;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 博客前端控制器
 *
 * @author fanfanli
 * @date 2021-04-05
 */
@RestController
public class BlogController {
    @Autowired
    BlogService blogService;
    @Autowired
    RedisService redisService;
    Logger logger = LoggerFactory.getLogger(BlogController.class);

    /**
     * 按置顶、创建时间排序 分页查询公开的博客
     */
    @VisitLogger(behavior = "访问页面",content = "首页")
    @GetMapping("/blogs")
    public Result getBlogsByPage(@RequestParam(defaultValue = "1") Integer currentPage) {
        //有缓存直接返回
        if(redisService.hasHashKey(RedisKeyConfig.BLOG_INFO_CACHE,currentPage)){
           return   Result.succ(redisService.getValueByHashKey(RedisKeyConfig.BLOG_INFO_CACHE,currentPage));
        }
        Page page = new Page(currentPage, 5);
        IPage pageData = blogService.page(page, new QueryWrapper<Blog>().eq("status", 1).orderByDesc("create_time"));
        redisService.saveKVToHash(RedisKeyConfig.BLOG_INFO_CACHE, currentPage,pageData);
        return Result.succ(pageData);
    }

    /**
     * 按创建时间排序 分类 分页查询公开的博客简要信息列表
     */
    @VisitLogger(behavior = "查看分类")
    @GetMapping("/blogsByType")
    public Result blogsByType(@RequestParam(defaultValue = "1") Integer currentPage, @RequestParam String typeName) {
        if (redisService.hasHashKey(RedisKeyConfig.CATEGORY_BLOG_CACHE, typeName+currentPage)) {
            return Result.succ(redisService.getValueByHashKey(RedisKeyConfig.CATEGORY_BLOG_CACHE, typeName+currentPage));
        }

        List<BlogInfo> list = blogService.getBlogInfoListByCategoryName(typeName);
        int pageSize = 10;
        Page page = new Page();
        int size = list.size();
        if (pageSize > size) {
            pageSize = size;
        }
        // 求出最大页数，防止currentPage越界
        int maxPage = size % pageSize == 0 ? size / pageSize : size / pageSize + 1;
        if (currentPage > maxPage) {
            currentPage = maxPage;
        }
        // 当前页第一条数据的下标
        int curIdx = currentPage > 1 ? (currentPage - 1) * pageSize : 0;
        List pageList = new ArrayList();
        // 将当前页的数据放进pageList
        for (int i = 0; i < pageSize && curIdx + i < size; i++) {
            pageList.add(list.get(curIdx + i));
        }
        page.setCurrent(currentPage).setSize(pageSize).setTotal(list.size()).setRecords(pageList);

        redisService.saveKVToHash(RedisKeyConfig.CATEGORY_BLOG_CACHE, typeName+currentPage, page);
        return Result.succ(page);
    }

    /**
     * 按创建时间排序 分页查询所有博客
     */
    @RequiresPermissions("user:read")
    @GetMapping("/blogList")
    public Result blogList(@RequestParam(defaultValue = "1") Integer currentPage,@RequestParam(defaultValue = "10") Integer pageSize) {

        Page page = new Page(currentPage, pageSize);
        IPage pageData = blogService.page(page, new QueryWrapper<Blog>().orderByDesc("create_time"));

        return Result.succ(pageData);
    }

    /**
     * 查询所有博客
     */
    @RequiresPermissions("user:read")
    @GetMapping("/blog/all")
    public Result blogAll() {
        List<Blog> list = blogService.lambdaQuery().list();
        return Result.succ(list);
    }
    /**
     * 查询友链的博客
     */

    @VisitLogger(behavior = "访问页面",content = "友链")
    @GetMapping("/friends")
    public Result friends() {
        if (redisService.hasHashKey(RedisKeyConfig.BLOG_INFO_CACHE,RedisKeyConfig.FRIEND_BLOG_CACHE)) {
            return Result.succ(redisService.getValueByHashKey(RedisKeyConfig.BLOG_INFO_CACHE,RedisKeyConfig.FRIEND_BLOG_CACHE));
        }
        List<Blog> list = blogService.lambdaQuery().eq(Blog::getTitle, "友情链接").list();
        redisService.saveKVToHash(RedisKeyConfig.BLOG_INFO_CACHE, RedisKeyConfig.FRIEND_BLOG_CACHE, list.get(0));
        return Result.succ(list.get(0));
    }
    /**
     * 查询关于我的博客
     */
    @VisitLogger(behavior = "访问页面",content = "关于我")
    @GetMapping("/about")
    public Result aboutMe() {
        if (redisService.hasHashKey(RedisKeyConfig.BLOG_INFO_CACHE,RedisKeyConfig.ABOUT_INFO_CACHE)) {
            return Result.succ(redisService.getValueByHashKey(RedisKeyConfig.BLOG_INFO_CACHE,RedisKeyConfig.ABOUT_INFO_CACHE));
        }
        List<Blog> list = blogService.lambdaQuery().eq(Blog::getTitle, "关于我！！").list();
        redisService.saveKVToHash(RedisKeyConfig.BLOG_INFO_CACHE,RedisKeyConfig.ABOUT_INFO_CACHE, list.get(0));
        return Result.succ(list.get(0));
    }

    /**
     * 按创建时间排序 分页查询所有博客
     */
    @VisitLogger(behavior = "访问页面",content = "归档")
    @GetMapping("/blog/archives")
    public Result getBlogsArchives(@RequestParam(defaultValue = "1") Integer currentPage) {
        if(redisService.hasHashKey(RedisKeyConfig.ARCHIVE_INFO_CACHE,currentPage)){
            return   Result.succ(redisService.getValueByHashKey(RedisKeyConfig.ARCHIVE_INFO_CACHE,currentPage));
        }
        Integer pageSize = 15;
        Page page = new Page(currentPage, pageSize);
        IPage pageData = blogService.page(page, new QueryWrapper<Blog>().eq("status", 1).orderByDesc("create_time"));
        //进行缓存
        redisService.saveKVToHash(RedisKeyConfig.ARCHIVE_INFO_CACHE, currentPage,pageData);
        return Result.succ(pageData);
    }

    /**
     * 根据内容搜索公开博客
     */
    @VisitLogger(behavior = "搜索博客")
    @GetMapping("/search")
    public Result search(@RequestParam String queryString) {
        List<Blog> list = blogService.list(new QueryWrapper<Blog>().like("content", queryString).eq("status", 1).orderByDesc("create_time"));
        return Result.succ(list);
    }

    /**
     * 查询某个公开博客详情
     */
    @VisitLogger(behavior = "查看博客")
    @GetMapping("/blog/{id}")
    public Result detail(@PathVariable(name = "id") Integer id) {


        Blog blog = blogService.getById(id);
        Assert.notNull(blog, "该博客已删除！");
        if (blog.getStatus()!=1){
            return Result.fail("你没有权限查阅此博客");
        }


        if (redisService.getMapByHash(RedisKeyConfig.BLOG_VIEWS_MAP).containsKey(id)) {
            redisService.incrementByHashKey(RedisKeyConfig.BLOG_VIEWS_MAP, id, 1);
        } else {
            redisService.saveKVToHash(RedisKeyConfig.BLOG_VIEWS_MAP, id, 1);
        }

        return Result.succ(blog);

    }


    /**
     * 查询某个博客详情
     */
    @RequiresPermissions("user:read")
    @GetMapping("/blog/detail/{id}")
    public Result getDetail(@PathVariable(name = "id") Long id) {


        Blog blog = blogService.getById(id);
        Assert.notNull(blog, "该博客已删除！");

        return Result.succ(blog);

    }
    /**
     * 删除某个博客
     */
    @RequiresRoles("role_root")
    @RequiresPermissions("user:delete")
    @RequiresAuthentication
    @GetMapping("/blog/delete/{id}")
    public Result delete(@PathVariable(name = "id") Long id) {

        if (blogService.removeById(id)) {
            redisService.deleteCacheByKey(RedisKeyConfig.BLOG_INFO_CACHE);
            redisService.deleteCacheByKey(RedisKeyConfig.ARCHIVE_INFO_CACHE);
            redisService.deleteCacheByKey(RedisKeyConfig.CATEGORY_BLOG_CACHE);
            return Result.succ(null);
        } else {
            return Result.fail("删除失败");
        }


    }


    /**
     * 修改某个博客
     */
    @RequiresPermissions("user:update")
    @RequiresAuthentication
    @PostMapping("/blog/update")
    public Result update(@Validated @RequestBody Blog blog) {
        //System.out.println(blog.toString());
        Blog temp = null;
        if (blog.getId() != null) {
            temp = blogService.getById(blog.getId());
            //Assert.isTrue(temp.getUserId() == ShiroUtil.getProfile().getId(), "没有权限编辑");
        } else {
            temp = new Blog();
            temp.setUserId(ShiroUtil.getProfile().getId());
            temp.setCreateTime(LocalDateTime.now());
            temp.setStatus(0);
        }
        temp.setUpdateTime(LocalDateTime.now());
        BeanUtil.copyProperties(blog, temp, "id", "userId", "createTime", "updateTime");
        blogService.saveOrUpdate(temp);
        redisService.deleteCacheByKey(RedisKeyConfig.BLOG_INFO_CACHE);
        redisService.deleteCacheByKey(RedisKeyConfig.ARCHIVE_INFO_CACHE);
        redisService.deleteCacheByKey(RedisKeyConfig.CATEGORY_BLOG_CACHE);
        return Result.succ(null);
    }

    /**
     * 创建博客
     */
    @RequiresPermissions("user:create")
    @RequiresAuthentication
    @PostMapping("/blog/create")
    public Result create(@Validated @RequestBody Blog blog) {
        //System.out.println(blog.toString());
        Blog temp = null;
        if (blog.getId() != null) {
            temp = blogService.getById(blog.getId());
        } else {
            temp = new Blog();
            temp.setUserId(ShiroUtil.getProfile().getId());
            temp.setCreateTime(LocalDateTime.now());
        }
        temp.setUpdateTime(LocalDateTime.now());
        BeanUtil.copyProperties(blog, temp, "id", "userId", "createTime", "updateTime");
        blogService.saveOrUpdate(temp);
        redisService.deleteCacheByKey(RedisKeyConfig.BLOG_INFO_CACHE);
        redisService.deleteCacheByKey(RedisKeyConfig.ARCHIVE_INFO_CACHE);
        redisService.deleteCacheByKey(RedisKeyConfig.CATEGORY_BLOG_CACHE);
        return Result.succ(null);
    }

    /**
     * 修改博客状态
     */
    @RequiresPermissions("user:update")
    @RequestMapping("blog/publish/{id}")
    public Result publish(@PathVariable(name = "id")String id){
        Blog blog = blogService.getById(id);
        if (blog.getStatus()==0)
        {
            blog.setStatus(1 );
        }
        else {
            blog.setStatus(0);
        }
        blogService.saveOrUpdate(blog);
        redisService.deleteCacheByKey(RedisKeyConfig.BLOG_INFO_CACHE);
        redisService.deleteCacheByKey(RedisKeyConfig.ARCHIVE_INFO_CACHE);
        redisService.deleteCacheByKey(RedisKeyConfig.CATEGORY_BLOG_CACHE);
        return Result.succ(null);

    }


//    /**
//     * 博客浏览次数加一
//     */
//    @RequestMapping("/blog/view/{id}")
//    public Result addView(@PathVariable(name = "id")String id){
//        Blog blog = blogService.getById(id);
//        blog.setViews(blog.getViews()+1);
//        blogService.saveOrUpdate(blog);
//        return Result.succ(null);
//    }
}

