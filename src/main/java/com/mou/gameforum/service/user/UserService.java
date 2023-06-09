package com.mou.gameforum.service.user;

import com.mou.gameforum.entity.User;
import com.mou.gameforum.entity.dto.NetworkRequestDto;
import com.mou.gameforum.entity.dto.UserLoginDto;
import com.mou.gameforum.entity.dto.UserRegisterDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    /**
     * 登录
     * @param loginDto 登录 dto
     * @param requestDto 请求 dto
     * @return 返回用户数据
     */
    User loginByDto(UserLoginDto loginDto, NetworkRequestDto requestDto);

    /**
     * 注册
     * @param registerDto 注册 dto
     * @param requestDto 请求 dto
     * @return 返回验证 token，在验证后用户账户会由 pending 转为 active
     */
    String registerByDto(UserRegisterDto registerDto,NetworkRequestDto requestDto);

    /**
     * 验证注册用户
     * @param username 用户名
     * @param token 验证 token
     * @return 返回用户数据
     */
    User verifyRegisterUser(String username,String token);
}
