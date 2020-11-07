package org.aliuselly.sms.config;

import org.aliuselly.sms.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@EnableWebMvc  // 启动 mvc 注解驱动
@ComponentScan(value = "org.aliuselly.sms", includeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Controller.class)
}, useDefaultFilters = false)
public class WebDispatcherServlet implements WebMvcConfigurer {

    /**
     * 处理静态资源
     * @param configurer
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    /**
     * 配置拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**").excludePathPatterns("/static/h-ui/**", "/static/easyui/**", "/system/login", "/system/getVerifiCodeImage");
    }

    /**
     * 配置文件上传解析器
     * @return
     */
    @Bean
    public CommonsMultipartResolver multipartResolver()
    {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
//        指定请求编码格式
        multipartResolver.setDefaultEncoding("UTF-8");
//        指定允许上传文件的最大值(20MB)
        multipartResolver.setMaxUploadSize(20971520);

        return multipartResolver;
    }

    /**
     * 配置视图解析器
     * @param registry
     */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/WEB-INF/view/", ".jsp");
    }
}
