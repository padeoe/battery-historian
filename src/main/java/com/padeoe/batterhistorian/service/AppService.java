package com.padeoe.batterhistorian.service;

import com.padeoe.batterhistorian.pojo.App;
import com.padeoe.batterhistorian.pojo.Tag;

import java.util.List;

/**
 * Created by padeoe on 2017/5/1.
 */
public interface AppService {
    List<App> search(String keyword);
    List<Tag> GetAppTags(String appId);

}
