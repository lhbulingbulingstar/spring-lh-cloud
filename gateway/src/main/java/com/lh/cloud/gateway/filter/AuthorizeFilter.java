package com.lh.cloud.gateway.filter;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.alibaba.fastjson.JSON;
import com.lh.cloud.common.param.response.Result;
import com.lh.cloud.common.param.response.ResultCode;
import com.lh.cloud.framework.redis.util.TokenService;
import com.lh.cloud.gateway.config.FilterProperties;
import com.lh.cloud.gateway.util.AllowPathUtil;
import lombok.AllArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @Author: lh
 * @Date: 2021/12/29 17:32
 */
@Component
@AllArgsConstructor
public class AuthorizeFilter implements GlobalFilter, Ordered {

    private final TokenService tokenService;
    private final FilterProperties filterProperties;

    /**
     * 通用拦截器
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest serverHttpRequest = exchange.getRequest();
        //将request存放至threadLocal

        ServerHttpResponse serverHttpResponse = exchange.getResponse();
        //判断是否白名单 白名单不需要判断token
        for (String rule : filterProperties.getAllowPaths()) {
            if (AllowPathUtil.isMatch(rule,serverHttpRequest.getURI().getPath())) {
                return chain.filter(exchange);
            }
        }
        String token = tokenService.getToken(serverHttpRequest);
        serverHttpResponse.setStatusCode(HttpStatus.OK);
        boolean flag=false;
        //验证token是否为空               验证token并刷新token时间
        if (StringUtils.isBlank(token)||!tokenService.existAndVerifyToken(token)) {
            flag=true;
        }
        if (flag){
            Result result = new Result(ResultCode.UNAUTHORIZED,"未授权",null);
            return getVoidMono(serverHttpResponse,result);
        }
        return chain.filter(exchange);
    }

    /**
     * 格式化webflux返回
     * @param serverHttpResponse
     * @param result
     * @return
     */
    private Mono<Void> getVoidMono(ServerHttpResponse serverHttpResponse, Result result) {
        serverHttpResponse.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        DataBuffer dataBuffer = serverHttpResponse.bufferFactory().wrap(JSON.toJSONString(result).getBytes());
        return serverHttpResponse.writeWith(Flux.just(dataBuffer));
    }


    @Override
    public int getOrder() {
        return 0;
    }


}