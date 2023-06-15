package com.mou.gameforum.controller;

import com.mou.gameforum.mapper.user.UserMapper;
import com.mou.gameforum.service.content.CommentService;
import com.mou.gameforum.service.content.PostService;
import com.mou.gameforum.service.content.SectionService;
import com.mou.gameforum.service.content.ZoneService;
import com.mou.gameforum.service.user.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

@Controller("/")
public class IndexControl {
    @Resource
    UserService userService;

    @Resource
    SectionService sectionService;

    @Resource
    PostService postService;

    @Resource
    CommentService commentService;

    @Resource
    ZoneService zoneService;

    @RequestMapping("/")
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("zones",zoneService.getZoneList());
        return modelAndView;
    }

    @GetMapping("/site/{zoneId}/{sectionId}")
    public ModelAndView section(@PathVariable("zoneId") Integer zoneId,
                                 @PathVariable("sectionId") Integer sectionId,
                                 @RequestParam(value = "page",defaultValue = "1") Integer page){
        ModelAndView modelAndView = new ModelAndView("content/section");
        modelAndView.addObject("zone",zoneService.getById(zoneId));
        modelAndView.addObject("section",sectionService.getById(sectionId));
        modelAndView.addObject("posts",postService.getPostListBySectionId(sectionId,page));
        modelAndView.addObject("page",page);
        modelAndView.addObject("totalPage",postService.getTotalPageBySectionId(sectionId));
        return modelAndView;
    }

    @GetMapping("/site/{zoneId}/{sectionId}/{postId}")
    public ModelAndView post(@PathVariable("zoneId") Integer zoneId,
                              @PathVariable("sectionId") Integer sectionId,
                              @PathVariable("postId") Integer postId,
                             @RequestParam(value = "commentPage",defaultValue = "1") Integer commentPage) {
        ModelAndView modelAndView = new ModelAndView("content/post");
        modelAndView.addObject("zone", zoneService.getById(zoneId));
        modelAndView.addObject("section", sectionService.getById(sectionId));
        modelAndView.addObject("post", postService.getPostById(postId));
        modelAndView.addObject("commentPage", commentPage);
        modelAndView.addObject("totalCommentPage", commentService.getTotalPageByPostId(postId));
        modelAndView.addObject("comments", commentService.getCommentsByPostId(postId, commentPage));
        return modelAndView;
    }

    @RequestMapping("/debug")
    public ModelAndView debug(){
        throw new NullPointerException();
    }
}
