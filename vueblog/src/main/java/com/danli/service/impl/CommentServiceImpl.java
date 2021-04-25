package com.danli.service.impl;

import com.danli.mapper.CommentMapper;
import com.danli.entity.Comment;
import com.danli.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fanfanli
 * @since 2021-04-08
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
