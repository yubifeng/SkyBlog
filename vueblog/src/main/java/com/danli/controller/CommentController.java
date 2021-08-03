package com.danli.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.danli.annotation.VisitLogger;
import com.danli.common.lang.Result;
import com.danli.common.lang.vo.PageComment;
import com.danli.entity.Comment;
import com.danli.entity.Friend;
import com.danli.service.CommentService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author fanfanli
 * @since 2021-04-08
 */
@RestController
public class CommentController {
    @Autowired
    CommentService commentService;

    //分页查询评论
    @RequiresAuthentication
    @RequiresPermissions("user:read")
    @GetMapping("/commentList")
    public Result getCommentListByPage(@RequestParam(defaultValue = "1") Integer currentPage,@RequestParam(defaultValue = "10") Integer pageSize ) {


        Page page = new Page(currentPage, pageSize);
        IPage pageData = commentService.page(page, new QueryWrapper<Comment>().orderByDesc("create_time"));

        //Assert.notNull(blog, "该博客已删除！");
        return Result.succ(pageData);

    }

    //分页查询博客对应评论
    @RequiresAuthentication
    @RequiresPermissions("user:read")
    @GetMapping("/comment/detail")
    public Result getCommentListByPageId(@RequestParam(defaultValue = "1") Long blogId, @RequestParam(defaultValue = "1") Integer currentPage,@RequestParam(defaultValue = "10") Integer pageSize ) {

        Page page = new Page(currentPage, pageSize);
        IPage pageData = commentService.page(page, new QueryWrapper<Comment>().eq("blog_id",blogId).orderByDesc("create_time"));

        //Assert.notNull(blog, "该博客已删除！");
        return Result.succ(pageData);

    }


    //获取某个博客下的所有评论
    @GetMapping("/comment/{blogId}")
    public Result getCommentByBlogId(@PathVariable(name = "blogId") Long blogId) {

        //实体模型集合对象转换为VO对象集合
        List<PageComment> pageComments = commentService.getPageCommentListByDesc(blogId, (long) -1);

        for (PageComment pageComment : pageComments) {

            List<PageComment> repley = commentService.getPageCommentList(blogId, pageComment.getId());
            pageComment.setReplyComments(repley);


        }

        //Assert.notNull(blog, "该博客已删除！");
        return Result.succ(pageComments);

    }

    @RequiresAuthentication
    @RequiresPermissions("user:update")
    @RequestMapping("comment/publish/{id}")
    public Result publish(@PathVariable(name = "id")String id){
        Comment comment = commentService.getById(id);
        comment.setIsPublished(!comment.getIsPublished());
//        Friend temp = new Friend();
//        BeanUtil.copyProperties(friend, temp);
        commentService.saveOrUpdate(comment);
        return Result.succ(null);

    }

    @RequiresAuthentication
    @RequiresPermissions("user:update")
    @RequestMapping("comment/update")
    public Result updateById(@Validated @RequestBody Comment comment){
        if(comment==null){
            return Result.fail("不能为空");
        }
//        Friend temp = new Friend();
//        BeanUtil.copyProperties(friend, temp);
        commentService.saveOrUpdate(comment);
        return Result.succ(null);

    }


    @RequiresRoles("role_root")
    @RequiresPermissions("user:delete")
    @RequiresAuthentication
    @RequestMapping("comment/delete/{id}")
    public Result delete(@PathVariable(name = "id")String id){

        if (commentService.removeById(id)) {
            return Result.succ(null);
        } else {
            return Result.fail("删除失败");
        }

    }
    @VisitLogger(behavior = "提交评论")
    @PostMapping("/comment/add")
    public Result edit(@Validated @RequestBody Comment comment, HttpServletRequest request) {

        if (comment.getContent().contains("<script>") || comment.getEmail().contains("<script>") || comment.getNickname().contains("<script>") || comment.getWebsite().contains("<script>")) {
            return Result.fail("非法输入");
        }
        System.out.println(comment.toString());
        Comment temp = new Comment();
        temp.setCreateTime(LocalDateTime.now());
        temp.setIp(request.getHeader("x-forwarded-for"));
        BeanUtil.copyProperties(comment, temp, "id", "ip", "createTime");
        commentService.saveOrUpdate(temp);
        return Result.succ(null);
    }


}
