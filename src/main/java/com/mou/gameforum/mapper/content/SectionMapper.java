package com.mou.gameforum.mapper.content;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.yulichang.base.MPJBaseMapper;
import com.mou.gameforum.entity.Section;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SectionMapper extends MPJBaseMapper<Section> {

    @Select("select * from sections where zid = #{zoneId}")
    List<Section> getSectionListByZoneId(Integer zoneId);
}
