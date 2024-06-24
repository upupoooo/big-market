package org.upup.infrastructure.persistent.dao;

import org.apache.ibatis.annotations.Mapper;
import org.upup.infrastructure.persistent.po.RaffleActivityAccount;

/**
 * @author upup
 * @description 抽奖活动账户表
 * @date 2024/3/2 22:48
 */
@Mapper
public interface IRaffleActivityAccountDao {
    void insert(RaffleActivityAccount raffleActivityAccount);

    int updateAccountQuota(RaffleActivityAccount raffleActivityAccount);

}


