package com.mou.gameforum.entity;


import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mou.gameforum.entity.enums.PostStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 帖子
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Post 文章")
@TableName("posts")
public class Post {
    Integer id;
    /**
     * 发布者
     */
    @Schema(description = "文章发布者")
    @TableField(exist = false)
    User publisher;
    /**
     * 发布时间
     */
    @Schema(description = "文章发布时间")
    Date releaseTime;
    /**
     * 最后修改时间
     */
    @Schema(description = "文章修改时间")
    Date modifyTime;
    /**
     * 文章封面
     */
    @Schema(description = "文章封面")
    String headImage;
    /**
     * 标题
     */
    @Schema(description = "文章标题")
    String title;
    /**
     * 内容
     */
    @Schema(description = "文章内容")
    String content;
    /**
     * 合作者
     */
    @Schema(description = "文章合作者发布者")
    @TableField(exist = false)
    List<User> collaborator;
    /**
     * 评论
     */
    @Schema(description = "文章评论")
    @TableField(exist = false)
    List<Comments> comments;
    /**
     * 观看量
     */
    @Schema(description = "观看量")
    Integer views;
    /**
     * 评论数
     */
    @Schema(description = "评论数")
    @TableField("comts")
    Integer commentsCount;
    /**
     * 点赞数
     */
    @Schema(description = "点赞数")
    Integer likes;

    @JsonIgnore
    Integer sid;
    /**
     * 文章状态
     */
    @Schema(description = "文章状态")
    PostStatusEnum status;
    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
