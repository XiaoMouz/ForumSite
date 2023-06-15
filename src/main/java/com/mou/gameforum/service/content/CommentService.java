package com.mou.gameforum.service.content;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mou.gameforum.entity.Comments;

import java.util.List;

public interface CommentService extends IService<Comments> {
    List<Comments> getCommentsByPostId(Integer postId, Integer page);
    int getTotalPageByPostId(Integer postId);
}
