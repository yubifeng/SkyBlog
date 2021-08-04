package com.danli.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.danli.common.lang.vo.BlogInfo;
import com.danli.entity.Blog;
import com.danli.mapper.BlogMapper;
import com.danli.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 服务实现类
 *
 * @author fanfanli
 * @date 2021-04-08
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {
    @Autowired
    BlogMapper blogMapper;

    /**
     * 通过分类名查找属于该分类的博客list
     *
     * @param categoryName 分类名
     * @return 博客list
     */
    @Override
    public List<BlogInfo> getBlogInfoListByCategoryName(String categoryName) {
        List<BlogInfo> blogInfos = blogMapper.getBlogByTypeName(categoryName);
        return blogInfos;
    }

}
