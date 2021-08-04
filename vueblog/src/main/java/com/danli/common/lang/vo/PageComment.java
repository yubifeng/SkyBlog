package com.danli.common.lang.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 传输给前端的评论视图对象
 *
 * @author fanfanli
 * @date 2021/5/1
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PageComment {
    private Long id;
    //昵称
    private String nickname;
    //评论内容
    private String content;
    //头像(图片路径)
    private String avatar;
    //个人网站
    private String website;
    //评论时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    //博主回复
    private Integer isAdminComment;
    //父评论昵称
    private String parentCommentNickname;
    //回复该评论的评论
    private List<PageComment> replyComments = new ArrayList<>();
}
