package com.padeoe.batterhistorian.service;

import com.padeoe.batterhistorian.pojo.App;
import com.padeoe.batterhistorian.pojo.Device;
import com.padeoe.batterhistorian.pojo.Record;
import com.padeoe.batterhistorian.service.impl.RecordServiceImpl;
import com.padeoe.platformtools.ApkInfo;
import com.padeoe.platformtools.EnvironmentNotConfiguredException;
import com.padeoe.platformtools.StatsInfoNotFoundException;

import java.io.IOException;
import java.util.List;

/**
 * Created by padeoe on 2017/5/1.
 */
public interface RecordService{
    /**
     * 查询特定app在哪些设备上测试过
     */
    List<Device> getTestedDevices(String appId);

    void testApp(Device device, App app, ApkInfo apkInfo) throws InterruptedException, StatsInfoNotFoundException, EnvironmentNotConfiguredException, IOException;
    Iterable<Record> getAllRecord();
    Iterable<Record> getAppPowerVersionLine(String packageName,String deviceId);
    Iterable<Record> getPowerByAppId(String appId);

}
