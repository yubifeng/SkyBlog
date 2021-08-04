package com.danli.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * Jwt
 *
 * @author fanfanli
 * @date  2021/5/28
 */
public class JwtToken implements AuthenticationToken {
    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}

