package com.lh.cloud.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lh.cloud.common.entity.User;
import com.lh.cloud.user.mapper.UserMapper;
import com.lh.cloud.user.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * @date 2022/6/22 17:05
 * @author lh
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService   {
}
