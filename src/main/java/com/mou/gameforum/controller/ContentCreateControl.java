package com.mou.gameforum.controller;

import com.mou.gameforum.entity.Post;
import com.mou.gameforum.entity.Section;
import com.mou.gameforum.entity.User;
import com.mou.gameforum.entity.dto.PostDto;
import com.mou.gameforum.entity.vo.RequestResult;
import com.mou.gameforum.mapper.content.PostMapper;
import com.mou.gameforum.mapper.content.SectionMapper;
import com.mou.gameforum.service.content.PostService;
import com.mou.gameforum.service.content.SectionService;
import com.mou.gameforum.utils.ResponseUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;

@Controller
public class ContentCreateControl {


    @Resource
    SectionService sectionService;

    @Resource
    PostService postService;

    @GetMapping("/content/create")
    public ModelAndView create(){
        ModelAndView modelAndView = new ModelAndView("content/create/post");
        modelAndView.addObject("sections",sectionService.list());
        return modelAndView;
    }

    @PostMapping("/content/create")
    public String createPost(@RequestBody PostDto postDto, HttpServletResponse response, HttpSession session) throws IOException {
        User user = (User) session.getAttribute("user");
        postDto.setPublisher(user);
        postService.addPost(postDto);
        ResponseUtils.responseJson(response,new RequestResult<>(200,"发布成功",null));
        return null;
    }
    @GetMapping("/content/edit/{postId}")
    public ModelAndView editPost(@PathVariable Integer postId,HttpSession session){
        User user = (User) session.getAttribute("user");
        ModelAndView modelAndView = new ModelAndView("content/create/post");
        modelAndView.addObject("sections",sectionService.list());
        Post post =postService.getPostById(postId);
        if(Objects.equals(post.getPublisher().getId(), user.getId())){
            modelAndView.addObject("post",post);
        }else{
            return new ModelAndView("redirect:/");
        }
        return modelAndView;
    }

    @PutMapping("/content/edit/{postId}")
    public String updatePost(@PathVariable Integer postId,
                             @RequestBody PostDto postDto,
                             HttpSession session,
                             HttpServletResponse response) throws IOException {
        User user = (User) session.getAttribute("user");
        Post post =postService.getPostById(postId);
        if(post==null){
            ResponseUtils.responseJson(response,new RequestResult<>(404,"NotFound",null));
            return null;
        }
        if(!Objects.equals(post.getPublisher().getId(), user.getId())) {
            ResponseUtils.responseJson(response, new RequestResult<>(403, "For", null));
            return null;
        }
        post.setContent(postDto.getContent());
        post.setTitle(postDto.getTitle());
        postService.updateById(post);
        return null;
    }
    @GetMapping("/content/delete/{postId}")
    public ModelAndView deletePost(@RequestParam Boolean delete,@PathVariable Integer postId, HttpSession session){
        Post post =postService.getPostById(postId);
        User user = (User) session.getAttribute("user");
        if(!delete){
            return new ModelAndView("redirect:/");
        }
        if(Objects.equals(post.getPublisher().getId(), user.getId())){
            postService.markDelete(post);
        }
        return new ModelAndView("redirect:/");
    }
}
