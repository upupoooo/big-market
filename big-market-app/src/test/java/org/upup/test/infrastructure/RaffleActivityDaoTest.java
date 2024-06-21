package org.upup.test.infrastructure;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.upup.infrastructure.persistent.dao.IRaffleActivityDao;
import org.upup.infrastructure.persistent.po.RaffleActivity;

import javax.annotation.Resource;

/**
 * @author upup
 * @description
 * @date 2024/6/21 16:53
 */

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RaffleActivityDaoTest {


    @Resource
    private IRaffleActivityDao raffleActivityDao;

    @Test
    public void test_queryRaffleActivityByActivityId(){
        RaffleActivity raffleActivity = raffleActivityDao.queryRaffleActivityByActivityId(100301L);
        log.info("测试结果：{}", JSON.toJSONString(raffleActivity));
    }
}
