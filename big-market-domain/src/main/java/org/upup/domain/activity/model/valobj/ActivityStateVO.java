package org.upup.domain.activity.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author upup
 * @description 活动状态值对象
 * @date 2024/6/21 18:38
 */
@Getter
@AllArgsConstructor
public enum ActivityStateVO {

    create("create", "创建"),
    open("open", "开启"),
    close("close", "关闭"),
    ;


    private final String code;
    private final String desc;

}

