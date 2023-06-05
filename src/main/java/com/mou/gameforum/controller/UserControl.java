package com.mou.gameforum.controller;

import com.mou.gameforum.entity.User;
import com.mou.gameforum.entity.dto.NetworkRequestDto;
import com.mou.gameforum.entity.dto.UserLoginDto;
import com.mou.gameforum.entity.vo.RequestResult;
import com.mou.gameforum.service.user.UserService;
import com.mou.gameforum.utils.ResponseUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Date;

@Controller
public class UserControl {
    @Resource
    UserService userService;
    @GetMapping("/login")
    public ModelAndView login(HttpServletResponse response,
                              HttpSession session,
                              HttpServletRequest request){
        User user = (User) session.getAttribute("user");

        if (user != null) {
            return new ModelAndView("redirect:/", "user", user);
        }

        return new ModelAndView("user/login");
    }
    @PostMapping("/login")
    public ModelAndView login(UserLoginDto user,
                              HttpServletResponse response,
                              HttpSession session,
                              HttpServletRequest request) throws IOException {
        // get network request ip
        // get right now time
        NetworkRequestDto nqd = new NetworkRequestDto(request.getRemoteAddr(),new Date());
        User verifyUser = userService.loginByDto(user,nqd);
        if(verifyUser!=null){
            session.setAttribute("user",verifyUser);
            return new ModelAndView("redirect:/","user",verifyUser);
        }
        ResponseUtils.responseJson(response, new RequestResult<>(401, "用户名或密码错误", null));
        return null;
    }
}
