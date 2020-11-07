package org.aliuselly.sms.config;

import com.github.pagehelper.PageInterceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@MapperScan("org.aliuselly.sms.dao")  // mapper 接口扫描器
public class MyBatisConfig {

    /**
     * mybatis 配置
     * @param dataSource
     * @return
     */
    @Bean
    public SqlSessionFactoryBean factoryBean(DataSource dataSource)
    {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
//        注入数据源
        factoryBean.setDataSource(dataSource);
//        引入 mybatis 分页插件
        factoryBean.setPlugins(new PageInterceptor());

        return factoryBean;
    }
}
