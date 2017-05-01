package com.padeoe.batterhistorian.service;

import com.padeoe.batterhistorian.pojo.Device;

import java.io.File;
import java.util.List;

/**
 * Created by padeoe on 2017/4/29.
 */
public interface TaskCommitService {
    List<Device>getDevices();
    boolean commitApk(File apkFile,List<Device>devices);
    boolean commitApk(File apkFile);
}
