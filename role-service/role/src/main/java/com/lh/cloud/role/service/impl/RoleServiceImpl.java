package com.lh.cloud.role.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lh.cloud.role.domain.Role;
import com.lh.cloud.role.mapper.RoleMapper;
import com.lh.cloud.role.service.IRoleService;
import org.springframework.stereotype.Service;

/**
 * Author: lh
 * Date: 2022/6/22 17:05
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
}
