package com.lh.cloud.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.lh.cloud.common.param.response.Result;
import com.lh.cloud.framework.mybaitisplus.util.controller.BaseController;
import com.lh.cloud.framework.redis.util.UserProvider;
import com.lh.cloud.kafka.service.KafkaMessageConsumer;
import com.lh.cloud.kafka.service.KafkaMessageSender;
import com.lh.cloud.user.api.RoleApi;
import com.lh.cloud.user.api.UserApi;
import com.lh.cloud.user.convert.UserConvert;
import com.lh.cloud.user.domain.User;
import com.lh.cloud.user.domain.param.response.UserVO;
import com.lh.cloud.user.service.IUserService;
import com.lh.cloud.user.service.TestService;
import lombok.AllArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

/**
 * 用户测试controller
 * @author lh
 */
@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/user")
public class UserController extends BaseController implements UserApi {
    private final IUserService userService;
    private final UserConvert userConvert;
    private final UserProvider userProvider;
    private final KafkaMessageSender messageSender;
    private final KafkaMessageConsumer messageConsumer;
    private final RoleApi roleApi;
    private final TestService testService;
    /**
     * 通过用户账户获取用户信息
     * @param account 用户账户
     * @return
     */
    @Override
    @GetMapping("/getUserByAccount")
    public Result<User> getUserByAccount(@RequestParam("account") String account) {
        return Result.success(userService.getOne(new LambdaQueryWrapper<User>().eq(User::getAccount,account)));
    }
    /**
     * 获取用户列表(分页)
     * @param account 用户账户
     * @return
     */
    @GetMapping("/list")
    public Result<User> list() {
        startPage();
        return getDataTable(userService.list());
    }

    /**
     * 获取登录用户
     * @return 登录用户信息
     */
    @GetMapping("/getLoginUser")
    public Result getLoginUser(){
       return Result.success(userProvider.getUser());
    }

    /**
     * 获取登录用户
     * @return 登录用户信息
     */
    @PostMapping("/save")
    public Result save(@RequestBody UserVO userVO){
        User user = userConvert.vo2entity(userVO);
        userService.save(user);
        return Result.success("添加成功");
    }

    /**
     * 测试日志方法
     * @return
     */
    @GetMapping("/log")
    public Result log(){
        log.info("test--------log--------end");
        return Result.success();
    }
    /**
     * 测试kafka发送
     * @return
     */
    @GetMapping("/send")
    public Result send() throws ExecutionException, InterruptedException {
        messageSender.send("test_topic","1","message");
        return Result.success();
    }

    /**
     * 测试kafka拉取
     * @return
     */
    @GetMapping("/get")
    public Result get() throws InterruptedException {
        messageConsumer.poll("test_topic",testService);
        return Result.success();
    }
}