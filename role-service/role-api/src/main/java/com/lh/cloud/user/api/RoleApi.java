package com.lh.cloud.user.api;

import com.lh.cloud.common.param.response.Result;
import com.lh.cloud.role.domain.Role;
import com.lh.cloud.user.api.fallback.RoleApiFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author: lh
 * Date: 2022/6/17 11:21
 */
@FeignClient(name = "role-service",fallback = RoleApiFallback.class,path = "/role")
public interface RoleApi {
    /**
     * 保存角色
     * @param role
     * @return
     */
    @PostMapping("/saveRole")
    public Result saveRole(@RequestBody Role role);
}
