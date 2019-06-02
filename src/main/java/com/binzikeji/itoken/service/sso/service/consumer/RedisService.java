package com.binzikeji.itoken.service.sso.service.consumer;

import com.binzikeji.itoken.service.sso.service.consumer.fallback.RedisServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description
 * @Author Bin
 * @Date 2019/4/16 15:38
 **/
@FeignClient(value = "itoken-service-redis", fallback = RedisServiceFallback.class)
public interface RedisService {

    @RequestMapping(value = "put", method = RequestMethod.GET)
    String put (@RequestParam(value = "key") String key, @RequestParam(value = "value") String value, @RequestParam(value = "seconds") long seconds);

    @RequestMapping(value = "get", method = RequestMethod.GET)
    String get(@RequestParam(value = "key") String key);
}
