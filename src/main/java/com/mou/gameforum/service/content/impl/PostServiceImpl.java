package com.mou.gameforum.service.content.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mou.gameforum.entity.Comments;
import com.mou.gameforum.entity.Post;
import com.mou.gameforum.entity.User;
import com.mou.gameforum.mapper.content.PostMapper;
import com.mou.gameforum.service.content.CommentService;
import com.mou.gameforum.service.content.PostService;
import com.mou.gameforum.service.user.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl
        extends ServiceImpl<PostMapper, Post>
        implements PostService {

    @Resource
    PostMapper postMapper;

    @Resource
    CommentService commentService;

    @Resource
    UserService userService;

    public List<Post> getPostListBySectionId(Integer sectionId, Integer page){
        QueryWrapper<Post> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("section_id",sectionId);
        Page<Post> p = new Page<>(page,10);
        List<Post> posts = postMapper.selectPage(p, queryWrapper).getRecords();
        return posts;
    }

    public Integer getTotalPageBySectionId(Integer sectionId){
        QueryWrapper<Post> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sid",sectionId);
        int total = Math.toIntExact(postMapper.selectCount(queryWrapper));
        return total%10==0?total/10:total/10+1;
    }
}
