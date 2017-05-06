package com.padeoe.batterhistorian.service.impl;

import com.padeoe.batterhistorian.dao.AppRepository;
import com.padeoe.batterhistorian.dao.RecordRepository;
import com.padeoe.batterhistorian.pojo.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by padeoe on 2017/5/1.
 */
@Service("RecordService")
public class RecordServiceImpl {
    @Autowired
    private RecordRepository recordRepository;

    List<Device> getTestedDevices(String appId) {
        return recordRepository.getTestedDevices(appId);
    }
}
