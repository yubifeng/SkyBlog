package com.danli.controller;


import com.danli.common.lang.Result;
import com.danli.common.lang.vo.VisitorNum;
import com.danli.entity.VisitLog;
import com.danli.entity.Visitor;
import com.danli.service.VisitorService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
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

    //获取总uv和pv
    @GetMapping("/visitornum")
    public Result getPvAndUv(){
        int uv = visitorService.list().size();
        int pv = visitorService.getPv();
        VisitorNum visitorNum = new VisitorNum(uv,pv);
        return Result.succ(visitorNum);
    }

    //查询所有游客
    @RequiresAuthentication
    @RequestMapping("/visitor")
    public Result getFriendList(){
        List<Visitor> list = visitorService.lambdaQuery().list();

        return Result.succ(list);
    }



    //增改某个游客
    @RequiresAuthentication
    @PostMapping("/visitor/update")
    public Result updateVisitLog(@Validated @RequestBody Visitor visitor){
        if(visitor ==null){
            return Result.fail("不能为空");
        }
        else{
            if(visitor.getId()==null){
                visitor.setLastTime(LocalDateTime.now());
                visitor.setCreateTime(LocalDateTime.now());
            }
            visitorService.saveOrUpdate(visitor);
        }
        return Result.succ(null);
    }

    //删除某个游客
    @RequiresAuthentication
    @GetMapping("/visitor/delete/{id}")
    public Result delete(@PathVariable(name = "id") Long id) {

        if (visitorService.removeById(id)) {
            return Result.succ(null);
        } else {
            return Result.fail("删除失败");
        }


    }


}
