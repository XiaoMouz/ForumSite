package com.mou.gameforum.entity;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mou.gameforum.entity.enums.CommentStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 评论
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("comments")
public class Comments {
    Integer id;
    User publisher;
    String content;
    Comments subComment;
    CommentStatusEnum status;
    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
