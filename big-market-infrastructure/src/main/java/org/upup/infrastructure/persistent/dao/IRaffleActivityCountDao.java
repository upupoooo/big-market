package org.upup.infrastructure.persistent.dao;

import org.apache.ibatis.annotations.Mapper;
import org.upup.infrastructure.persistent.po.RaffleActivityCount;

/**
 * @author upup
 * @description 抽奖活动次数配置表Dao
 * @date 2024/3/2 22:48
 */
@Mapper
public interface IRaffleActivityCountDao {
    RaffleActivityCount queryRaffleActivityCountByActivityCountId(Long activityCountId);
}




