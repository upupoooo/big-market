package org.upup.domain.strategy.service;

import org.upup.domain.strategy.model.entity.RaffleAwardEntity;
import org.upup.domain.strategy.model.entity.RaffleFactorEntity;

/**
 * @author upup
 * @description 抽奖策略接口
 * @date 2024/2/26 14:26
 */
public interface IRaffleStrategy {
    RaffleAwardEntity performRaffle(RaffleFactorEntity raffleFactorEntity);
}
