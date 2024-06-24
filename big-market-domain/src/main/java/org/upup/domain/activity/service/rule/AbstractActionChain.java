package org.upup.domain.activity.service.rule;

/**
 * @author upup
 * @description 下单规则责任链抽象类
 * @date 2024/6/24 16:11
 */
public abstract class AbstractActionChain implements IActionChain {

    private IActionChain next;

    @Override
    public IActionChain next() {
        return next;
    }

    @Override
    public IActionChain appendNext(IActionChain next) {
        this.next = next;
        return next;
    }
}
