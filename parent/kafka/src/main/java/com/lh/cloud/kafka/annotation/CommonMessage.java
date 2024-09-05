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
public @interface CommonMessage {
    /**
     * Message 所属的 Topic
     *
     * @return String
     */
    String topic() default "";
    /**
     * Message 所属的 分区
     *
     * @return String
     */
    String key() default "";
}
