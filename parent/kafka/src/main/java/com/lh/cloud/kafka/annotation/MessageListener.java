package com.lh.cloud.kafka.annotation;

import java.lang.annotation.*;

/**
 * @author lh
 * @date 2022/11/4 17:12
 * @copyright 六花
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface MessageListener {
    /**
     * Message 所属的 Topic
     *
     * @return String
     */
    String topic() default "";
    /**
     * Message 消费组
     *
     * @return String
     */
    String groupId() default "";
}
