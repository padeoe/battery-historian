package com.padeoe.batterhistorian.dao;

import com.padeoe.batterhistorian.pojo.Device;
import com.padeoe.batterhistorian.pojo.Record;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by padeoe on 2017/5/1.
 */

public interface RecordRepository extends CrudRepository<Record,Long>{
    @Query("select record.deviceId from Record record where record.appId=?1")
    List<Device> GetTestedDevice(String appId);
}
