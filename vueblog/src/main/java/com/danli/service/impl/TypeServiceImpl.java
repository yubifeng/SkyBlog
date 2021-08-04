package com.danli.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.danli.entity.Type;
import com.danli.mapper.TypeMapper;
import com.danli.service.TypeService;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 *
 * @author fanfanli
 * @date 2021-04-08
 */
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type> implements TypeService {

}
