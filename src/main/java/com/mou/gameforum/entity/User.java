package com.mou.gameforum.entity;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mou.gameforum.entity.enums.UserStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;


import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "User 用户")
@TableName("users")
public class User {
    /**
     * 用户id
     */
    @Schema(description = "用户 uid")
    Integer id;
    /**
     * 用户用户名
     */
    @Schema(description = "用户名")
    String username;
    /**
     * 用户邮箱
     */
    @Schema(description = "用户邮箱")
    String email;
    /**
     * 用户名称
     */
    @Schema(description = "用户昵称")
    String nickname;
    /**
     * 用户密码
     */
    @JsonIgnore
    String password;
    /**
     * 用户 token
     */
    @Schema(description = "用户 token")
    String token;
    /**
     * 用户状态
     */
    @Schema(description = "用户状态")
    UserStatusEnum status;
    /**
     * 用户注册 ip
     */
    @Schema(description = "用户注册 ip")
    String registerIp;
    /**
     * 用户登录ip
     */
    @Schema(description = "用户登录 ip")
    String loginIp;
    /**
     * 用户注册时间
     */
    @Schema(description = "用户注册时间")
    Date registerTime;
    /**
     * 用户登录时间
     */
    @Schema(description = "用户登录时间")
    Date loginTime;
    /**
     * 用户头像相对路径
     */
    @Schema(description = "用户头像相对路径")
    String avatar_path;
    /**
     * 用户描述
     */
    @Schema(description = "用户描述")
    String about;
    /**
     * 用户权限组
     */
    @Schema(description = "用户权限组")
    @TableField(exist = false)
    List<Levels> levels;

    public Levels findMaxLevel(){
        Levels maxLevel=levels.get(0);
        for(Levels l:this.levels){
            if(l.getReadLevel()> maxLevel.getReadLevel()){
                maxLevel=l;
            }
        }
        return maxLevel;
    }
    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
