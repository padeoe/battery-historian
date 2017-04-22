package com.padeoe.platformtools;

import org.zeroturnaround.exec.ProcessExecutor;
import org.zeroturnaround.exec.stream.slf4j.Slf4jStream;

import java.io.*;
import java.util.concurrent.TimeoutException;

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

    public static void main(String[] args) {
        try {
            String result=new ApkReader(new File("C:\\Users\\padeoe\\Desktop\\app-release.apk")).readApkManifest();
            ApkInfo apkInfo=new ApkInfo(result);
            System.out.println(apkInfo);
        } catch (EnvironmentNotConfiguredException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
