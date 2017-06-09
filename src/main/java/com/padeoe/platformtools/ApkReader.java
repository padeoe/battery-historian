package com.padeoe.platformtools;


import java.io.File;
import java.io.IOException;

/**
 * Created by padeoe on 2017/4/22.
 */
public class ApkReader {
    File apkFile;

    public ApkReader(File apkFile) {
        this.apkFile = apkFile;
    }

    public String readApkManifest() throws EnvironmentNotConfiguredException, IOException, InterruptedException {
        String[]commands=new String[]{"aapt","dump","badging",apkFile.getPath()};
        ADBTool.ProcessOutput processOutput= ADBTool.execute(commands);
        if(processOutput.errorString!=null){
            throw new EnvironmentNotConfiguredException(processOutput.errorString);
        }
        else {
            return processOutput.stdString;
        }
    }

    public ApkInfo getApkInfo() throws InterruptedException, EnvironmentNotConfiguredException, IOException {
        return new ApkInfo(readApkManifest());
    }

}
