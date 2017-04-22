package com.padeoe.platformtools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by padeoe on 2017/4/22.
 */
public class ApkInfo {
    private String allManifest;
    private String apkName;
    private String versionName;
    private String packageName;
    private String sdkVersion;
    private String targetSdkVersion;
    private String versionCode;

    public ApkInfo(String allManifest) {
        this.allManifest=allManifest;
        apkName = getAttribution("application-label:'(.*?)'");
        versionName = getAttribution("versionName='(.*?)'");
        packageName = getAttribution("package: name='(.*?)'");
        sdkVersion = getAttribution("sdkVersion:'(.*?)'");
        targetSdkVersion = getAttribution("targetSdkVersion:'(.*?)'");
        versionCode= getAttribution("versionCode='(.*?)'");
    }

    private String getAttribution(String regex)
    {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(allManifest);
        if(matcher.find()){
            return matcher.group(1);
        }
        return "";
    }

    public boolean newerThan(ApkInfo apkInfo){
        return Integer.parseInt(versionCode)>Integer.parseInt(apkInfo.versionCode);
    }

    @Override
    public String toString() {
        return "ApkInfo{" +
                "apkName='" + apkName + '\'' +
                ", versionName='" + versionName + '\'' +
                ", packageName='" + packageName + '\'' +
                ", sdkVersion='" + sdkVersion + '\'' +
                ", targetSdkVersion='" + targetSdkVersion + '\'' +
                ", versionCode='" + versionCode + '\'' +
                '}';
    }
}
