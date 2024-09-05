package com.lh.cloud.user.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lh.cloud.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Date: 2022/6/22 16:12
 * @author lh
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_user")
public class User extends BaseEntity {
    @TableId("id")
    private String id;

    @TableField("name")
    private String name;

    @TableField("account")
    private String account;

    @TableField("password")
    private String password;
}
