package com.danli.controller;


import com.danli.common.lang.Result;
import com.danli.entity.VisitLog;
import com.danli.service.VisitLogService;
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
public class VisitLogController {
    @Autowired
    VisitLogService visitLogService;

    //查询所有游客浏览日志
    @RequiresAuthentication
    @RequestMapping("/visitLogList")
    public Result getFriendList(){
        List<VisitLog> list = visitLogService.lambdaQuery().list();

        return Result.succ(list);
    }



    //增改某个日志
    @RequiresAuthentication
    @PostMapping("/visitLog/update")
    public Result updateVisitLog(@Validated @RequestBody VisitLog visitLog){
        if(visitLog ==null){
            return Result.fail("不能为空");

        }
        else{
            if(visitLog.getId()==null){
                visitLog.setCreateTime(LocalDateTime.now());
            }
            visitLogService.saveOrUpdate(visitLog);
        }
        return Result.succ(null);
    }

    //删除某个浏览日志
    @RequiresAuthentication
    @GetMapping("/visitLog/delete/{id}")
    public Result delete(@PathVariable(name = "id") Long id) {

        if (visitLogService.removeById(id)) {
            return Result.succ(null);
        } else {
            return Result.fail("删除失败");
        }


    }
}
