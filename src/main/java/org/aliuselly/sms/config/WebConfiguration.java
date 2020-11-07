package org.aliuselly.sms.config;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

public class WebConfiguration extends AbstractAnnotationConfigDispatcherServletInitializer {

    /**
     * 启动 spring
     * @return
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{SpringConfiguration.class};
    }

    /**
     * 启动 spring mvc
     * @return
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebDispatcherServlet.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    /**
     * 配置 spring mvc 编码过滤器
     * @return
     */
    @Override
    protected Filter[] getServletFilters() {
        return new Filter[]{new CharacterEncodingFilter("UTF-8", true)};
    }

    /**
     * 配置 spring 文件的监听器
     *
     * 这个是监听 spring 配置文件的，咱们是纯注解的，就不要配置了，因为呢
     * 没有 applicationContext 文件会启动不起来的
     * @param servletContext
     */
    /*@Override
    protected void registerContextLoaderListener(ServletContext servletContext) {
        super.registerContextLoaderListener(servletContext);
//        servletContext.addListener(ContextLoaderListener.class);
    }*/
}
