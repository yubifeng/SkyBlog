package com.danli.controller;


import cn.hutool.core.bean.BeanUtil;
import com.danli.common.lang.Result;
import com.danli.entity.Blog;
import com.danli.entity.Friend;
import com.danli.entity.Visitor;
import com.danli.service.FriendService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author fanfanli
 * @since 2021-04-08
 */
@RestController

public class FriendController {


    @Autowired
    FriendService friendService;
    //获取友链接口
    @RequestMapping("/friendList")
    public Result getFriendList(){
        List<Friend> list = friendService.lambdaQuery().eq(Friend::getIsPublished, 1).list();

        return Result.succ(list);
    }
    //友链浏览次数加一
    @RequestMapping("/friend/{id}")
    public Result addView(@PathVariable(name = "id")String id){
        Friend friend = friendService.getById(id);
        friend.setViews(friend.getViews()+1);
//        Friend temp = new Friend();
//        BeanUtil.copyProperties(friend, temp);
        friendService.saveOrUpdate(friend);
        return Result.succ(null);
    }
//    @RequiresAuthentication
//    @PostMapping("/friend/edit")
//    public Result saveOrUpgrade(@Validated @RequestBody Friend friend){
//
//
//
//
//
//    }


}
