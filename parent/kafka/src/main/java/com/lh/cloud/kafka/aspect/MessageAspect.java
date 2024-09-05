package com.lh.cloud.kafka.aspect;

import com.alibaba.fastjson.JSONObject;
import com.lh.cloud.kafka.annotation.CommonMessage;
import com.lh.cloud.kafka.service.KafkaMessageSender;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author lh
 * @date 2024/4/15 16:44
 * @copyright 六花
 */

@Aspect
@Component
@Order(2)
@Slf4j
public class MessageAspect {
    @Autowired
    private KafkaMessageSender kafkaMessageSender;

    /**
     * 编织切入点
     */
    @Pointcut("@annotation(CommonMessage)")
    public void messageAnnotationPointcut() {

    }

    /**
     * 方法执行之后执行
     *
     * @param joinPoint
     */
    @AfterReturning(returning = "message", value = "messageAnnotationPointcut()")
    public void afterReturn(JoinPoint joinPoint, Object message) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        CommonMessage annotation = signature.getMethod().getAnnotation(CommonMessage.class);
        String topic = annotation.topic();
        String key = annotation.key();
        if (message != null) {
            try {
                String messageStr = message.toString();
                kafkaMessageSender.send(topic, key, messageStr);
            } catch (Exception e) {
                log.error("发送消息出现异常");
            }
        }
    }
}
