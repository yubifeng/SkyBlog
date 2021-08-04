package com.danli.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.danli.common.lang.vo.UserInfo;
import com.danli.entity.User;
import com.danli.mapper.UserMapper;
import com.danli.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author fanfanli
 * @since 2021-04-08
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public List<UserInfo> getUserInfoList(){
        List<UserInfo> userInfos = userMapper.getUserInfo();
        return  userInfos;
    }
}
