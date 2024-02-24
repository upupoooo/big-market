package org.upup.test.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.upup.domain.strategy.service.armory.IStrategyArmory;

import javax.annotation.Resource;

/**
 * @author upup
 * @description
 * @date 2024/2/24 22:35
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class IStrategyArmoryTest {

    @Resource
    private IStrategyArmory strategyArmory;

    @Test
    public void test_assembleLotteryStrategy(){
        strategyArmory.assembleLotteryStrategy(100001L);
    }

    @Test
    public void text_getRandomAwardId(){
        log.info("测试结果:{} 奖品ID值",strategyArmory.getRandomAwardId(100001L));
        log.info("测试结果:{} 奖品ID值",strategyArmory.getRandomAwardId(100001L));
        log.info("测试结果:{} 奖品ID值",strategyArmory.getRandomAwardId(100001L));
    }
}
