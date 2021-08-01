package com.danli.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.danli.common.lang.Result;
import com.danli.entity.Friend;
import com.danli.entity.VisitLog;
import com.danli.entity.Visitor;
import com.danli.mapper.VisitLogMapper;
import com.danli.service.VisitLogService;
import com.danli.util.IpAddressUtils;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author fanfanli
 * @since 2021-04-08
 */
@Service
public class VisitLogServiceImpl extends ServiceImpl<VisitLogMapper, VisitLog> implements VisitLogService {

}
