package com.mou.gameforum.entity;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Zone 分区")
@TableName("section_zone")
public class Zone {
    Integer id;
    /**
     * 大区名
     */
    @Schema(description = "大区名")
    String name;
    /**
     * 大区描述
     */
    @Schema(description = "大区描述")
    String description;;
    /**
     * 大区管理员
     */
    @Schema(description = "大区管理员")
    User[] moderator;
    /**
     * 大区管理员权限组
     */
    @Schema(description = "大区管理组")
    Levels[] levels;
    /**
     * 大区图标相对路径
     */
    @Schema(description = "图标相对路径")
    String icon;
    /**
     * 大区下版块
     */
    @Schema(description = "大区下版块")
    List<Section> sections;
    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
