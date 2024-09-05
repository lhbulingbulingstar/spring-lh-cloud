package com.lh.cloud.role.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Author: lh
 * Date: 2022/6/22 17:27
 */

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_role")
public class Role {
    @TableId(value = "role_id")
    private String roleId;

    @TableField("name")
    private String name;

}
