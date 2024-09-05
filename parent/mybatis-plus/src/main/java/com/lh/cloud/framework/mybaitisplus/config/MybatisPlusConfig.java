package com.lh.cloud.framework.mybaitisplus.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import com.lh.cloud.framework.redis.util.UserProvider;
import com.lh.cloud.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author: lh
 * @date: 2022/9/30 15:11
 *
 */
@Slf4j
@Configuration
@AllArgsConstructor
public class MybatisPlusConfig {
    private final UserProvider userProvider;
    @Value("mybatis-plus.ignoreTable")
    private List<String> ignoreTable;
    @Bean
    public MetaObjectHandler getMetaObjectHandler() {
        return new MetaObjectHandler() {
            @Override
            public void insertFill(MetaObject metaObject) {
                log.info("进行填充创建时间");
                this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
                User user = userProvider.getUser();
                if (user!=null){
                    this.strictInsertFill(metaObject, "createBy",String.class, user.getId());
                }
            }

            @Override
            public void updateFill(MetaObject metaObject) {
                log.info("进行填充修改时间");
                this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
                User user = userProvider.getUser();
                if (user!=null){
                    this.strictUpdateFill(metaObject, "updateBy",String.class, user.getId());
                }
            }
        };
    }

    @Bean
    public PaginationInnerInterceptor paginationInterceptor() {
        PaginationInnerInterceptor paginationInterceptor = new PaginationInnerInterceptor();
        paginationInterceptor.setOverflow(true);
        return paginationInterceptor;
    }

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.POSTGRE_SQL));
        //行级租户配置
        interceptor.addInnerInterceptor(new TenantLineInnerInterceptor(new TenantLineHandler() {
            @Override
            public Expression getTenantId() {
                return new LongValue(1);
            }

            // 这是 default 方法,默认返回 false 表示所有表都需要拼多租户条件
            @Override
            public boolean ignoreTable(String tableName) {
                if (ignoreTable!=null){
                    for (String table : ignoreTable) {
                        return !table.equalsIgnoreCase(tableName);
                    }
                }
                return true;
            }
        }));

        interceptor.addInnerInterceptor(paginationInterceptor());

        // 如果用了分页插件注意先 add TenantLineInnerInterceptor 再 add PaginationInnerInterceptor
        // 用了分页插件必须设置 MybatisConfiguration#useDeprecatedExecutor = false
        // interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return interceptor;
    }
}
