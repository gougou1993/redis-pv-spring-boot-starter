package com.zhong.redispv.handler;

import com.zhong.redispv.properties.RedisPVProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/***
 * RedisPVHandlerInterceptor
 */
public class RedisPVHandlerInterceptor implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(RedisPVHandlerInterceptor.class);

    private static final String PV = "pv";

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisPVProperties  redisPVProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (handler != null) {
            final String CUR_DATE = LocalDateTime.now().format(DateTimeFormatter.ofPattern(redisPVProperties.getDateFormat()));
            final String PV_KEY = redisPVProperties.getPrefix() + ":" + CUR_DATE + ":" + request.getRequestURI();
            redisTemplate.opsForZSet().incrementScore(redisPVProperties.getPrefix()+ ":" + CUR_DATE, request.getRequestURI(), 1);//zset
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {


    }
}
