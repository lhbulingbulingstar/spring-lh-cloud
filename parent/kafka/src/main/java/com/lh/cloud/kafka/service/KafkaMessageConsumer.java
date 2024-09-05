package com.lh.cloud.kafka.service;

import com.lh.cloud.kafka.config.KafkaProperties;
import io.seata.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;


/**
 * kafka消息消费者
 *
 * @author lh
 */
@Slf4j
@Component
public class KafkaMessageConsumer {
    @Autowired
    private KafkaProperties kafkaProperties;
    private final Properties properties = new Properties();
    protected KafkaConsumer<String, String> consumer;

    @PostConstruct
    private void init() {
        //kafka ip
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getIp());
        //是否自动确认消费(确认之后同组消费人员不能重复消费)
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, kafkaProperties.getAutoCommit());
        //一次消费数量
        properties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, kafkaProperties.getMaxPollRecords());

        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
    }

    /**
     * 消费消息
     *
     * @param topic             主题
     * @param groupId           消费组
     * @param cycle             是否一直消费
     * @param consumerOperation 消息操作类(保存数据库)
     * @throws InterruptedException
     */
    public void poll(String topic, String groupId, Boolean cycle, ConsumerOperation consumerOperation) throws InterruptedException {
        if (StringUtils.isBlank(groupId)) {
            groupId = kafkaProperties.getGroupId();
        }
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        this.consumer = new KafkaConsumer<>(properties);
        this.consumer.subscribe(Collections.singletonList(topic));
        do {
            //获取消息  超时1秒获取不到数据就断开
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(kafkaProperties.getPollTimeOut()));
            ArrayList<String> messages = new ArrayList<>();
            for (ConsumerRecord<String, String> record : records) {
                messages.add(record.value());
            }
            //执行消息操作(保存数据库)
            Boolean operation = consumerOperation.operation(messages);
            consumerOperation.operationAsync(messages);
            //如果配置为自动提交-不需要自己提交 如果配置为手动提交-判断业务是否处理完毕
            if (operation && String.valueOf(Boolean.FALSE).equalsIgnoreCase(kafkaProperties.getAutoCommit())) {
                //手动修改偏移量 修改该消费组消费的下一条消息位置
                this.consumer.commitSync();
            }
            //如果是循环消费 则中途停顿
            if (cycle) {
                Thread.sleep(kafkaProperties.getPollIntervalTime());
            }
        } while (cycle);
    }

    /**
     * 消费消息(使用配置文件分组)
     *
     * @param topic             主题
     * @param cycle             是否一直消费
     * @param consumerOperation 消息操作类(保存数据库)
     * @throws InterruptedException
     */
    public void poll(String topic, Boolean cycle, ConsumerOperation consumerOperation) throws InterruptedException {
        poll(topic, kafkaProperties.getGroupId(), cycle, consumerOperation);
    }

    /**
     * 消费消息(使用配置文件分组默认不循环)
     *
     * @param topic             主题
     * @param consumerOperation 消息操作类(保存数据库)
     * @throws InterruptedException
     */
    public void poll(String topic, ConsumerOperation consumerOperation) throws InterruptedException {
        poll(topic, kafkaProperties.getGroupId(), consumerOperation);
    }

    /**
     * 消费消息(默认不循环)
     *
     * @param topic             主题
     * @param consumerOperation 消息操作类(保存数据库)
     * @throws InterruptedException
     */
    public void poll(String topic, String groupId, ConsumerOperation consumerOperation) throws InterruptedException {
        poll(topic, groupId, false, consumerOperation);
    }
}