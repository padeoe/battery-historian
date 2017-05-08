package com.padeoe.batterhistorian.service.impl;

import com.padeoe.batterhistorian.dao.AppRepository;
import com.padeoe.batterhistorian.pojo.App;
import com.padeoe.batterhistorian.pojo.Tag;
import com.padeoe.batterhistorian.service.AppService;
import com.padeoe.platformtools.BatteryStats;
import com.padeoe.platformtools.BatteryStatsReader;
import com.padeoe.platformtools.EnvironmentNotConfiguredException;
import com.padeoe.platformtools.StatsInfoNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by padeoe on 2017/5/1.
 */

@Service("AppService")
public class AppServiceImpl implements AppService{
    @Autowired
    private AppRepository appRepository;
    @Override
    public List<App> search(String keyword) {
        return appRepository.searchApp(keyword);
    }

    @Override
    public List<Tag> GetAppTags(String appId) {
        return appRepository.getAppById(Integer.parseInt(appId)).stream().flatMap(app->app.getTags().stream()).collect(Collectors.toList());
    }


}
