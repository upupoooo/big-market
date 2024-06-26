package org.upup.domain.strategy.event;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.upup.types.event.BaseEvent;

import java.util.Date;

/**
 * @author upup
 * @description 策略奖品库存清空消息
 * @date 2024/6/25 20:01
 */
@Component
public class RaffleStockZeroMessageEvent extends BaseEvent<String> {

    @Value("${spring.rabbitmq.topic.raffle_stock_zero}")
    private String topic;

    @Override
    public EventMessage<String> buildEventMessage(String key) {
        return EventMessage.<String>builder()
                .id(RandomStringUtils.randomNumeric(11))
                .timestamp(new Date())
                .data(key)
                .build();
    }

    @Override
    public String topic() {
        return topic;
    }

}

