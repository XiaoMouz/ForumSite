package com.mou.gameforum.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Component
public class LogFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(LogFilter.class);


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            // 记录请求的控制器类名
            String controllerClassName = filterChain.getClass().getName();
            logger.info("Controller Class Name: {}", controllerClassName);

            filterChain.doFilter(request, response);

            // 如果请求处理成功，记录返回的 ModelAndView 的视图名称
            if (response.getStatus() < 300) {
                ModelAndView mav = (ModelAndView) request.getAttribute("org.springframework.web.servlet.ModelAndView");
                if (mav != null) {
                    String viewName = mav.getViewName();
                    logger.info("Returned view name: {}", viewName);
                }
            }

        } catch (Exception e) {
            logger.error("LogFilter exception:", e);
        }
    }
}