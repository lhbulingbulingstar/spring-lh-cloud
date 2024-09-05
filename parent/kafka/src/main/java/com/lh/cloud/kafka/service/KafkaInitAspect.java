package com.lh.cloud.kafka.service;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author: lh
 * @date: 2022/11/1 13:46
 * @copyright 六花
 */
@Aspect
@Component
@Order(1)
public class KafkaInitAspect {
    /**
     * 编织拉取消息切点
     */
    @Pointcut("execution(* com.lh.cloud.kafka.service.KafkaMessageConsumer.poll*(..))")
    public void pollPointcut() {

    }
    /**
     * 编织发送消息切点
     */
    @Pointcut("execution(* com.lh.cloud.kafka.service.KafkaMessageSender.send*(..))")
    public void sendPointcut() {

    }

    /**
     * 消息发送之前执行
     * @param joinPoint
     */
    @Before(value = "sendPointcut()")
    public void start(JoinPoint joinPoint){
        Object aThis = joinPoint.getTarget();
        KafkaMessageSender sender=(KafkaMessageSender)aThis;
        sender.kafkaProducer=new KafkaProducer<String, String>(sender.properties);
    }

    /**
     * 消息发送之后执行
     * @param joinPoint
     */
    @After(value = "sendPointcut()")
    public void sendClose(JoinPoint joinPoint){
        Object aThis = joinPoint.getTarget();
        KafkaMessageSender sender=(KafkaMessageSender)aThis;
        if (sender.kafkaProducer!=null){
            sender.kafkaProducer.close();
        }
    }

    /**
     * 拉取之后执行
     * @param joinPoint
     */
    @After(value = "pollPointcut()")
    public void pollClose(JoinPoint joinPoint){
        Object aThis = joinPoint.getTarget();
        KafkaMessageConsumer consumer=(KafkaMessageConsumer)aThis;
        if (consumer.consumer!=null){
            consumer.consumer.close();
        }
    }
}
