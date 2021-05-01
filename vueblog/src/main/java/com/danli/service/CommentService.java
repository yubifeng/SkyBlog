package com.danli.service;

import com.danli.common.lang.vo.PageComment;
import com.danli.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fanfanli
 * @since 2021-04-08
 */
public interface CommentService extends IService<Comment> {
    public List<PageComment> getPageCommentListByDesc(Long blogId, Long parentCommentId);
    public List<PageComment> getPageCommentList(Long blogId, Long parentCommentId);
}
