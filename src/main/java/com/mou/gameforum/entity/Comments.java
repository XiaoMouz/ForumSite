package com.mou.gameforum.entity;

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
public class Comments {
    Integer id;
    User publisher;
    String content;
    Comments subComment;
    CommentStatusEnum status;
}
