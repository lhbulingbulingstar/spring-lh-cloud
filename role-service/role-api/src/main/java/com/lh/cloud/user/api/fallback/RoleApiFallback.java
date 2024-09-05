package com.lh.cloud.user.api.fallback;

import com.lh.cloud.common.param.response.Result;
import com.lh.cloud.role.domain.Role;
import com.lh.cloud.user.api.RoleApi;
import org.springframework.stereotype.Component;

/**
 * @author: lh
 * @date: 2022/9/30 14:42
 */
@Component
public class RoleApiFallback implements RoleApi {
    @Override
    public Result saveRole(Role role) {
        return null;
    }
}
