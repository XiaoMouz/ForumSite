package com.mou.gameforum.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.yulichang.base.MPJBaseMapper;
import com.mou.gameforum.entity.Levels;
import com.mou.gameforum.entity.User;
import com.mou.gameforum.entity.dto.NetworkRequestDto;
import com.mou.gameforum.entity.dto.UserLoginDto;
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
     * @param loginDto 登录 dto，包含邮箱和 md5 密码
     * @return 用户
     */
    @Select("select * from users where email = #{email} and password = #{password}")
    User getUserByUserDtoEmail(UserLoginDto loginDto);

    /**
     * 更新用户的登录时间和 IP
     * @param user 用户
     * @param requestDto 请求 dto，包含了 ip 和时间
     */
    @Update("update users set loginTime = #{requestDto.time}, loginIp = #{requestDto.ip} where id = #{user.id}")
    void updateUserLoginTimeAndIp(User user, NetworkRequestDto requestDto);

    /**
     * 获取对应用户的权限组
     * @param user 用户
     * @return 权限组列表
     */
    @Select("select * from users_levels where id in (select lId from users_levels where uId = #{id})")
    List<Levels> getUserLevels(User user);

    /**
     * 重写后的 insert ，屏蔽了 id
     * @param entity 用户
     * @return 插入的行数
     */
    @Override
    @Insert("insert into users(username, nickname , password, email, loginTime, loginIp, registerIp, registerTime) values(#{username}, #{nickname}, #{password}, #{email}, #{loginTime}, #{loginIp}, #{registerIp}, #{registerTime})")
    int insert(User entity);
}
