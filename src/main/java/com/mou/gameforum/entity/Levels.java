package com.mou.gameforum.entity;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("Level 权限组")
@TableName("userlevels")
public class Levels {
    /**
     * 权限组id
     */
    Integer id;
    /**
     * 权限组名称
     */
    @ApiModelProperty("权限组名称")
    String levelName;
    /**
     * 权限组阅读等级
     */
    @ApiModelProperty("权限组阅读等级")
    Integer readLevel;
    /**
     * 权限组回复权限
     */
    @ApiModelProperty("权限组回复权限")
    Boolean reply;
    /**
     * 权限组发帖权限
     */
    @ApiModelProperty("权限组发帖权限")
    Boolean releasePost;
    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
