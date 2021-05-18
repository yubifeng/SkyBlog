package com.danli.controller;


import com.danli.common.lang.Result;
import com.danli.entity.Blog;
import com.danli.entity.Friend;
import com.danli.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @RequestMapping("/friendList")
    public Result getFriendList(){
        List<Friend> list = friendService.lambdaQuery().eq(Friend::getIsPublished, 1).list();

        return Result.succ(list);
    }

}
