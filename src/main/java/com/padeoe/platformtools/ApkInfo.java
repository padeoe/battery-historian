package com.padeoe.platformtools;

import java.util.ArrayList;
import java.util.List;
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
    private String launchableActivity;
    private List<String>permissions;



    public ApkInfo(String allManifest) {
        this.allManifest=allManifest;
        apkName = getAttribution("application-label:'(.*?)'");
        versionName = getAttribution("versionName='(.*?)'");
        packageName = getAttribution("package: name='(.*?)'");
        sdkVersion = getAttribution("sdkVersion:'(.*?)'");
        targetSdkVersion = getAttribution("targetSdkVersion:'(.*?)'");
        versionCode= getAttribution("versionCode='(.*?)'");
        launchableActivity=getAttribution("launchable-activity: name='([\\w\\.]+)'");
        permissions=getAttributions("uses-permission: name='(.*)'");
    }

    private String getAttribution(String regex)
    {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(allManifest);
        if(matcher.find()){
            return matcher.group(1);
        }
        return null;
    }

    private List<String> getAttributions(String regex)
    {
        List<String>permissions=new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(allManifest);
        while (matcher.find()){
            permissions.add(matcher.group(1));
        }
        return permissions;
    }

    public boolean newerThan(ApkInfo apkInfo){
        return Integer.parseInt(versionCode)>Integer.parseInt(apkInfo.versionCode);
    }

    public String getAllManifest() {
        return allManifest;
    }

    public String getApkName() {
        return apkName;
    }

    public String getVersionName() {
        return versionName;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getSdkVersion() {
        return sdkVersion;
    }

    public String getTargetSdkVersion() {
        return targetSdkVersion;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public String getLaunchableActivity() {
        return launchableActivity;
    }

    public List<String> getPermissions() {
        return permissions;
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
                ", launchableActivity='" + launchableActivity + '\'' +
                ", permissions=" + permissions +
                '}';
    }
}
