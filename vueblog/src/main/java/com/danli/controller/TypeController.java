package com.danli.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.danli.common.lang.Result;
import com.danli.entity.Type;
import com.danli.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

public class TypeController {
    @Autowired
    TypeService typeService;

    @GetMapping("/types")
    public Result blogs() {

        List<Type> list = typeService.list(new QueryWrapper<Type>());
        return Result.succ(list);
    }
}
