package org.upup.test.infrastructure;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.upup.infrastructure.persistent.dao.IAwardDao;
import org.upup.infrastructure.persistent.po.Award;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author upup
 * @description 奖品持久化单元测试
 * @date 2024/2/24 02:09
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class AwardDaoTest {

    @Resource
    private IAwardDao iAwardDao;


    @Test
    public void test_queryAwardList(){
        List<Award> awards = iAwardDao.queryAwardList();

        log.info(JSONObject.toJSONString(awards));
    }
}
