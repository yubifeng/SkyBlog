package com.danli.controller;


import com.danli.common.lang.Result;
import com.danli.common.lang.vo.VisitorNum;
import com.danli.entity.Visitor;
import com.danli.service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author fanfanli
 * @since 2021-04-08
 */
@RestController
public class VisitorController {
    @Autowired
    VisitorService visitorService;
//    @RequestMapping("/visitor")
//    public Result visitorInfo(Visitor visitor) {
//        System.out.println(visitor);
//        if (visitor != null) {
//            visitorService.saveOrUpdate(visitor);
//            return Result.succ(null);
//        } else {
//         return Result.fail(null);
//        }
//
//    }


    @GetMapping("/visitornum")
    public Result getPvAndUv(){
        int uv = visitorService.list().size();
        int pv = visitorService.getPv();
        VisitorNum visitorNum = new VisitorNum(uv,pv);
        return Result.succ(visitorNum);
    }




}
