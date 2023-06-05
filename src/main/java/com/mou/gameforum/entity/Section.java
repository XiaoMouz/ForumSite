package com.mou.gameforum.entity;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分区
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("版块")
@TableName("sections")
public class Section {
    Integer id;
    /**
     * 版块名
     */
    @ApiModelProperty("版块名称")
    String name;
    /**
     * 版块描述
     */
    @ApiModelProperty("版块详情")
    String description;;
    /**
     * 版块版主
     */
    @ApiModelProperty("版块版主 用户类型")
    User[] moderator;
    /**
     * 版块图标相对路径
     */
    @ApiModelProperty("版块图标")
    String icon;
    /**
     * 版块下文章
     */
    @ApiModelProperty("版块下文章")
    List<Post> posts;
    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
