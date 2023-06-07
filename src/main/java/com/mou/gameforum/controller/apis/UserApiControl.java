package com.mou.gameforum.controller.apis;

import com.mou.gameforum.entity.User;
import com.mou.gameforum.entity.dto.NetworkRequestDto;
import com.mou.gameforum.entity.dto.UserLoginDto;
import com.mou.gameforum.entity.enums.UserStatusEnum;
import com.mou.gameforum.entity.vo.RequestResult;
import com.mou.gameforum.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

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
                    description = "successful operation",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = User.class)
                            )
                    }
                    )
    })
    @PostMapping("/login")
    public String login(@RequestBody  UserLoginDto userLoginDto, HttpServletRequest request, HttpServletResponse response){
        NetworkRequestDto nrd = new NetworkRequestDto(request.getRemoteAddr(),new Date());
        User user = userService.loginByDto(userLoginDto,nrd);
        if(user==null) {
            return new RequestResult<>(401,"用户名或密码错误",null).toString();
        }
        if(user.getStatus()!= UserStatusEnum.ACTIVE){
            response.setStatus(403);
            return new RequestResult<>(403,"账户未激活或已封禁",null).toString();
        }

        response.setStatus(401);
        return new RequestResult<>(200,"登录成功",user).toString();
    }
}
