package com.mou.gameforum.advice;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
@RestControllerAdvice
@Controller
@RestController
public class GlobalExceptionAdviceControl {
    /**
     * 404 页面
     * @return ModelAndView
     */
    @RequestMapping("/404")
    public ModelAndView error404(){
        return new ModelAndView("error/404");
    }

    /**
     * 接管所有 Exception，出现时跳转至 500 页面
     * @param ex Exception
     * @return ModelAndView
     */
    @ExceptionHandler(Exception.class)
    @RequestMapping("/500")
    public ModelAndView handleException(Exception ex) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("error/500");
        modelAndView.addObject("error", ex);
        return modelAndView;
    }
}
