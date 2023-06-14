package com.mou.gameforum.service.user;

import com.mou.gameforum.entity.User;
import com.mou.gameforum.entity.dto.NetworkRequestDto;
import com.mou.gameforum.entity.dto.UserLoginDto;
import com.mou.gameforum.entity.dto.UserRegisterDto;
import com.mou.gameforum.entity.dto.UserResetDto;
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

    /**
     * 重新验证用户邮箱
     * @param user 用户
     * @return 返回验证 token
     */
    String resendVerifyToken(User user);

    /**
     * 重置用户密码
     * @param user 用户
     * @return 返回重置密码的 token
     */
    String resetUser(User user);

    /**
     * 验证重置密码的用户
     * @param username 用户名
     * @param token 重置密码的 token
     * @return 返回用户数据
     */
    User selectVerifyResetUser(String username,String token);

    /**
     * 重置用户密码
     * @param userResetDto 重置密码 dto
     * @return void
     */
    User resetPassword(UserResetDto userResetDto);

    /**
     * 根据 id 获取用户
     * @param id 用户 id
     * @return 用户
     */
    User getUserById(Integer id);

    /**
     * 根据 USERNAME 或 邮箱 获取用户
     * @param username 用户 username 或邮箱
     * @return 用户
     */
    User getUserByUsername(String username);

    /**
     * 更新用户
     * @param user 用户
     */
    void updateUserInfo(User user);
}
