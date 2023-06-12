package com.mou.gameforum.entity;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Level 权限组")
@TableName("userlevels")
public class Levels {
    /**
     * 权限组id
     */
    Integer id;
    /**
     * 权限组名称
     */
    @Schema(description = "权限组名称")
    String levelName;
    /**
     * 站点管理权(是否可打开后台页)
     */
    @Schema(description = "权限组站点管理权")
    Boolean manageSite;
    /**
     * 权限组阅读等级
     */
    @Schema(description = "权限组阅读等级")
    Integer readLevel;
    /**
     * 是否是默认的验证后权限组
     */
    @Schema(description = "是否是默认验证后权限组")
    Boolean verifyLevel;
    /**
     * 是否是默认待验证权限组
     */
    @Schema(description = "是否是默认待验证权限组")
    Boolean pendingLevel;
    /**
     * 是否是默认禁言权限组
     */
    @Schema(description = "是否是禁言权限组")
    Boolean muteLevel;
    /**
     * 权限组回复权限
     */
    @Schema(description = "权限组回复权限")
    Boolean reply;
    /**
     * 权限组发帖权限
     */
    @Schema(description = "权限组发帖权限")
    Boolean releasePost;
    /**
     * 权限组颜色，默认为 #000000 黑
     */
    String color;
    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
