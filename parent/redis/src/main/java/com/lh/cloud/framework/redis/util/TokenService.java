package com.lh.cloud.framework.redis.util;

import com.lh.cloud.common.constants.Constants;
import com.lh.cloud.common.util.JWTUtil;
import com.lh.cloud.user.domain.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * @author: lh
 * @date: 2022/10/5 22:59
 */
@Component
public class TokenService {
    @Autowired
    private RedisUtil redisUtil;

    @Value("${token.secret:\"\"")
    private String secret;

    @Value("${token.expireTime:0}")
    private Integer expireTime;

    @Value("${token.verifyTime:0}")
    private Integer verifyTime;
    /**
     * 刷新token
     * @param token
     */
    public void verifyToken(String token, User value){
        String userKey = getUserKey(token);
        redisUtil.setCacheObject(Constants.REDIS_PREFIX+Constants.TOKEN+userKey,value,expireTime, TimeUnit.MINUTES);
    }

    /**
     * 判断token是否在有效期 如果存在则刷新token时间
     * @param token
     * @return
     */
    public boolean existAndVerifyToken(String token){
        String userKey = Constants.REDIS_PREFIX+Constants.TOKEN+ getUserKey(token);
        User user = (User)redisUtil.getCacheObject(userKey);
        Long expire = redisUtil.getExpire(userKey);
        //如果剩余时长小于 TOKEN_VERIFY_TIME 则刷新token
        //说明token不存在
        if (user==null||expire<0){
            return false;
        }else {
            if (expire<(verifyTime*60*1000)){
                verifyToken(token,user);
            }
            return true;
        }
    }

    /**
     * 获取请求token
     *
     * @return token
     */
    public String getToken() {
        String token="";
        ServletRequestAttributes servletRequestAttributes =(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (servletRequestAttributes!=null){
            HttpServletRequest request = servletRequestAttributes.getRequest();
            String tokenStr = request.getHeader(Constants.TOKEN_HEADER);
            if (StringUtils.isNotEmpty(tokenStr) && tokenStr.startsWith(Constants.TOKEN_PREFIX)) {
                token = tokenStr.replace(Constants.TOKEN_PREFIX, "");
            }
        }
        return token;
    }
    /**
     * 获取请求token
     *
     * @param request
     * @return token
     */
    public String getToken(ServerHttpRequest request) {
        String token="";
        if (request!=null) {
            String tokenStr = request.getHeaders().getFirst(Constants.TOKEN_HEADER);
            if (StringUtils.isNotBlank(tokenStr) && tokenStr.startsWith(Constants.TOKEN_PREFIX)) {
                token = tokenStr.replace(Constants.TOKEN_PREFIX, "");
            }
        }
        return token;
    }

    /**
     * 通过用户id生成token
     * @param userKey
     * @return
     */
    public String createToken(String userKey, User user){
        String token = JWTUtil.generateToken(userKey, secret);
        verifyToken(token,user);
        return token;
    }

    /**
     * 通过token获取userKey
     *
     * @param token
     * @return token
     */
    public String getUserKey(String token) {
        return JWTUtil.getUserKey(token, secret);
    }

}
