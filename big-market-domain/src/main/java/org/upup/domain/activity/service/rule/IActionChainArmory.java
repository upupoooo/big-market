package org.upup.domain.activity.service.rule;

/**
 * @author upup
 * @description
 * @date 2024/6/24 16:12
 */
public interface IActionChainArmory {

    IActionChain next();

    IActionChain appendNext(IActionChain next);

}

