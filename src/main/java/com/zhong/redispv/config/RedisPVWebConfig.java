package com.zhong.redispv.config;

import com.zhong.redispv.controller.RedisPVController;
import com.zhong.redispv.handler.RedisPVHandlerInterceptor;
import com.zhong.redispv.properties.RedisPVProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.Serializable;

@Configuration
@ConditionalOnClass(RedisPVHandlerInterceptor.class)
@EnableConfigurationProperties(RedisPVProperties.class)
@ConditionalOnProperty(
        prefix = "pv",
        name = "isopen",
        havingValue = "true"
)
public class RedisPVWebConfig implements WebMvcConfigurer {

    @Bean(name = "redisTemplate")
    @ConditionalOnClass(value = {RedisTemplate.class,LettuceConnectionFactory.class})
    @ConditionalOnMissingBean(RedisTemplate.class)
    RedisTemplate<String, Serializable> redisTemplate(LettuceConnectionFactory connectionFactory){
        RedisTemplate<String, Serializable> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setConnectionFactory(connectionFactory);
        return  redisTemplate;

    }

    @Bean(name = "redisPVHandlerInterceptor")
    @ConditionalOnMissingBean(RedisPVHandlerInterceptor.class)
    @ConditionalOnProperty(
            prefix = "pv",
            name = "isopen",
            havingValue = "true"
    )
    public RedisPVHandlerInterceptor redisPVHandlerInterceptor(){ // HandlerInterceptor中注入service
        return new RedisPVHandlerInterceptor();
    }

    @Bean
    @ConditionalOnMissingBean(RedisPVController.class)
    @ConditionalOnProperty(
            prefix = "pv",
            name = "api",
            havingValue = "true"
    )
    RedisPVController redisPVController(){
        return new RedisPVController();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(redisPVHandlerInterceptor()).addPathPatterns("/**");//拦截所有
    }

}
