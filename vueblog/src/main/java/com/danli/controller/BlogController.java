package com.danli.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.danli.annotation.VisitLogger;
import com.danli.common.lang.Result;
import com.danli.common.lang.vo.BlogInfo;
import com.danli.entity.Blog;
import com.danli.service.BlogService;
import com.danli.util.ShiroUtil;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @@Description 博客相关
 * @Author Danli
 * @Date 2021-04-05
 */
@RestController
public class BlogController {
    @Autowired
    BlogService blogService;

    /**
     * 按置顶、创建时间排序 分页查询博客简要信息列表
     *
     * @param currentPage 页码
     * @return
     */
    @VisitLogger(behavior = "访问页面",content = "首页")
    @GetMapping("/blogs")
    public Result blogs(@RequestParam(defaultValue = "1") Integer currentPage) {

        Page page = new Page(currentPage, 5);
        IPage pageData = blogService.page(page, new QueryWrapper<Blog>().eq("status", 1).orderByDesc("create_time"));

        return Result.succ(pageData);
    }

    @VisitLogger(behavior = "查看分类")
    @GetMapping("/blogsByType")
    public Result blogsByType(@RequestParam(defaultValue = "1") Integer currentPage, @RequestParam String typeName) {
        List<BlogInfo> list = blogService.getBlogInfoListByCategoryName(typeName);
        int pageSize = 5;
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
        return Result.succ(page);
    }

    @RequiresPermissions("user:read")
    @GetMapping("/blogList")
    public Result blogList(@RequestParam(defaultValue = "1") Integer currentPage,@RequestParam(defaultValue = "10") Integer pageSize) {

        Page page = new Page(currentPage, pageSize);
        IPage pageData = blogService.page(page, new QueryWrapper<Blog>().orderByDesc("create_time"));
        return Result.succ(pageData);
    }

    @RequiresPermissions("user:read")
    @GetMapping("/blog/all")
    public Result blogAll() {
        List<Blog> list = blogService.lambdaQuery().list();
        return Result.succ(list);
    }
    @VisitLogger(behavior = "访问页面",content = "友链")
    @GetMapping("/friends")
    public Result friends() {
        List<Blog> list = blogService.lambdaQuery().eq(Blog::getTitle, "友情链接").list();

        return Result.succ(list.get(0));
    }

    @VisitLogger(behavior = "访问页面",content = "关于我")
    @GetMapping("/about")
    public Result aboutMe() {
        List<Blog> list = blogService.lambdaQuery().eq(Blog::getTitle, "关于我！！").list();


        return Result.succ(list.get(0));
    }

    @VisitLogger(behavior = "访问页面",content = "归档")
    @GetMapping("/blog/archives")
    public Result getBlogsArchives(@RequestParam(defaultValue = "1") Integer currentPage) {
        Page page = new Page(currentPage, 15);
        IPage pageData = blogService.page(page, new QueryWrapper<Blog>().eq("status", 1).orderByDesc("create_time"));
        return Result.succ(pageData);
    }

    @VisitLogger(behavior = "搜索博客")
    @GetMapping("/search")
    public Result search(@RequestParam String queryString) {
        List<Blog> list = blogService.list(new QueryWrapper<Blog>().like("content", queryString).eq("status", 1).orderByDesc("create_time"));
        return Result.succ(list);
    }

    @VisitLogger(behavior = "查看博客")
    @GetMapping("/blog/{id}")
    public Result detail(@PathVariable(name = "id") Long id) {
        Blog blog = blogService.getById(id);
        Assert.notNull(blog, "该博客已删除！");
        return Result.succ(blog);

    }

    @RequiresRoles("role_root")
    @RequiresPermissions("user:delete")
    @RequiresAuthentication
    @GetMapping("/blog/delete/{id}")
    public Result delete(@PathVariable(name = "id") Long id) {

        if (blogService.removeById(id)) {
            return Result.succ(null);
        } else {
            return Result.fail("删除失败");
        }


    }


    @RequiresPermissions("user:update")
    @RequiresAuthentication
    @PostMapping("/blog/update")
    public Result update(@Validated @RequestBody Blog blog) {
        //System.out.println(blog.toString());
        Blog temp = null;
        if (blog.getId() != null) {
            temp = blogService.getById(blog.getId());
//            Assert.isTrue(temp.getUserId() == ShiroUtil.getProfile().getId(), "没有权限编辑");
        } else {
            temp = new Blog();
            temp.setUserId(ShiroUtil.getProfile().getId());
            temp.setCreateTime(LocalDateTime.now());
            temp.setStatus(0);
        }
        temp.setUpdateTime(LocalDateTime.now());
        BeanUtil.copyProperties(blog, temp, "id", "userId", "createTime", "updateTime");
        blogService.saveOrUpdate(temp);
        return Result.succ(null);
    }


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
        return Result.succ(null);
    }

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
        return Result.succ(null);

    }

    //博客浏览次数加一
    @RequestMapping("/blog/view/{id}")
    public Result addView(@PathVariable(name = "id")String id){
        Blog blog = blogService.getById(id);
        blog.setViews(blog.getViews()+1);
        blogService.saveOrUpdate(blog);
        return Result.succ(null);
    }
}

