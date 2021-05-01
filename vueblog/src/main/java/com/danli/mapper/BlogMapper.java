package com.danli.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.danli.common.lang.vo.BlogInfo;
import com.danli.entity.Blog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author fanfanli
 * @since 2021-04-08
 */
@Mapper
@Repository
public interface BlogMapper extends BaseMapper<Blog> {
    List<BlogInfo> getBlogByTypeName(String typeName);

}
