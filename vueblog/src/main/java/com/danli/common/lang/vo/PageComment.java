package com.danli.common.lang.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.tracing.dtrace.ArgsAttributes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PageComment {
    private Long id;
    private String nickname;//昵称
    private String content;//评论内容
    private String avatar;//头像(图片路径)
    private String website;//个人网站
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;//评论时间
    private Integer isAdminComment;//博主回复
    private String parentCommentNickname;//父评论昵称
    private List<PageComment> replyComments = new ArrayList<>();//回复该评论的评论
}
