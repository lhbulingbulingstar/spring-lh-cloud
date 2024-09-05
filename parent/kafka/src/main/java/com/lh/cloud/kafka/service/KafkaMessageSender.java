package com.lh.cloud.kafka.service;

import com.lh.cloud.kafka.config.KafkaProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


/**
 * kafka消息生产者
 * @author lh
 */
@Slf4j
@Component
public class KafkaMessageSender {
    @Autowired
    private KafkaProperties kafkaProperties;
    protected final Properties properties=new Properties();
    protected KafkaProducer<String, String> kafkaProducer;

    @PostConstruct
    private void init(){
        //kafka ip
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,kafkaProperties.getIp());
        //ack机制
        properties.put(ProducerConfig.ACKS_CONFIG,kafkaProperties.getAck());
        //重试次数
        properties.put(ProducerConfig.RETRIES_CONFIG,kafkaProperties.getRetries());
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
    }

    /**
     * 同步发送消息无返回
     * @param topic 主题
     * @param key 分区
     * @param message 消息
     */
    public void send(String topic,String key,String message){
        kafkaProducer.send(new ProducerRecord<String,String>(topic,key,message));
    }

    /**
     * 同步发送消息有返回
     * @param topic 主题
     * @param key 分区
     * @param message 消息
     * @return RecordMetadata
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public RecordMetadata sendSync(String topic, String key, String message) throws ExecutionException, InterruptedException {
        Future<RecordMetadata> send = kafkaProducer.send(new ProducerRecord<String, String>(topic, key, message));
        RecordMetadata recordMetadata = send.get();
        return recordMetadata;
    }

    /**
     * 异步发送消息
     * @param topic 主题
     * @param key 分区
     * @param message 消息
     * @param callback 异步操作
     */
    public void sendAsync(String topic, String key, String message,Callback callback){
       kafkaProducer.send(new ProducerRecord<String, String>(topic, key, message),callback);
    }

}