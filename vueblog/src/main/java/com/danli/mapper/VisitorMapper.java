package com.danli.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.danli.entity.Visitor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

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
public interface VisitorMapper extends BaseMapper<Visitor> {
    @Select("select 1 from visitor where uuid=#{uuid} limit 1), 0")
    int hasUUID(String uuid);

}
