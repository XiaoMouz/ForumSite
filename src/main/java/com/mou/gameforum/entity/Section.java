package com.mou.gameforum.entity;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分区
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sections")
public class Section {
    Integer id;
    /**
     * 分区名
     */
    String name;
    /**
     * 分区描述
     */
    String description;;
    /**
     * 分区版主
     */
    User[] moderator;
    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
