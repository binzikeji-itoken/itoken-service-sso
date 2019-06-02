package com.binzikeji.itoken.service.sso.service.consumer.fallback;

import com.binzikeji.itoken.service.sso.service.consumer.RedisService;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author Bin
 * @Date 2019/4/16 15:45
 **/
@Component
public class RedisServiceFallback implements RedisService {

    @Override
    public String put(String key, String value, long seconds) {
        return null;
    }

    @Override
    public String get(String key) {
        return null;
    }
}
