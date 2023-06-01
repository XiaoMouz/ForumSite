package com.mou.gameforum.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    /**
     * 用户id
     */
    Integer id;
    /**
     * 用户用户名
     */
    String username;
    /**
     * 用户邮箱
     */
    String email;
    /**
     * 用户名称
     */
    String nickname;
    /**
     * 用户密码
     */
    @JsonIgnore
    String password;
    /**
     * 用户 token
     */
    String token;
    /**
     * 用户注册 ip
     */
    String registerIp;
    /**
     * 用户登录ip
     */
    String loginIp;
    /**
     * 用户注册时间
     */
    Date registerTime;
    /**
     * 用户登录时间
     */
    Date loginTime;
    /**
     * 用户头像相对路径
     */
    String avatar_path;
    /**
     * 用户权限组
     */
    Levels[] levels;
}
