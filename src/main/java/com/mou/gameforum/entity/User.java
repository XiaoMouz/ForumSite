package com.mou.gameforum.entity;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mou.gameforum.entity.enums.UserStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("User 用户")
@TableName("users")
public class User {
    /**
     * 用户id
     */
    @ApiModelProperty("用户 uid")
    Integer id;
    /**
     * 用户用户名
     */
    @ApiModelProperty("用户名")
    String username;
    /**
     * 用户邮箱
     */
    @ApiModelProperty("用户邮箱")
    String email;
    /**
     * 用户名称
     */
    @ApiModelProperty("用户昵称")
    String nickname;
    /**
     * 用户密码
     */
    @JsonIgnore
    String password;
    /**
     * 用户 token
     */
    @ApiModelProperty("用户 token")
    String token;
    /**
     * 用户状态
     */
    @ApiModelProperty("用户状态")
    UserStatusEnum status;
    /**
     * 用户注册 ip
     */
    @ApiModelProperty("用户注册 ip")
    String registerIp;
    /**
     * 用户登录ip
     */
    @ApiModelProperty("用户登录 ip")
    String loginIp;
    /**
     * 用户注册时间
     */
    @ApiModelProperty("用户注册时间")
    Date registerTime;
    /**
     * 用户登录时间
     */
    @ApiModelProperty("用户登录时间")
    Date loginTime;
    /**
     * 用户头像相对路径
     */
    @ApiModelProperty("用户头像相对路径")
    String avatar_path;
    /**
     * 用户权限组
     */
    @ApiModelProperty("用户权限组")
    @TableField(exist = false)
    List<Levels> levels;


    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
