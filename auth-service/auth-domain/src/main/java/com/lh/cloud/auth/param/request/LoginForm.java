package com.lh.cloud.auth.param.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: lh
 * @date: 2022/9/30 17:01
 */
@Data
public class LoginForm implements Serializable {
    /**
     * 账号
     */
    private String account;
    /**
     * 密码
     */
    private String password;
}
