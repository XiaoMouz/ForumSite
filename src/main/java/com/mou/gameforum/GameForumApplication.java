package com.mou.gameforum;

import com.mou.gameforum.filter.AuthFilter;
import org.jetbrains.annotations.NotNull;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Scanner;

@SpringBootApplication
@Configuration
@MapperScan("com.mou.gameforum.mapper")
public class GameForumApplication {
    @Autowired
    AuthFilter authFilter;

    public GameForumApplication() {
        super();
    }

    @Bean
    WebMvcConfigurer createWebMvcConfigurer() {
        return new WebMvcConfigurer() {
            /**
             * 为静态资源添加映射路径
             */
            @Override
            public void addResourceHandlers(@NotNull ResourceHandlerRegistry registry) {
                // 映射路径`/static/`到classpath路径:
                registry.addResourceHandler("/static/**")
                        .addResourceLocations("classpath:/static/");
            }

            /**
             * 添加了身份验证拦截器
             */
            @Override
            public void addInterceptors(@NotNull InterceptorRegistry registry) {
                registry.addInterceptor(authFilter).addPathPatterns("/**")
                        .excludePathPatterns(
                                "/404",
                                "/500",
                                "/login/**",
                                "/register/**",
                                "/reset/**",
                                "/logout",
                                "/api/**",
                                "/assets/**",
                                "/"
                        ); // 例外路径
            }

        };
    }

    public static void main(String[] args) {
        SpringApplication.run(GameForumApplication.class, args);
    }

}
