package com.lh.cloud.user.service;

import com.lh.cloud.kafka.service.ConsumerOperation;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: lh
 * @date: 2022/11/1 9:16
 * @copyright 六花
 */

@Service
public class TestService extends ConsumerOperation {
    @Override
    public Boolean operation(List<String> list) {
        if (list != null) {
            list.forEach(item -> {
                System.out.println("测试消息：" + item);
            });
        }
        return true;
    }
}
