package com.mou.gameforum.controller.apis;

import com.mou.gameforum.entity.User;
import com.mou.gameforum.entity.dto.NetworkRequestDto;
import com.mou.gameforum.entity.dto.UserLoginDto;
import com.mou.gameforum.entity.vo.RequestResult;
import com.mou.gameforum.service.user.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController()
@RequestMapping("/api/user")
@CrossOrigin("*")
public class UserApiControl {

    @Resource
    UserService userService;

    @PostMapping("/login")
    public String login(@RequestBody  UserLoginDto userLoginDto, HttpServletRequest request){
        NetworkRequestDto nrd = new NetworkRequestDto(request.getRemoteAddr(),new Date());
        User user = userService.loginDto(userLoginDto,nrd);
        if(user!=null){
            return new RequestResult<>(200,"登录成功",user).toString();
        }
        return new RequestResult<>(401,"用户名或密码错误",null).toString();
    }
}
