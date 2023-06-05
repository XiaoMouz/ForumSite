package com.mou.gameforum.entity;


import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel("Post 文章")
@TableName("posts")
public class Post {
    Integer id;
    /**
     * 发布者
     */
    @ApiModelProperty("文章发布者")
    User publisher;
    /**
     * 发布时间
     */
    @ApiModelProperty("文章发布时间")
    Date releaseTime;
    /**
     * 最后修改时间
     */
    @ApiModelProperty("文章修改时间")
    Date modifyTime;
    /**
     * 标题
     */
    @ApiModelProperty("文章标题")
    String title;
    /**
     * 内容
     */
    @ApiModelProperty("文章内容")
    String content;
    /**
     * 合作者
     */
    @ApiModelProperty("文章合作者发布者")
    User[] collaborator;
    /**
     * 评论
     */
    @ApiModelProperty("文章评论")
    List<Comments> comments;
    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
