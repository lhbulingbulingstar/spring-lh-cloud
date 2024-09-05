package com.lh.cloud.user.api.fallback;

import com.lh.cloud.common.param.response.Result;
import com.lh.cloud.user.api.UserApi;
import com.lh.cloud.user.domain.User;
import org.springframework.stereotype.Component;

/**
 * @author: lh
 * @date: 2022/9/30 14:46
 */

@Component
public class UserApiFallback implements UserApi {
    @Override
    public Result<User> getUserByAccount(String account) {
        return null;
    }
}
