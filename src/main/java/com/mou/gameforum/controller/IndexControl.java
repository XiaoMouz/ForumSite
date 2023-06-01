package com.mou.gameforum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller("/")
public class IndexControl {
    @RequestMapping("/")
    public ModelAndView index(){
        return new ModelAndView("index");
    }
}
