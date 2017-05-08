package com.padeoe.batterhistorian.service;

import com.padeoe.batterhistorian.pojo.App;
import com.padeoe.batterhistorian.pojo.Device;
import com.padeoe.batterhistorian.pojo.Record;
import com.padeoe.batterhistorian.service.impl.RecordServiceImpl;

import java.util.List;

/**
 * Created by padeoe on 2017/5/1.
 */
public interface RecordService{
    /**
     * 查询特定app在哪些设备上测试过
     */
    List<Device> getTestedDevices(String appId);

    void testApp(List<Device>devices,App app);
    Iterable<Record> getAllRecord();
    Iterable<Record> getAppPowerVersionLine(String packageName,String deviceId);

}
