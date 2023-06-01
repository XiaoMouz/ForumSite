package com.mou.gameforum.entity;


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
}
