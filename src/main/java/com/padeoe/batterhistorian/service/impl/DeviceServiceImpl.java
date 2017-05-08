package com.padeoe.batterhistorian.service.impl;

import com.padeoe.batterhistorian.dao.DeviceRepository;
import com.padeoe.batterhistorian.pojo.Device;
import com.padeoe.batterhistorian.service.DeviceService;
import com.padeoe.platformtools.ADBDevice;
import com.padeoe.platformtools.ADBTool;
import com.padeoe.platformtools.EnvironmentNotConfiguredException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by padeoe on 2017/5/8.
 */
@Service("DeviceService")
public class DeviceServiceImpl implements DeviceService {
    @Autowired
    DeviceRepository deviceRepository;

    @Override
    public Iterable<Device> getAllDevices() {
        return deviceRepository.findAll();
    }

    @Override
    public List<Device> detectAllDeviceConnected() throws IOException, EnvironmentNotConfiguredException {
        return ADBTool.getDevices().stream().filter(adbDevice -> adbDevice.getState().equals(ADBDevice.State.CONNECTED)).map(adbDevice -> {
            try {
                return Device.fromADBDevice(adbDevice);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (EnvironmentNotConfiguredException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());
    }
}
