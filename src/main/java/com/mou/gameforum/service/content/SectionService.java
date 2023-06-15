package com.mou.gameforum.service.content;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mou.gameforum.entity.Section;

import java.util.List;

public interface SectionService extends IService<Section> {
    List<Section> getSectionListByZoneId(Integer zoneId);
}
