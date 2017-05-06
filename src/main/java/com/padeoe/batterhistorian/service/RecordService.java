package com.padeoe.batterhistorian.service;

import com.padeoe.batterhistorian.pojo.Device;
import com.padeoe.batterhistorian.service.impl.RecordServiceImpl;

import java.util.List;

/**
 * Created by padeoe on 2017/5/1.
 */
public interface RecordService{
    List<Device> getTestedDevices(String appId);
}
