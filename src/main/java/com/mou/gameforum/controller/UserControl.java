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

    /**
     * 用户登录页面
     * @param response response
     * @param session session
     * @param request request
     * @return 用户登录页
     */
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

    /**
     * 用户登录 Post 请求处理
     * @param user 用户登录 dto
     * @param response response
     * @param session session
     * @param request request
     * @return 登录成功重定向到首页，失败返回 json 供异步
     * @throws IOException IOException
     */
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

    /**
     * 用户注册页面
     * @param response response
     * @param session session
     * @param request request
     * @return 用户注册页
     */
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

    /**
     * 用户注册 Post 请求处理
     * @param user 用户注册 dto
     * @param response response
     * @param session session
     * @param request request
     * @return 注册成功重定向到验证页，失败返回 json 供异步
     * @throws IOException IOException
     */
    @PostMapping("/register")
    public ModelAndView register(@RequestBody UserRegisterDto user,
                                 HttpServletResponse response,
                                 HttpSession session,
                                 HttpServletRequest request) throws IOException {
        NetworkRequestDto nqd = new NetworkRequestDto(request.getRemoteAddr(),new Date());
        String token = userService.registerByDto(user,nqd);
        // check username is illegal
        if(user.getUsername()==null||user.getUsername().length()<3||user.getUsername().length()>16||!user.getUsername().matches("[a-zA-Z0-9 ]*")){
            response.setStatus(400);
            ResponseUtils.responseJson(response,new RequestResult<>(400,"Username not illegal",null));
            return null;
        }
        if(user.getEmail()==null||user.getEmail().length()<3||user.getEmail().length()>30||!user.getEmail().contains("@")){
            response.setStatus(400);
            ResponseUtils.responseJson(response,new RequestResult<>(400,"Email not illegal",null));
            return null;
        }

        if(token == null){
            response.setStatus(409);
            ResponseUtils.responseJson(response,new RequestResult<>(409,"Username or email is exits",null));
            return null;
        }
        session.setAttribute("username",user.getUsername());
        return new ModelAndView("redirect:/register/verify","username",user.getUsername());
    }

    /**
     * 用户注册后验证页
     * @param username 用户名
     * @param token 验证码
     * @param response response
     * @param session session
     * @param request request
     * @return 验证页
     */
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
            return new ModelAndView("user/register/verify","message","Your token is null");
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
        return new ModelAndView("user/register/verifysuccess");
    }

    @GetMapping("/register/verify/resend")
    public ModelAndView registerVerifyResend(String username,
                                             HttpServletResponse response,
                                             HttpSession session,
                                             HttpServletRequest request) throws IOException {
        if (username == null) {
            response.setStatus(400);
            ResponseUtils.responseJson(response, new RequestResult<>(400, "Username cannot is null", null));
            return null;
        }
        User user = userService.getUserByUsername(username);
        if (user == null) {
            response.setStatus(404);
            ResponseUtils.responseJson(response, new RequestResult<>(404, "Username is not exist", null));
            return null;
        }
        if (user.getStatus() == UserStatusEnum.ACTIVE) {
            response.setStatus(409);
            ResponseUtils.responseJson(response, new RequestResult<>(409, "User is activated", null));
            return null;
        }
        String token = userService.resendVerifyToken(user);
        if (token == null) {
            response.setStatus(500);
            ResponseUtils.responseJson(response, new RequestResult<>(500, "Send email failed", null));
            return null;
        }

        response.setStatus(201);

        session.removeAttribute("user");
        session.removeAttribute("message");
        ResponseUtils.responseJson(response, new RequestResult<>(201, "Resend verify token success", null));
        return null;
    }

    /**
     * 用户登出
     * @param response response
     * @param session session
     * @param request request
     * @return 重定向到首页
     * @throws IOException IOException
     */
    @GetMapping("/logout")
    public ModelAndView logout(HttpServletResponse response,
                               HttpSession session,
                               HttpServletRequest request) throws IOException {
        session.removeAttribute("user");
        return new ModelAndView("redirect:/");
    }

    /**
     * 用户个人信息页
     * @param id 请求的 用户 id
     * @param response response
     * @param session session
     * @param request request
     * @return 个人信息页
     */
    @GetMapping("/user/{id}")
    public ModelAndView userProfile(@PathVariable Integer id,
                                    HttpServletResponse response,
                                    HttpSession session,
                                    HttpServletRequest request
                                    ) {
        User user = (User) session.getAttribute("user");

        ModelAndView mav = new ModelAndView("user/profile");
        if (Objects.equals(user.getId(), id)) {
            mav.addObject("self", true);
            return mav;

        }
        user = userService.getUserById(id);
        if (user == null) {
            mav.addObject("self", true);
            mav.addObject("message", "User is not exist");
            return mav;
        }
        mav.clear();
        mav = new ModelAndView("user/profile");
        mav.addObject("guest", user);
        mav.addObject("self", false);
        return mav;
    }
}
