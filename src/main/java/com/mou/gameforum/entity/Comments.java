package com.mou.gameforum.entity;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mou.gameforum.entity.enums.CommentStatusEnum;;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 评论
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("comments")
@Schema(description = "Comment 评论")
public class Comments {
    Integer id;
    /**
     * 评论者
     */
    @Schema(description = "评论发布者")
    User publisher;
    /**
     * 评论发布时间
     */
    @Schema(description = "评论发布时间")
    Date releaseTime;
    /**
     * 评论内容
     */
    @Schema(description = "评论内容")
    String content;
    /**
     * 子评论
     */
    @Schema(description = "子评论")
    List<Comments> subComment;
    /**
     * 评论状态
     */
    @Schema(description = "评论状态")
    CommentStatusEnum status;
    /**
     * 点赞数
     */
    @Schema(description = "点赞数")
    Integer likes;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
