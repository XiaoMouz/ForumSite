package com.mou.gameforum.service.content.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mou.gameforum.entity.Zone;
import com.mou.gameforum.mapper.content.ZoneMapper;
import com.mou.gameforum.service.content.SectionService;
import com.mou.gameforum.service.content.ZoneService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZoneServiceImpl
        extends ServiceImpl<ZoneMapper, Zone>
        implements ZoneService {

    @Resource
    ZoneMapper zoneMapper;

    @Resource
    SectionService sectionService;

    @Override
    public List<Zone> getZoneList() {
        List<Zone> zoneList = zoneMapper.selectList(null);
        for (Zone zone : zoneList) {
            zone.setSections(sectionService.getSectionListByZoneId(zone.getId()));
        }
        return zoneList;

    }
}
