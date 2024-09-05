package com.lh.cloud.kafka.service;

import com.lh.cloud.framework.uitl.SpringUtil;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * @author: lh
 * @date: 2022/10/31 16:09
 * @copyright 六花
 */

public abstract class ConsumerOperation {
    /**
     * 信息操作方法
     *
     * @param message 消息体列表
     * @return 是否操作成功
     */
    public abstract Boolean operation(List<String> message);
    /**
     * 异步执行
     * @author lh
     * @date 2022/11/4 18:04
     * @copyright 六花
     * @param message 消息体列表
     * @return null
     **/
    public void operationAsync(List<String> message) {
        ThreadPoolTaskExecutor lhThreadPool =  SpringUtil.getBean(ThreadPoolTaskExecutor.class);
        //异步执行
        lhThreadPool.execute(() -> {
            operation(message);
        });
    }
}
