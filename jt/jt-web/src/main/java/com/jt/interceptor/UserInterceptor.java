package com.jt.interceptor;

import com.jt.pojo.User;
import com.jt.util.ObjectMapperUtil;
import com.jt.util.UserThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 核心:获取用户的访问数据/数据 request / response
 */
@Component
public class UserInterceptor implements HandlerInterceptor {
    @Autowired
    private JedisCluster jedisCluster;
    //完成用户校验,如果没有登录,则跳转用户登录页面
    //如果用户已经登录则放行
    //boolean : true 放行  false : 拦截 +重定向

    /**
     * 用户拦截器实现步骤:
     * 1.首先获取用户的cookie数据
     * 2.判断用户是否已经登录.如果用户没有登录,则重定向用户登录页面.
     * 如果用户已经登录,则判断Redis中是否有数据.
     *              有数据:表示用户之前登录成功 予以放行
     *              没有数据: 表示用户登陆失败.之后重定向到登录页面.
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = null;
        Cookie[] cookies =
                request.getCookies();
        for (Cookie cookie:cookies
             ) {
            if ("JT_TICKET".equalsIgnoreCase(cookie.getName())) {
                token = cookie.getValue();
                break;
            }
        }
        if (!StringUtils.isEmpty(token)) {
            //判断redis中有数据
            String userJson = jedisCluster.get(token);
            if (!StringUtils.isEmpty(userJson)) {
                //程序执行到这里表示用户已经登录
                User user = ObjectMapperUtil.toObject(userJson, User.class);
                UserThreadLocal.set(user);
                System.out.println("拦截器启动!!!!!!!将数据传入线程ThreadLocal");
                return true;
            }
        }
        //如果执行到这里,表示用户没有登陆
        response.sendRedirect("/user/login.html");
        return false;//
    }



    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {


    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserThreadLocal.remove();
        System.out.println("用户本次调用完成,清除Threadlocal数据");
    }
}
