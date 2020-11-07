package org.aliuselly.sms.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {

    /**
     * 该方法会在控制器方法前执行，其返回值表示是否中断后续操作
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        获取请求的 uri
        String url = request.getRequestURI();
//        判断用户是否已登录
        if(request.getSession().getAttribute("userInfo") != null)
        {
            return true;
        }
//        用户未登录，拦截其请求并将其转发到用户登录页面
        request.getRequestDispatcher("/WEB-INF/view/system/login.jsp").forward(request, response);
        return false;
    }

    /**
     * 该方法会在控制器方法调用之后，且视图解析器之前调用
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 该方法会在整个请求完成，即视图渲染结束后执行
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
