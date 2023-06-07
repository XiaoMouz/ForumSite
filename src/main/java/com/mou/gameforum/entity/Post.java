package com.mou.gameforum.entity;


import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.annotation.TableName;
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
    List<User> collaborator;
    /**
     * 评论
     */
    @Schema(description = "文章评论")
    List<Comments> comments;
    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
