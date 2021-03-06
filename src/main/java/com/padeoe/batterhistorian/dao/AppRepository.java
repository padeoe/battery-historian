package com.padeoe.batterhistorian.dao;

import com.padeoe.batterhistorian.pojo.App;
import com.padeoe.batterhistorian.pojo.Tag;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by padeoe on 2017/4/24.
 */
public interface AppRepository extends CrudRepository<App, Long> {

    @Query("select app from App app where concat( app.name,app.description) like concat('%',?1,'%')")
    List<App> searchApp(String keyword);

    List<App> getAppById(int appId);

}
