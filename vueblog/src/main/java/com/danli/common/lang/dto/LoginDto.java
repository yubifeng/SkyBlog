package com.danli.common.lang.dto;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotBlank;
import java.io.InputStream;
import java.io.Serializable;

/**
 * 登录数据实体类
 *
 * @author fanfanli
 * @date   2021/5/3
 */
@Slf4j
@Data
public class LoginDto extends AbstractMethodError implements Serializable  {

    @NotBlank(message = "昵称不能为空")
    private String username;
    @NotBlank(message = "密码不能为空")
    private String password;


    public static void main(String[] args) {


        InputStream inputStream = LoginDto.class.getResourceAsStream("");


    }

}
