package org.upup.domain.strategy.service.rule.chain;

/**
 * @author upup
 * @description 责任链装配
 * @date 2024/2/28 16:10
 */
public interface ILogicChainArmory {
    ILogicChain next();

    ILogicChain appendNext(ILogicChain next);

}
