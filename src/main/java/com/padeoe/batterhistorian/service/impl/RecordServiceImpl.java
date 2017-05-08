package com.padeoe.batterhistorian.service.impl;

import com.padeoe.batterhistorian.dao.AppRepository;
import com.padeoe.batterhistorian.dao.RecordRepository;
import com.padeoe.batterhistorian.pojo.App;
import com.padeoe.batterhistorian.pojo.Device;
import com.padeoe.batterhistorian.pojo.Record;
import com.padeoe.batterhistorian.service.RecordService;
import com.padeoe.platformtools.BatteryStats;
import com.padeoe.platformtools.BatteryStatsReader;
import com.padeoe.platformtools.EnvironmentNotConfiguredException;
import com.padeoe.platformtools.StatsInfoNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by padeoe on 2017/5/1.
 */
@Service("RecordService")
public class RecordServiceImpl implements RecordService {
    @Autowired
    private RecordRepository recordRepository;

    public List<Device> getTestedDevices(String appId) {
        return recordRepository.getTestedDevices(appId);
    }



    @Override
    public void testApp(List<Device> devices, App app) {
        devices.forEach(device -> {
            try {
                BatteryStats batteryStats = new BatteryStatsReader(device.getSerialNumber(), app.getPackageName()).read();
                recordRepository.save(new Record(new Date(), app, device, batteryStats.getMmpp(),
                        batteryStats.getComponetPower(BatteryStats.MobileComponet.CPU),
                        batteryStats.getComponetPower(BatteryStats.MobileComponet.RADIO),
                        batteryStats.getComponetPower(BatteryStats.MobileComponet.WAKE),
                        batteryStats.getComponetPower(BatteryStats.MobileComponet.WIFI),
                        batteryStats.getComponetPower(BatteryStats.MobileComponet.GPS),
                        batteryStats.getComponetPower(BatteryStats.MobileComponet.SENSOR),
                        batteryStats.getComponetPower(BatteryStats.MobileComponet.CAMERA)
                ));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (StatsInfoNotFoundException e) {
                e.printStackTrace();
            } catch (EnvironmentNotConfiguredException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });


    }

    @Override
    public Iterable<Record> getAllRecord() {
        return recordRepository.findAll();
    }

    @Override
    public Iterable<Record> getAppPowerVersionLine(String packageName,String deviceId) {
        return recordRepository.getAppPowerVersionLine(packageName,deviceId);
    }
}
