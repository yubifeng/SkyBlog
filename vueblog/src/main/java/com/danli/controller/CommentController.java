package com.danli.controller;



import cn.hutool.json.JSON;
import com.danli.common.lang.Result;
import com.danli.common.lang.vo.PageComment;
import com.danli.entity.Blog;
import com.danli.entity.Comment;
import com.danli.service.CommentService;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
         List<PageComment>  pageComments = commentService.getPageCommentList(id, (long) -1);


        for ( PageComment pageComment : pageComments) {

            List<PageComment> repley = commentService.getPageCommentList(id, pageComment.getId());
            pageComment.setReplyComments(repley);


        }


        //Assert.notNull(blog, "该博客已删除！");
        return Result.succ(pageComments);

    }

}
