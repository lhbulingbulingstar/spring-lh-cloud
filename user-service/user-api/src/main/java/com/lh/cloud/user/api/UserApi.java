package com.lh.cloud.user.api;

import com.lh.cloud.common.param.response.Result;
import com.lh.cloud.user.api.fallback.UserApiFallback;
import com.lh.cloud.common.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Date: 2022/6/17 11:21
 * @author lh
 */
@FeignClient(name = "user-service",fallback = UserApiFallback.class,path = "/user")
public interface UserApi {
    /**
     * 通过账户获取用户
     * @param account
     * @return
     */
    @RequestMapping("/getUserByAccount")
    public Result<User> getUserByAccount(@RequestParam("account") String account);
}
