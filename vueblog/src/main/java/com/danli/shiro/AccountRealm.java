package com.danli.shiro;

import cn.hutool.core.bean.BeanUtil;
import com.danli.entity.User;
import com.danli.service.UserService;
import com.danli.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 登录认证和授权
 *
 * @author fanfanli
 * @date  2021/5/28
 */
@Slf4j
@Component
public class AccountRealm extends AuthorizingRealm {
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserService userService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }


    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.info("执行doGetAuthorizationInfo方法进行授权");
//        String username = JwtUtil.getUsername(principalCollection.toString());
        log.info(principals.toString());
//        log.info("登录的用户:" + username);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        AccountProfile accountProfile = (AccountProfile)principals.getPrimaryPrincipal();
        String[] roles = accountProfile.getRole().split(",");
        log.info("roles");
        for(String role : roles){
            info.addRole(role);
            if(role.equals("role_root")){
                info.addStringPermission("user:create");
                info.addStringPermission("user:update");
                info.addStringPermission("user:read");
                info.addStringPermission("user:delete");
            }
            else if( role.equals("role_admin")){
                info.addStringPermission("user:read");
                info.addStringPermission("user:create");
                info.addStringPermission("user:update");
            }
            else if( role.equals("role_user")){
                info.addStringPermission("user:read");
                info.addStringPermission("user:create");
            }
            else if(role.equals("role_guest")){
                info.addStringPermission("user:read");
            }
        }


        return info;

    }

    /**
     * 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        JwtToken jwt = (JwtToken) token;
        log.info("jwt----------------->{}", jwt);
        String userId = (String) jwtUtils.getClaimByToken((String) jwt.getPrincipal()).get("userId");
        String username = (String) jwtUtils.getClaimByToken((String) jwt.getPrincipal()).get("username");
        User user = userService.getById(Long.parseLong(userId));
        if (user == null) {
            throw new UnknownAccountException("账户不存在！");
        }
        if (user.getStatus() == -1) {
            throw new LockedAccountException("账户已被锁定！");
        }
        if(!user.getUsername().equals(username)){
            throw new UnknownAccountException("userId与username不一致");
        }
        AccountProfile profile = new AccountProfile();
        //知道它的身份 principals
        BeanUtil.copyProperties(user, profile);
        log.info("profile----------------->{}", profile.toString());
        return new SimpleAuthenticationInfo(profile, jwt.getCredentials(), getName());
    }
}

