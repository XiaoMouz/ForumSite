package com.mou.gameforum.entity;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mou.gameforum.entity.enums.CommentStatusEnum;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 评论
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("comments")
@ApiModel("Comment 评论")
public class Comments {
    Integer id;
    /**
     * 评论者
     */
    User publisher;
    /**
     * 评论发布时间
     */
    Date releaseTime;
    /**
     * 评论内容
     */
    String content;
    /**
     * 子评论
     */
    Comments[] subComment;
    /**
     * 评论状态
     */
    CommentStatusEnum status;
    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
