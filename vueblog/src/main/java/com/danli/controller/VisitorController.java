package com.danli.controller;


import com.danli.common.lang.Result;
import com.danli.entity.Visitor;
import com.danli.service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author fanfanli
 * @since 2021-04-08
 */
@RestController
@RequestMapping("/visitor")
public class VisitorController {
    @Autowired
    VisitorService visitorService;

    public Result visitorInfo(Visitor visitor) {
        System.out.println(visitor);
        if (visitor != null) {
            visitorService.saveOrUpdate(visitor);
            return Result.succ(null);
        } else {
            return Result.fail(null);
        }

    }


}
