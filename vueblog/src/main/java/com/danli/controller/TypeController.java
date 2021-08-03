package com.danli.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.danli.annotation.VisitLogger;
import com.danli.common.lang.Result;
import com.danli.entity.Friend;
import com.danli.entity.Type;
import com.danli.service.TypeService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
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

public class TypeController {
    @Autowired
    TypeService typeService;

    @GetMapping("/types")
    public Result blogs() {

        List<Type> list = typeService.list(new QueryWrapper<Type>());
        return Result.succ(list);
    }


    //分页查询分类
    @RequiresAuthentication
    @RequiresPermissions("user:read")
    @GetMapping("/type/list")
    public Result typeList(@RequestParam(defaultValue = "1") Integer currentPage, @RequestParam(defaultValue = "10") Integer pageSize) {

        Page page = new Page(currentPage, pageSize);
        IPage pageData = typeService.page(page, new QueryWrapper<Type>());
        return Result.succ(pageData);
    }

    //创建分类
    @RequiresPermissions("user:create")
    @RequiresAuthentication
    @PostMapping("/type/create")
    public Result createType(@Validated @RequestBody Type type){
        if(type==null){
            return Result.fail("不能为空");
        }
        else{
            typeService.saveOrUpdate(type);
        }
        return Result.succ(null);
    }


    //创建分类
    @RequiresPermissions("user:update")
    @RequiresAuthentication
    @PostMapping("/type/update")
    public Result updateType(@Validated @RequestBody Type type){
        if(type==null){
            return Result.fail("不能为空");
        }
        else{
            typeService.saveOrUpdate(type);
        }
        return Result.succ(null);
    }

    //删除分类
    @RequiresRoles("role_root")
    @RequiresPermissions("user:delete")
    @RequiresAuthentication
    @GetMapping("/type/delete/{id}")
    public Result delete(@PathVariable(name = "id") Long id) {

        if (typeService.removeById(id)) {
            return Result.succ(null);
        } else {
            return Result.fail("删除失败");
        }

    }




}
