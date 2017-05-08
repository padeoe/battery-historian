package com.padeoe.batterhistorian.controller;

import com.padeoe.batterhistorian.dao.DeviceRepository;
import com.padeoe.batterhistorian.dao.RecordRepository;
import com.padeoe.batterhistorian.pojo.App;
import com.padeoe.batterhistorian.pojo.Device;
import com.padeoe.batterhistorian.pojo.Record;
import com.padeoe.batterhistorian.service.RecordService;
import com.padeoe.platformtools.ADBTool;
import com.padeoe.platformtools.EnvironmentNotConfiguredException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.stream.Collectors;

/**
 * Created by padeoe on 2017/5/1.
 */
@Controller
@RequestMapping(path = "/record")
public class RecordController {
    @Autowired
    private RecordService recordService;

    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<Record> getAllApps() {
        return recordService.getAllRecord();
    }

    @GetMapping(path = "/testqq")
    public @ResponseBody
    void testQQ() {

        try {
            recordService.testApp(ADBTool.getDevices().stream().map(adbDevice -> {
                try {
                    return Device.fromADBDevice(adbDevice);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (EnvironmentNotConfiguredException e) {
                    e.printStackTrace();
                }
                return null;
            }).collect(Collectors.toList()), new App("com.tencent.mm",21,"QQ","2.3","this is qq"));
        } catch (EnvironmentNotConfiguredException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @GetMapping(path = "/qqallversion")
    public @ResponseBody
    Iterable<Record> getPowerVersionLine(@RequestParam String packageName,@RequestParam String deviceId) {
        return recordService.getAppPowerVersionLine(packageName,deviceId);
    }

}
