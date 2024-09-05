package com.lh.cloud.framework.redis.util;

import com.lh.cloud.common.constants.Constants;
import com.lh.cloud.user.domain.User;
import com.lh.cloud.user.domain.param.response.UserVO;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @author: lh
 * @date: 2022/10/6 0:04
 */
@Component
@AllArgsConstructor
public class UserProvider {
    private final TokenService tokenService;
    private final RedisUtil redisUtil;

    /**
     * 获取用户详情
     *
     * @return
     */
    public User getUser() {
        String token = tokenService.getToken();
        if (StringUtils.isNotBlank(token)) {
            String userKey = Constants.REDIS_PREFIX + Constants.TOKEN + tokenService.getUserKey(token);
            return (User) redisUtil.getCacheObject(userKey);
        }
        return null;
    }

    /**
     * 删除当前用户
     *
     * @return
     */
    public void delUser() {
        String token = tokenService.getToken();
        if (StringUtils.isNotBlank(token)) {
            String userKey = Constants.REDIS_PREFIX + Constants.TOKEN + tokenService.getUserKey(token);
            redisUtil.deleteObject(userKey);
        }
    }

    /**
     * 重新设置当前登录用户信息
     *
     * @return
     */
    public void setUser(User user) {
        String token = tokenService.getToken();
        if (StringUtils.isNotBlank(token)){
            tokenService.verifyToken(token, user);
        }
    }


}
