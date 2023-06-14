package com.mou.gameforum.advice;

import jakarta.servlet.http.HttpServletResponse;
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
    public ModelAndView error404(HttpServletResponse response){
        response.setStatus(404);
        return new ModelAndView("error/404");
    }

    /**
     * 接管所有 Exception，出现时跳转至 500 页面
     * @param error Exception
     * @return ModelAndView
     */
    @ExceptionHandler(Exception.class)
    @RequestMapping("/500")
    public ModelAndView handleException(Exception error,HttpServletResponse response) {
        response.setStatus(500);
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("error/500");
        modelAndView.addObject("error", error);
        modelAndView.addObject("errorName", error.getClass().getName());
        modelAndView.addObject("errorMessage",error.getMessage());
        return modelAndView;
    }
}
