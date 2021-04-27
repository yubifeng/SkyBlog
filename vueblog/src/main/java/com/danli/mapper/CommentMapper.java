package com.danli.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.danli.common.lang.vo.PageComment;
import com.danli.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author fanfanli
 * @since 2021-04-08
 */
@Mapper
@Repository
public interface CommentMapper extends BaseMapper<Comment> {
    //通过select注解实现查询
    @Select("select id, nickname, content, avatar, create_time, is_admin_comment  from comment where blog_id=#{blogId} and parent_comment_id=#{parentCommentId} order by create_time desc")
    List<PageComment> getPageCommentListByPageAndParentCommentId(@Param("blogId") long blogId, @Param("parentCommentId") long  parentCommentId);

}
