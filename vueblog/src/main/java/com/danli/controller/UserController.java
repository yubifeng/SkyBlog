package com.danli.controller;


import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.danli.common.lang.Result;
import com.danli.common.lang.vo.UserInfo;
import com.danli.entity.User;
import com.danli.service.UserService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户前端控制器
 *
 * @author fanfanli
 * @date  2021/4/5
 */


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    /**
     * 分页查询用户
     */
    @RequiresRoles("role_root")
    @RequiresAuthentication
    @RequiresPermissions("user:read")
    @GetMapping("/list")
    public Result userList(@RequestParam(defaultValue = "1") Integer currentPage, @RequestParam(defaultValue = "10") Integer pageSize) {
        List<UserInfo> list = userService.getUserInfoList();
        int size = list.size();
        Page page = new Page(currentPage,pageSize);
        if (pageSize > size) {
            pageSize = size;
        }
        // 求出最大页数，防止currentPage越界
        int maxPage = size % pageSize == 0 ? size / pageSize : size / pageSize + 1;
        if (currentPage > maxPage) {
            currentPage = maxPage;
        }
        // 当前页第一条数据的下标
        int curIdx = currentPage > 1 ? (currentPage - 1) * pageSize : 0;
        List pageList = new ArrayList();
        // 将当前页的数据放进pageList
        for (int i = 0; i < pageSize && curIdx + i < size; i++) {
            pageList.add(list.get(curIdx + i));
        }
        page.setTotal(list.size()).setRecords(pageList);
        return Result.succ(page);
    }

    /**
     * 创建用户
     */
    @RequiresRoles("role_root")
    @RequiresPermissions("user:create")
    @RequiresAuthentication
    @PostMapping("/create")
    public Result createUser(@Validated @RequestBody User user){
        if(user==null){
            return Result.fail("不能为空");
        }
        else{
            if(user.getRole().contains("role_root")){
                return Result.fail("禁止设置root用户");
            }
            user.setUpdateTime(LocalDateTime.now());
            user.setCreateTime(LocalDateTime.now());
            user.setPassword(SecureUtil.md5(user.getPassword()));
            userService.saveOrUpdate(user);
        }
        return Result.succ(null);
    }


    /**
     * 修改用户信息
     */
    @RequiresRoles("role_root")
    @RequiresPermissions("user:update")
    @RequiresAuthentication
    @PostMapping("/update")
    public Result updateUser(@Validated @RequestBody User user){
        if(user==null){
            return Result.fail("不能为空");
        }
        else{
            user.setUpdateTime(LocalDateTime.now());
            User subUser = userService.getById(user.getId());
            if(subUser.getRole().equals("role_root")){
                return Result.fail("禁止修改此用户");
            }
            //未修改密码
            if(user.getPassword().equals("")){
                user.setPassword(subUser.getPassword());
            }
            else{
                //存储在数据中的密码为md5加密后的
                user.setPassword(SecureUtil.md5(user.getPassword()));
            }
            userService.saveOrUpdate(user);
        }
        return Result.succ(null);
    }

    /**
     * 删除用户
     */
    @RequiresRoles("role_root")
    @RequiresPermissions("user:delete")
    @RequiresAuthentication
    @GetMapping("/delete/{id}")
    public Result delete(@PathVariable(name = "id") Long id) {
        User user = userService.getById(id);
        if(user.getRole().equals("role_root")){
            return Result.fail("禁止删除此用户");
        }
        if (userService.removeById(id)) {
            return Result.succ(null);
        } else {
            return Result.fail("删除失败");
        }

    }

    /**
     * 修改用户的状态
     */
    @RequiresRoles("role_root")
    @RequiresPermissions("user:update")
    @RequestMapping("/publish/{id}")
    public Result publish(@PathVariable(name = "id")Long id){
        User user = userService.getById(id);
        if(user.getRole().equals("role_root")){
            return Result.fail("禁止禁用此用户");
        }
        if (user.getStatus()==0)
        {
            user.setStatus(1);
        }
        else {
            user.setStatus(0);
        }
        userService.saveOrUpdate(user);
        return Result.succ(null);
    }
}
