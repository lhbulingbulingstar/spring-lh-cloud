package com.lh.cloud.role.controller;

import com.lh.cloud.common.param.response.Result;
import com.lh.cloud.common.util.UserProvider;
import com.lh.cloud.role.domain.Role;
import com.lh.cloud.role.service.IRoleService;
import com.lh.cloud.common.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author lh
 */
@RestController
@RequestMapping("/role")
@AllArgsConstructor
public class RoleController {
    private final IRoleService roleService;
    private final UserProvider userProvider;
    @GetMapping("/get")
    public Result get() throws InterruptedException {
        return Result.success("获取到了值");
    }

    @GetMapping("/getRole")
    public Result getRole() throws InterruptedException {
        return Result.success("获取到了值");
    }

    @PostMapping("/saveRole")
    public Result saveRole(@RequestBody Role role) {
        User user = userProvider.getUser();
        roleService.save(role);
        return Result.success();
    }
}
