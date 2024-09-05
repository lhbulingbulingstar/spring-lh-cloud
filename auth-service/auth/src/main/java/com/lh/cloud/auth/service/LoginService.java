package com.lh.cloud.auth.service;

import com.lh.cloud.common.param.response.Result;
import com.lh.cloud.common.param.response.ResultCode;
import com.lh.cloud.framework.exception.CustomException;
import com.lh.cloud.framework.redis.util.TokenService;
import com.lh.cloud.framework.redis.util.UserProvider;
import com.lh.cloud.user.api.UserApi;
import com.lh.cloud.user.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author: lh
 * @date: 2022/9/30 17:05
 */
@Service
@AllArgsConstructor
public class LoginService {
    private final UserProvider userProvider;
    private final UserApi userApi;
    private final TokenService tokenService;
    private final BCryptPasswordEncoder passwordEncoder;
    /**
     * 登录接口
     * @param account 账号
     * @param password 密码
     * @return
     */
    public Result<String> login(String account, String password) {
        Result<String> success = Result.success();
        //生成令牌
        Result<User> result = userApi.getUserByAccount(account);
        if (result!=null&& ResultCode.SUCCESS.equals(result.getCode())){
            User user = result.getData();
            if (user==null){
                throw new CustomException("用户不存在");
            }
            //验证密码是否正确
            boolean matches = passwordEncoder.matches(password, user.getPassword());
            if (!matches){
                throw new CustomException("用户密码错误");
            }
            //生成token
            String token = tokenService.createToken(account,user);
            success.setData(token);
            return success;
        }
        return Result.error("服务异常");
    }

    public Result<String> logout() {
        userProvider.delUser();
        return Result.success("退出成功");
    }
}
