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
    /**
     * 查询特定app在哪些设备上测试过
     */
    @Query("select distinct record.device from Record record where record.app=?1")
    List<Device> getTestedDevices(String appId);

    /**
     * 获取特定app的测试记录
     * @param appId app的id
     * @return 测试记录
     */
    List<Record>getRecordsByAppId(String appId);

    /**
     * 获取app不同版本的耗电情况
     * @param packageName app的包名
     * @return 测试记录
     */
    @Query("")
    List<Record>getAppPowerVersionLine(String packageName);


}
