package com.lh.cloud.role.convert;

import com.lh.cloud.role.domain.Role;
import com.lh.cloud.role.param.response.RoleVO;
import org.mapstruct.Mapper;


/**
 * @author: lh
 * @date: 2022/9/22 19:48
 */
@Mapper(componentModel = "spring")
public interface RoleConvert {

    RoleVO convert(Role role);
}
