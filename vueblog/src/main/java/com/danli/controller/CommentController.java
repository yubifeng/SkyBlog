package com.danli.controller;



import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSON;
import com.danli.common.lang.Result;
import com.danli.common.lang.vo.PageComment;
import com.danli.entity.Blog;
import com.danli.entity.Comment;
import com.danli.service.CommentService;
import com.danli.util.ShiroUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fanfanli
 * @since 2021-04-08
 */
@RestController
public class CommentController {
    @Autowired
    CommentService commentService;






    @GetMapping("/comment/{id}")
    public Result getCommentByBlogId(@PathVariable(name = "id") Long id) {

        //实体模型集合对象转换为VO对象集合
         List<PageComment>  pageComments = commentService.getPageCommentListByDesc(id, (long) -1);

        for ( PageComment pageComment : pageComments) {

            List<PageComment> repley = commentService.getPageCommentList(id, pageComment.getId());
            pageComment.setReplyComments(repley);


        }

        //Assert.notNull(blog, "该博客已删除！");
        return Result.succ(pageComments);

    }





    @PostMapping("/comment/add")
    public Result edit(@Validated @RequestBody Comment comment,HttpServletRequest request) {

        if(comment.getContent().contains("<script>")||comment.getEmail().contains("<script>")||comment.getNickname().contains("<script>")||comment.getWebsite().contains("<script>")){
            return Result.fail("非法输入");
        }
        System.out.println(comment.toString());
        Comment temp = new Comment();
        temp.setCreateTime(LocalDateTime.now());
        temp.setIp(request.getRemoteAddr());
        BeanUtil.copyProperties(comment, temp, "id", "ip", "createTime");
        commentService.saveOrUpdate(temp);
        return Result.succ(null);
    }













}
