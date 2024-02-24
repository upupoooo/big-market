package org.upup.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RMap;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.upup.infrastructure.persistent.redis.IRedisService;

import javax.annotation.Resource;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiTest {

    @Resource
    private IRedisService redisService;

    @Test
    public void test() {
        RMap<Object, Object> map = redisService.getMap("strategy_id_10001");

        map.put(1,101);
        map.put(2,101);
        map.put(3,101);
        map.put(4,102);
        map.put(5,103);
        map.put(6,104);
        map.put(7,105);
        map.put(8,106);
        map.put(9,107);
        map.put(10,101);
        log.info("测试结果：{}",redisService.getFromMap("strategy_id_10001",1).toString());
    }

}
