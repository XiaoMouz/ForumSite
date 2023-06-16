package com.mou.gameforum.service.content.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mou.gameforum.entity.Comments;
import com.mou.gameforum.entity.Levels;
import com.mou.gameforum.entity.Post;
import com.mou.gameforum.entity.User;
import com.mou.gameforum.entity.enums.PostStatusEnum;
import com.mou.gameforum.mapper.content.PostMapper;
import com.mou.gameforum.mapper.levels.LevelsMapper;
import com.mou.gameforum.service.content.CommentService;
import com.mou.gameforum.service.content.PostService;
import com.mou.gameforum.service.user.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl
        extends ServiceImpl<PostMapper, Post>
        implements PostService {

    @Resource
    LevelsMapper levelsMapper;

    @Resource
    PostMapper postMapper;

    @Resource
    CommentService commentService;

    @Resource
    UserService userService;

    public List<Post> getPostListBySectionId(Integer sectionId, Integer page){
        QueryWrapper<Post> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sid",sectionId);
        queryWrapper.eq("status", PostStatusEnum.PUBLISH);
        Page<Post> p = new Page<>(page,10);
        List<Post> posts = postMapper.selectPage(p, queryWrapper).getRecords();

        return posts;
    }

    public Integer getTotalPageBySectionId(Integer sectionId){
        QueryWrapper<Post> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sid",sectionId);
        queryWrapper.eq("status", PostStatusEnum.PUBLISH);
        int total = Math.toIntExact(postMapper.selectCount(queryWrapper));
        return total%10==0?total/10:total/10+1;
    }

    public Post getPostById(Integer id){
        Post post = postMapper.selectById(id);
        post = getPostPublisherAndCollComment(post);
        return post;
    }

    private Post getPostPublisherAndCollComment(Post post){
        //post.setCollaborator(userService.getUserById(post.getCollaboratorId()));
        post.setPublisher(postMapper.getPostPublisher(post.getId()));
        post.setPublisher(setUserLevels(post.getPublisher()));
        post.setComments(commentService.getCommentsByPostId(post.getId(),1));
        return post;
    }

    private User setUserLevels(User user) {
        if(user!=null){
            ArrayList<Levels> list = new ArrayList<>();
            levelsMapper.getUserLevels(user).forEach(
                    u -> list.add(levelsMapper.selectById(u.getId()))
            );
            user.setLevels(list);
        }
        return user;
    }

    @Override
    public List<Post> getPostListByKeyword(String keyword) {
        QueryWrapper<Post> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("content",keyword);
        queryWrapper.eq("status", PostStatusEnum.PUBLISH);
        List<Post> posts = postMapper.selectList(queryWrapper);
        posts.forEach(post -> {
            post.setPublisher(postMapper.getPostPublisher(post.getId()));
            post.setPublisher(setUserLevels(post.getPublisher()));
        });
        queryWrapper = new QueryWrapper<>();
        queryWrapper.like("title",keyword);
        queryWrapper.eq("status", PostStatusEnum.PUBLISH);
        posts.addAll(postMapper.selectList(queryWrapper));
        return posts;
    }
}
