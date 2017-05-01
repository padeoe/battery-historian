package com.padeoe.batterhistorian.service.impl;

import com.padeoe.batterhistorian.pojo.Device;
import com.padeoe.batterhistorian.service.TaskCommitService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

/**
 * Created by padeoe on 2017/4/29.
 */
@Service("TaskCommitService")
public class TaskCommitServiceImpl implements TaskCommitService{

    @Override
    public List<Device> getDevices() {
        return null;
    }

    @Override
    public boolean commitApk(File apkFile, List<Device> devices) {
        return false;
    }

    @Override
    public boolean commitApk(File apkFile) {
        return false;
    }
}
