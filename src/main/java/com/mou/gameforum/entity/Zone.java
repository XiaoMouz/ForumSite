package com.mou.gameforum.entity;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("Zone 分区")
@TableName("section_zone")
public class Zone {
    Integer id;
    /**
     * 大区名
     */
    String name;
    /**
     * 大区描述
     */
    String description;;
    /**
     * 大区版主
     */
    User[] moderator;
    /**
     * 大区管理员权限组
     */
    Levels[] levels;
    /**
     * 大区图标相对路径
     */
    String icon;
    /**
     * 大区下版块
     */
    Section[] sections;
    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
