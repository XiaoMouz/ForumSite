package com.mou.gameforum.controller;

import com.mou.gameforum.entity.User;
import com.mou.gameforum.entity.dto.NetworkRequestDto;
import com.mou.gameforum.entity.dto.UserLoginDto;
import com.mou.gameforum.entity.dto.UserRegisterDto;
import com.mou.gameforum.entity.enums.UserStatusEnum;
import com.mou.gameforum.entity.vo.RequestResult;
import com.mou.gameforum.service.user.UserService;
import com.mou.gameforum.utils.ResponseUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.sf.jsqlparser.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;

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
    public ModelAndView login(@RequestBody UserLoginDto user,
                              HttpServletResponse response,
                              HttpSession session,
                              HttpServletRequest request) throws IOException {
        NetworkRequestDto nqd = new NetworkRequestDto(request.getRemoteAddr(),new Date());
        User verifyUser = userService.loginByDto(user,nqd);
        if(verifyUser==null){
            response.setStatus(401);
            ResponseUtils.responseJson(response, new RequestResult<>(401, "用户名或密码错误", null));
            return null;
        }
        if(verifyUser.getStatus()!= UserStatusEnum.ACTIVE){
            response.setStatus(403);
            ResponseUtils.responseJson(response,new RequestResult<>(403,"账户未激活或已封禁",null));
            return null;
        }

        session.setAttribute("user",verifyUser);
        return new ModelAndView("redirect:/","user",verifyUser);
    }

    @GetMapping("/register")
    public ModelAndView register(HttpServletResponse response,
                                 HttpSession session,
                                 HttpServletRequest request){
        User user = (User) session.getAttribute("user");
        if (user != null) {
            return new ModelAndView("redirect:/", "user", user);
        }
        return new ModelAndView("user/register");
    }

    @PostMapping("/register")
    public ModelAndView register(@RequestBody UserRegisterDto user,
                                 HttpServletResponse response,
                                 HttpSession session,
                                 HttpServletRequest request) throws IOException {
        NetworkRequestDto nqd = new NetworkRequestDto(request.getRemoteAddr(),new Date());
        String token = userService.registerByDto(user,nqd);
        if(token == null){
            response.setStatus(409);
            ResponseUtils.responseJson(response,new RequestResult<>(409,"用户名或邮箱已注册",null));
            return null;
        }
        session.setAttribute("username",user.getUsername());
        return new ModelAndView("redirect:/register/verify","username",user.getUsername());
    }

    @GetMapping("/register/verify")
    public ModelAndView registerVerify(String username,
                                       String token,
                                       HttpServletResponse response,
                                       HttpSession session,
                                       HttpServletRequest request){
        if(username==null&&token==null){
            return new ModelAndView("user/register/verify");
        }
        if(token==null){
            return new ModelAndView("user/register/verify","username",username);
        }
        if(username==null){
            return new ModelAndView("user/register");
        }
        User user = userService.verifyRegisterUser(username,token);

        if(user==null){
            ModelAndView mav = new ModelAndView("user/register/verify");
            mav.addObject("message",
                    "Your token is wrong or username not exist");
            return mav;
        }

        session.removeAttribute("user");
        session.removeAttribute("username");
        return new ModelAndView("redirect:/");
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletResponse response,
                               HttpSession session,
                               HttpServletRequest request) throws IOException {
        session.removeAttribute("user");
        return new ModelAndView("redirect:/");
    }

    @GetMapping("/user/{id}")
    public ModelAndView userProfile(@PathVariable Integer id,
                                    HttpServletResponse response,
                                    HttpSession session,
                                    HttpServletRequest request
                                    ){
        User user = (User) session.getAttribute("user");
        if(!Objects.equals(id, user.getId())){
            new ModelAndView("user/profile","self",false);
        }
        return new ModelAndView("user/profile","self",true);
    }
}
