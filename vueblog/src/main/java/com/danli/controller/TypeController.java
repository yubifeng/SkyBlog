package com.danli.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.danli.common.lang.Result;
import com.danli.config.RedisKeyConfig;
import com.danli.entity.Type;
import com.danli.service.RedisService;
import com.danli.service.TypeService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 分类前端控制器
 *
 * @author fanfanli
 * @date  2021/4/8
 */
@RestController
public class TypeController {
    @Autowired
    TypeService typeService;
    @Autowired
    RedisService redisService;

    /**
     * 查询所有分类
     */
    @GetMapping("/types")
    public Result blogs() {
        if (redisService.hasHashKey(RedisKeyConfig.CATEGORY_NAME_CACHE, RedisKeyConfig.All)) {
            return Result.succ(redisService.getValueByHashKey(RedisKeyConfig.CATEGORY_NAME_CACHE, RedisKeyConfig.All));
        }
        List<Type> list = typeService.list(new QueryWrapper<Type>());
        redisService.saveKVToHash(RedisKeyConfig.CATEGORY_NAME_CACHE, RedisKeyConfig.All, list);
        return Result.succ(list);
    }



    /**
     * 分页查询分类
     */
    @RequiresAuthentication
    @RequiresPermissions("user:read")
    @GetMapping("/type/list")
    public Result typeList(@RequestParam(defaultValue = "1") Integer currentPage, @RequestParam(defaultValue = "10") Integer pageSize) {

        Page page = new Page(currentPage, pageSize);
        IPage pageData = typeService.page(page, new QueryWrapper<Type>());
        return Result.succ(pageData);
    }


    /**
     * 增加分类
     */
    @RequiresPermissions("user:create")
    @RequiresAuthentication
    @PostMapping("/type/create")
    public Result createType(@Validated @RequestBody Type type){
        if(type==null){
            return Result.fail("不能为空");
        }
        else{
            typeService.saveOrUpdate(type);
            redisService.deleteCacheByKey(RedisKeyConfig.CATEGORY_NAME_CACHE);
        }
        return Result.succ(null);
    }



    /**
     * 修改分类
     */
    @RequiresPermissions("user:update")
    @RequiresAuthentication
    @PostMapping("/type/update")
    public Result updateType(@Validated @RequestBody Type type){
        if(type==null){
            return Result.fail("不能为空");
        }
        else{
            typeService.saveOrUpdate(type);
            redisService.deleteCacheByKey(RedisKeyConfig.CATEGORY_NAME_CACHE);
        }
        return Result.succ(null);
    }


    /**
     * 删除分类
     */
    @RequiresRoles("role_root")
    @RequiresPermissions("user:delete")
    @RequiresAuthentication
    @GetMapping("/type/delete/{id}")
    public Result delete(@PathVariable(name = "id") Long id) {

        if (typeService.removeById(id)) {
            redisService.deleteCacheByKey(RedisKeyConfig.CATEGORY_NAME_CACHE);
            return Result.succ(null);
        } else {
            return Result.fail("删除失败");
        }

    }

}
