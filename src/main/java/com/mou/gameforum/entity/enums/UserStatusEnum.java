package com.mou.gameforum.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

public enum UserStatusEnum {
    /**
     * 激活状态
     */
    ACTIVE("active"),
    /**
     * 等待验证
     */
    PENDING("pending"),
    /**
     * 封禁状态
     */
    BANNED("banned");
    
    @EnumValue
    private final String status;

    UserStatusEnum(String status) {
        this.status=status;
    }
}
