package com.mou.gameforum.controller.apis;

import com.mou.gameforum.entity.User;
import com.mou.gameforum.entity.dto.NetworkRequestDto;
import com.mou.gameforum.entity.dto.UserLoginDto;
import com.mou.gameforum.entity.dto.UserRegisterDto;
import com.mou.gameforum.entity.enums.UserStatusEnum;
import com.mou.gameforum.entity.vo.RequestResult;
import com.mou.gameforum.service.user.UserService;
import com.mou.gameforum.utils.ResponseUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Date;

@RestController()
@RequestMapping("/api/user")
@Tag(name = "用户接口",description = "用户操作接口" )
@CrossOrigin("*")
public class UserApiControl {

    @Resource
    UserService userService;

    @Operation(summary = "用户登录",tags ={"user"})
    @ApiResponses(value = {
            @ApiResponse(
                    description = "Login success",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Login success",
                                            value = "{\"code\":200,\"data\":{\"id\":1,\"username\":\"test\",\"password\":\"123456\",\"email\":\"example@example.com\",\"status\":\"ACTIVE\",\"createTime\":\"2021-07-07T15:00:00.000+00:00\",\"updateTime\":\"2021-07-07T15:00:00.000+00:00\"}}"
                                    )
                            )
                    }
            ),
            @ApiResponse(
                    description = "Username or password is wrong",
                    responseCode = "401",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Username or password is wrong",
                                    value = "{\"code\":401,\"message\":\"Username or password is wrong\",\"data\":null}"
                            )
                    )
            ),
            @ApiResponse(
                    description = "Account is not active or has been banned",
                    responseCode = "403",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Account is not active or has been banned",
                                    value = "{\"code\":403,\"message\":\"Account is not active or has been banned\",\"data\":null}"
                            )
                    )
            )
    })
    @PostMapping("/login")
    public RequestResult<User> login(@RequestBody  UserLoginDto userLoginDto, HttpServletRequest request, HttpServletResponse response){
        NetworkRequestDto nrd = new NetworkRequestDto(request.getRemoteAddr(),new Date());
        User user = userService.loginByDto(userLoginDto,nrd);
        if(user==null) {
            return new RequestResult<>(401,"Username or password is wrong",null);
        }
        if(user.getStatus()!= UserStatusEnum.ACTIVE){
            response.setStatus(403);
            return new RequestResult<>(403,"Account is not active or has been banned",null);
        }

        response.setStatus(200);
        return new RequestResult<>(200,null,user);
    }

    @Operation(summary = "用户注册",tags ={"user"})
    @ApiResponses(value = {
            @ApiResponse(
                    description = "Register success",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Register success",
                                            value = "{\"code\":201,\"message\":\"Register success waiting for verify\",\"data\":null}"
                                    )
                            )
                    }
            ),@ApiResponse(
                responseCode = "409",
                description = "Username or email is already registered",
                content = {
                        @Content(
                                mediaType = "application/json",
                                examples = @ExampleObject(
                                        name = "Username or email is exist",
                                        value = "{\"code\":409,\"message\":\"Username or email is exist\",\"data\":null}"
                                )
                        )
                }
            ),@ApiResponse(
                    responseCode = "400",
                    description = "Username or email is not illegal",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Username or email is not illegal",
                                            value = "{\"code\":400,\"message\":\"Username or email is not illegal\",\"data\":null}"
                                    )
                            )
                    }
            )
    })
    @PostMapping("/register")
    public RequestResult<String> register(@RequestBody UserRegisterDto user,
                                 HttpServletResponse response,
                                 HttpSession session,
                                 HttpServletRequest request) throws IOException {
        NetworkRequestDto nqd = new NetworkRequestDto(request.getRemoteAddr(),new Date());
        String token = userService.registerByDto(user,nqd);
        if(user.getUsername()==null||user.getUsername().length()<3||user.getUsername().length()>16||!user.getUsername().matches("[a-zA-Z0-9 ]*")){
            response.setStatus(400);
            return new RequestResult<>(400,"Username not illegal",null);
        }
        if(user.getEmail()==null||user.getEmail().length()<3||user.getEmail().length()>30||!user.getEmail().contains("@")||
                !user.getEmail().matches("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$")){
            response.setStatus(400);
            return new RequestResult<>(400,"Email not illegal",null);
        }
        // check special character,as space, * some
        if(token == null) {
            response.setStatus(409);
            return new RequestResult<>(409, "Username or email is exist", null);
        }
        response.setStatus(201);
        return new RequestResult<>(201,"Register success waiting for verify",user.username);
    }
}
