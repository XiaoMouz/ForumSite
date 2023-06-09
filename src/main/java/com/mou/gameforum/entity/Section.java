package com.mou.gameforum.entity;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "版块")
@TableName("sections")
public class Section {
    Integer id;
    /**
     * 版块名
     */
    @Schema(description = "版块名称")
    String name;
    /**
     * 版块描述
     */
    @Schema(description = "版块详情")
    String description;;
    /**
     * 版块版主
     */
    @Schema(description = "版块版主 用户类型")
    @TableField(exist = false)
    User[] moderator;
    /**
     * 版块图标相对路径
     */
    @Schema(description = "版块图标")
    String icon;
    /**
     * 版块下文章
     */
    @Schema(description = "版块下文章")
    @TableField(exist = false)
    List<Post> posts;

    @Schema(description = "版块下文章数量")
    @TableField("total")
    Integer totalPost;
    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
