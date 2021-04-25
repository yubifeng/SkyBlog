package com.danli.service.impl;

import com.danli.entity.Tag;
import com.danli.mapper.TagMapper;
import com.danli.service.TagService;
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
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

}
