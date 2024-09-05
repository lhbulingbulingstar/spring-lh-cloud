package com.lh.cloud.gateway.util;

import org.springframework.util.AntPathMatcher;

/**
 * @Author: lh
 * @Date: 2021/12/30 11:30
 */
public class AllowPathUtil {
    /**
     * 判断url是否与规则配置:
     * ? 表示单个字符
     * * 表示一层路径内的任意字符串，不可跨层级
     * ** 表示任意层路径
     *
     * @param rule     匹配规则
     * @param urlPath 需要匹配的url
     * @return
     */
    public static boolean isMatch(String rule, String urlPath) {
        AntPathMatcher matcher = new AntPathMatcher();
        return matcher.match(rule, urlPath);
    }
}
