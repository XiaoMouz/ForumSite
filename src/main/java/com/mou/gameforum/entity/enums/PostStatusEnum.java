package com.mou.gameforum.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.mou.gameforum.entity.Post;

/**
 * 文章状态枚举类
 */
public enum PostStatusEnum {
    /**
     * 已发布
     */
    PUBLISH("publish"),
    /**
     * 草稿
     */
    DRAFT("draft"),
    /**
     * 删除
     */
    DELETED("deleted");

    @EnumValue
    private final String status;

    PostStatusEnum(String status) {
        this.status = status;
    }
}
