package com.mou.gameforum.service.content;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mou.gameforum.entity.Zone;
import java.util.List;

public interface ZoneService extends IService<Zone> {

    List<Zone> getZoneList();
}
