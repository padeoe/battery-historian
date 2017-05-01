package com.padeoe.batterhistorian.controller;

import com.padeoe.batterhistorian.dao.DeviceRepository;
import com.padeoe.batterhistorian.pojo.App;
import com.padeoe.batterhistorian.pojo.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by padeoe on 2017/4/30.
 */
@Controller
@RequestMapping(path="/device")
public class DeviceController {
    @Autowired
    private DeviceRepository deviceRepository;
    @GetMapping(path="/all")
    public @ResponseBody
    Iterable<Device> getAllApps() {
        return deviceRepository.findAll();
    }
}
