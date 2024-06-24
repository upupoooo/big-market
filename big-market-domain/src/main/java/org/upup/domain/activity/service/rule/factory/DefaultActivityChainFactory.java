package org.upup.domain.activity.service.rule.factory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.upup.domain.activity.service.rule.IActionChain;

import java.util.Map;

/**
 * @author upup
 * @description 责任链工厂
 * @date 2024/6/24 16:15
 */
@Service
public class DefaultActivityChainFactory {
    /**
     * 1. 通过构造函数注入。
     * 2. Spring 可以自动注入 IActionChain 接口实现类到 map 对象中，key 就是 bean 的名字。
     * 3. 活动下单动作的责任链是固定的，所以直接在构造函数中组装即可。
     */
    private final IActionChain actionChain;

    public DefaultActivityChainFactory(Map<String,IActionChain> actionChainGroupMap) {
        actionChain= actionChainGroupMap.get(ActionModel.activity_base_action.code);
        actionChain.appendNext(actionChainGroupMap.get(ActionModel.activity_sku_stock_action.code));
    }
    public IActionChain openActionChain() {
        return this.actionChain;
    }

    @Getter
    @AllArgsConstructor
    public enum ActionModel {

        activity_base_action("activity_base_action", "活动的库存、时间校验"),
        activity_sku_stock_action("activity_sku_stock_action", "活动sku库存"),
        ;

        private final String code;
        private final String info;

    }

}
