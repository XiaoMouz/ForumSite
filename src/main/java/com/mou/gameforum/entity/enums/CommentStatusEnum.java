package com.mou.gameforum.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * 评论状态枚举类
 */
public enum CommentStatusEnum {
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
    private String status;

    CommentStatusEnum(String status) {
    }
}
