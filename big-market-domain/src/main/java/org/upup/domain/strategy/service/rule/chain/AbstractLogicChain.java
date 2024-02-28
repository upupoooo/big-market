package org.upup.domain.strategy.service.rule.chain;

/**
 * @author upup
 * @description 抽奖策略责任链，判断走那种抽奖策略。如；默认抽象、权重抽奖、黑名单抽奖
 * @date 2024/2/28 16:11
 */
public abstract class AbstractLogicChain implements ILogicChain{

    private ILogicChain next;
    protected abstract String ruleModel();

    @Override
    public ILogicChain next() {
        return next;
    }

    @Override
    public ILogicChain appendNext(ILogicChain next) {
        this.next = next;
        return next;
    }
}
