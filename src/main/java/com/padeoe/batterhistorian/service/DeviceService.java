package com.padeoe.batterhistorian.service;

import com.padeoe.batterhistorian.pojo.Device;
import com.padeoe.platformtools.EnvironmentNotConfiguredException;

import java.io.IOException;
import java.util.List;

/**
 * Created by padeoe on 2017/5/8.
 */
public interface DeviceService {
    Iterable<Device> getAllDevices();
    List<Device> detectAllDeviceConnected() throws IOException, EnvironmentNotConfiguredException;
}
