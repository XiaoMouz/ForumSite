package com.mou.gameforum.entity;


import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 帖子
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("posts")
public class Post {
    Integer id;
    /**
     * 版块
     */
    Section section;
    /**
     * 发布者
     */
    User publisher;
    /**
     * 发布时间
     */
    Date releaseTime;
    /**
     * 最后修改时间
     */
    Date modifyTime;
    /**
     * 标题
     */
    String title;
    /**
     * 内容
     */
    String content;
    /**
     * 合作者
     */
    User[] collaborator;
    /**
     * 评论
     */
    Comments[] comments;
    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
