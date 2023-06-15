package com.mou.gameforum.mapper.content;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.yulichang.base.MPJBaseMapper;
import com.mou.gameforum.entity.Zone;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ZoneMapper extends MPJBaseMapper<Zone> {

    @Override
    @Insert("insert into section_zone (name,description,icon) values (#{name},#{description},#{icon})")
    int insert(Zone entity);

    @Select("select * from section_zone")
    List<Zone> getZoneList();
}
