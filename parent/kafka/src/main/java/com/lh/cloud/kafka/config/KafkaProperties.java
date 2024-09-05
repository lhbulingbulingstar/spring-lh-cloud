package com.lh.cloud.kafka.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;


/**
 * @author: lh
 * @date: 2022/10/31 10:54
 * @copyright 六花
 */
@Data
@ConfigurationProperties(prefix = "lh.kafka")
@EnableConfigurationProperties(KafkaProperties.class)
@Configuration
public class KafkaProperties {
    /**
     * ip+port,ip+port kafka集群
     */
    private String ip;
    /**
     * 0 - 只管发送不管是否存储
     * 1 - 只管发送之后存储到磁盘了 不管是否同步
     * all - 发送之后存储磁盘等待同步完成之后才会成功
     */
    private String ack;
    /**
     * 重试次数
     */
    private String retries;
    /**
     * 消息是否自动确认 true false
     */
    private String autoCommit;
    /**
     * earliest-当各分区下有已提交的offset时，从提交的offset开始消费；无提交的offset时，从头开始消费
     * latest-当各分区下有已提交的offset时，从提交的offset开始消费；无提交的offset时，消费新产生的该分区下的数据
     * none-topic各分区都存在已提交的offset时，从offset后开始消费；只要有一个分区不存在已提交的offset，则抛出异常
     */
    private String reset;
    /**
     * 一次最多消费的数量
     */
    private Integer maxPollRecords;
    /**
     * 拉取信息超时时常(毫秒)
     */
    private Integer pollTimeOut;
    /**
     * 拉取信息间隔时长(毫秒)
     */
    private Integer pollIntervalTime;
    /**
     * 消费组(同组不能重复消费)
     */
    private String groupId;

}
