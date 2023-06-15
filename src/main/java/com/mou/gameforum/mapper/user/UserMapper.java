package com.mou.gameforum.mapper.user;

import com.github.yulichang.base.MPJBaseMapper;
import com.mou.gameforum.entity.Levels;
import com.mou.gameforum.entity.User;
import com.mou.gameforum.entity.dto.NetworkRequestDto;
import com.mou.gameforum.entity.dto.UserLoginDto;
import com.mou.gameforum.entity.dto.UserRegisterDto;
import com.mou.gameforum.entity.dto.UserResetDto;
import com.mou.gameforum.entity.enums.UserStatusEnum;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserMapper extends MPJBaseMapper<User> {
    /**
     * 根据登录 dto 用户名获取用户
     * @param loginDto 登录 dto，包含用户名和 md5 密码
     * @return 用户
     */
    @Select("select * from users where username = #{username} and password = #{password}")
    User getUserByUserDto(UserLoginDto loginDto);

    /**
     * 根据登录 dto 邮箱获取用户
     * @param loginDto 登录 dto，包含邮箱和 md5 密码，username 即为邮箱
     * @return 用户
     */
    @Select("select * from users where email = #{username} and password = #{password}")
    User getUserByUserDtoEmail(UserLoginDto loginDto);

    /**
     * 根据注册 dto 获取用户
     * @param dto 注册 dto，包含用户名和邮箱
     * @return 用户
     */
    @Select("select * from users where username = #{username} or email = #{email}")
    User getUserByRegisterDto(UserRegisterDto dto);

    /**
     * 更新用户的登录时间和 IP
     * @param user 用户
     * @param requestDto 请求 dto，包含了 ip 和时间
     */
    @Update("update users set loginTime = #{requestDto.time}, loginIp = #{requestDto.ip} where id = #{user.id}")
    void updateUserLoginTimeAndIp(User user, NetworkRequestDto requestDto);

    /**
     * 激活用户
     * @param username 用户名
     * @param token token
     * @param status 用户状态
     * @return 更新的行数
     */
    @Update("update users set status = #{status} where username = #{username} and token = #{token}")
    int updateUserStatus(String username, String token, UserStatusEnum status);

    /**
     * 清除用户的 token
     *
     * @param user 用户
     */
    @Update("update users set token = null where id = #{id}")
    void cleanUserToken(User user);

    /**
     * 根据用户名获取用户
     * @param username 用户名
     * @return 用户
     */
    @Select("select * from users where username = #{username}")
    User selectUserByUsername(String username);

    /**
     * 根据邮箱获取用户
     * @param email 邮箱
     * @return 用户
     */
    @Select("select * from users where email = #{email}")
    User selectUserByEmail(String email);

    /**
     * 更新用户 token
     * @param user 用户
     * @param token token
     */
    @Update("update users set token = #{token} where id = #{user.id}")
    void updateUserToken(User user,String token);

    @Select("select * from users where username = #{username} and token = #{token}")
    User getUserByToken(String username,String token);

    @Update("update users set password = #{password} where username = #{username} and token = #{token}")
    int updateUserPassword(UserResetDto userResetDto);

    @Update("update users set nickname = #{nickname}, email = #{email}, about = #{about}, avatar_path = #{avatar_path} where id = #{id}")
    int updateUserInfo(User user);

    /**
     * 重写后的 insert ，屏蔽了 id
     * @param entity 用户
     * @return 插入的行数
     */
    @Override
    @Insert("insert into users(username, nickname , password, email, token, loginTime, loginIp, registerIp, registerTime) values(#{username}, #{nickname}, #{password}, #{email}, #{token}, #{loginTime}, #{loginIp}, #{registerIp}, #{registerTime})")
    int insert(User entity);
}
