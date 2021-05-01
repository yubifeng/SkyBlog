package com.danli.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.danli.common.lang.vo.BlogInfo;
import com.danli.entity.Blog;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author fanfanli
 * @since 2021-04-05
 */
public interface BlogService extends IService<Blog> {
    List<BlogInfo> getBlogInfoListByCategoryName(String categoryName);
}
