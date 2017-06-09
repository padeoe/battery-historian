package com.padeoe.batterhistorian.service.impl;

import com.padeoe.batterhistorian.dao.AppRepository;
import com.padeoe.batterhistorian.dao.RecordRepository;
import com.padeoe.batterhistorian.pojo.App;
import com.padeoe.batterhistorian.pojo.Device;
import com.padeoe.batterhistorian.pojo.Record;
import com.padeoe.batterhistorian.service.RecordService;
import com.padeoe.platformtools.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by padeoe on 2017/5/1.
 */
@Service("RecordService")
public class RecordServiceImpl implements RecordService {
    @Autowired
    private RecordRepository recordRepository;

    public List<Device> getTestedDevices(String appId) {
        return recordRepository.getTestedDevices(Integer.parseInt(appId));
    }


    @Override
    public void testApp(Device device, App app, ApkInfo apkInfo) throws InterruptedException, StatsInfoNotFoundException, EnvironmentNotConfiguredException, IOException {
        System.out.println("开始测试app");
        System.out.println(ADBTool.launchActivity(device.getSerialNumber(), app.getPackageName(), apkInfo.getLaunchableActivity()));
        //Thread.sleep(20000);
      //  BatteryStats batteryStats = new BatteryStatsReader(device.getSerialNumber(), app.getPackageName()).read();
/*        recordRepository.save(new Record(new Date(), app, device, batteryStats.getMmpp(),
                batteryStats.getComponetPower(BatteryStats.MobileComponet.CPU),
                batteryStats.getComponetPower(BatteryStats.MobileComponet.RADIO),
                batteryStats.getComponetPower(BatteryStats.MobileComponet.WAKE),
                batteryStats.getComponetPower(BatteryStats.MobileComponet.WIFI),
                batteryStats.getComponetPower(BatteryStats.MobileComponet.GPS),
                batteryStats.getComponetPower(BatteryStats.MobileComponet.SENSOR),
                batteryStats.getComponetPower(BatteryStats.MobileComponet.CAMERA)
        ));*/
        recordRepository.save(new Record(new Date(), app, device, 2.08,
                0.00137,
                0.00123,
                0,
                0,
                0,
                0,
                0)
        );

    }

    @Override
    public Iterable<Record> getAllRecord() {
        return recordRepository.findAll();
    }

    @Override
    public Iterable<Record> getAppPowerVersionLine(String packageName, String deviceId) {
        return recordRepository.getAppPowerVersionLine(packageName,deviceId);
    }

    @Override
    public Iterable<Record> getAppPowerVersionLineWithDeviceName(String packageName, String deviceName) {
        return recordRepository.findAllByPackageNameAndDeviceName(packageName,deviceName);
    }


    @Override
    public Iterable<Record> getPowerByAppId(String appId) {
        return recordRepository.findAllByApp_Id(Integer.parseInt(appId));
    }

}
