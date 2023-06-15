package com.mou.gameforum.service.content.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mou.gameforum.entity.Section;
import com.mou.gameforum.mapper.content.SectionMapper;
import com.mou.gameforum.service.content.SectionService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SectionServiceImpl
        extends ServiceImpl<SectionMapper, Section>
        implements SectionService {

    @Resource
    SectionMapper sectionMapper;

    public List<Section> getSectionListByZoneId(Integer zoneId){
        return sectionMapper.getSectionListByZoneId(zoneId);
    }
}
