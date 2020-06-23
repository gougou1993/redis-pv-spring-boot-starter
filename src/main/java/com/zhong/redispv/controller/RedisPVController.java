package com.zhong.redispv.controller;

import com.zhong.redispv.properties.RedisPVProperties;
import com.zhong.redispv.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/pv")
public class RedisPVController {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 前十不带score
     * @param key
     * @return
     */
    @GetMapping("/top_10/{key}")
    public ResultVO top10(@PathVariable String key){
        final long start = 0;
        final long end  = 10;
        Set set = redisTemplate.opsForZSet().reverseRange(key, start, end);
        return ResultVO.success(set);
    }

    /**
     * 前十带score
     * @param key
     * @return
     */
    @GetMapping("/top_10_ws/{key}")
    public ResultVO top10_withscores(@PathVariable String key){
        final long start = 0;
        final long end  = 10;
        Set set = redisTemplate.opsForZSet().reverseRangeWithScores(key, start, end);
        return ResultVO.success(set);
    }
}
