package com.padeoe.batterhistorian.service;

import com.padeoe.batterhistorian.pojo.App;
import com.padeoe.batterhistorian.pojo.Tag;

import java.util.List;

/**
 * Created by padeoe on 2017/5/1.
 */
public interface AppService {
    /**
     * 搜索特定含关键词的app
     * @param keyword 关键词
     * @return 相关的app
     */
    List<App> search(String keyword);

    /**
     * 获取app的标签
     * @param appId app的id
     * @return app的标签列表
     */
    List<Tag> GetAppTags(String appId);

}
