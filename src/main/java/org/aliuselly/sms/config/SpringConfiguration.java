package org.aliuselly.sms.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

@Configuration
@PropertySource("classpath:c3p0.properties")
@EnableTransactionManagement  // 开启事务注解扫描
@Import(MyBatisConfig.class)  // mybatis 与 spring 整合
@ComponentScan(value = "org.aliuselly.sms", excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class})
})
public class SpringConfiguration {

    @Value("${c3p0.driverClass}")
    private String driverClass;
    @Value("${c3p0.jdbcUrl}")
    private String jdbcUrl;
    @Value("${c3p0.user}")
    private String user;
    @Value("${c3p0.password}")
    private String password;
    @Value("${c3p0.maxPoolSize}")
    private String maxPoolSize;
    @Value("${c3p0.minPoolSize}")
    private String minPoolSize;
    @Value("${c3p0.initialPoolSize}")
    private String initialPoolSize;
    @Value("${c3p0.maxIdleTime}")
    private String maxIdleTime;

    /**
     * 配置数据源
     * @return
     * @throws PropertyVetoException
     */
    @Bean
    public DataSource dataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
//        数据库驱动
        dataSource.setDriverClass(driverClass);
//        数据库 url
        dataSource.setJdbcUrl(jdbcUrl);
//        用户名
        dataSource.setUser(user);
//        密码
        dataSource.setPassword(password);
//        最大连接数
        dataSource.setMaxPoolSize(Integer.parseInt(maxPoolSize));
//        最小连接数
        dataSource.setMinPoolSize(Integer.parseInt(minPoolSize));
//        初始化连接数
        dataSource.setInitialPoolSize(Integer.parseInt(initialPoolSize));
//        连接生存时间
        dataSource.setMaxIdleTime(Integer.parseInt(maxIdleTime));

        return dataSource;
    }

    /**
     * spring 事务管理器
     * @return
     */
    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource)
    {
//        控制数据源
        return new DataSourceTransactionManager(dataSource);
    }
}
