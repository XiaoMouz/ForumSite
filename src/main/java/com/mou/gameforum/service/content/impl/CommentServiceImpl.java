package com.mou.gameforum.service.content.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mou.gameforum.entity.Comments;
import com.mou.gameforum.entity.Post;
import com.mou.gameforum.entity.enums.PostStatusEnum;
import com.mou.gameforum.mapper.content.CommentMapper;
import com.mou.gameforum.service.content.CommentService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl
        extends ServiceImpl<CommentMapper, Comments>
        implements CommentService {

    @Resource
    CommentMapper commentMapper;

    @Override
    public List<Comments> getCommentsByPostId(Integer postId, Integer page) {
        QueryWrapper<Comments> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pid",postId);
        queryWrapper.eq("status", PostStatusEnum.PUBLISH);
        Page<Comments> p = new Page<>(page,10);
        List<Comments> comments = commentMapper.selectPage(p, queryWrapper).getRecords();
        comments.forEach(comment -> {
            comment.setUid(commentMapper.getCommentPublisher(comment.getId()));
        });
        return comments;
    }

    public int getTotalPageByPostId(Integer postId){
        QueryWrapper<Comments> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pid",postId);
        queryWrapper.eq("status", PostStatusEnum.PUBLISH);
        int total = Math.toIntExact(commentMapper.selectCount(queryWrapper));
        return total%10==0?total/10:total/10+1;
    }

    public Comments getSubCommentByIdPage(Comments comments,Integer page){
        QueryWrapper<Comments> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pid",comments.getId());
        queryWrapper.eq("fid",comments.getId());
        queryWrapper.eq("status", PostStatusEnum.PUBLISH);
        Page<Comments> p = new Page<>(page,10);
        List<Comments> subComments = commentMapper.selectPage(p, queryWrapper).getRecords();
        subComments.forEach(subComment -> {
            subComment.setUid(commentMapper.getCommentPublisher(subComment.getId()));
        });
        comments.setSubComment(subComments);
        return comments;
    }
}
