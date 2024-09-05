package com.lh.cloud.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Date: 2022/7/28 16:54
 * @author lh
 */

@Data
@ConfigurationProperties(prefix = "filter")
@EnableConfigurationProperties(FilterProperties.class)
@Configuration
public class FilterProperties {

    private List<String> allowPaths;

}

