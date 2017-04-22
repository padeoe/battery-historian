package com.padeoe.platformtools;

import javax.validation.constraints.NotNull;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by padeoe on 2017/4/20.
 */
public class ADBTool {
    /**
     * 获取当前adb连接的设备
     *
     * @return
     * @throws EnvironmentNotConfiguredException
     * @throws IOException
     */
    public static List<ADBDevice> getDevices() throws EnvironmentNotConfiguredException, IOException {
        List<ADBDevice> adbDevices = new LinkedList<>();
        String[] commands = new String[]{"adb", "devices", "-l"};
        Runtime runtime = Runtime.getRuntime();
        Process process;
        try {
            process = runtime.exec(commands);
        } catch (IOException e) {
            throw new EnvironmentNotConfiguredException("adb环境未配置好", e);
        }
        InputStream is = process.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader bf = new BufferedReader(isr);
        String firstLine = bf.readLine();
        if (firstLine == null || firstLine.indexOf("List of devices attached") == -1) {
            throw new EnvironmentNotConfiguredException("adb命令输出与预期不符");
        }
        String line;
        while ((line = bf.readLine()) != null) {
            ADBDevice device = parseLineGetDevice(line);
            if (device != null) {
                device.setBrand(device.getProp("ro.product.brand"));
                device.setModel(device.getProp("ro.product.model"));
                adbDevices.add(device);
            }
        }
        bf.close();
        return adbDevices;
    }


    private static ADBDevice parseLineGetDevice(String input) {
        Pattern pattern = Pattern.compile("(\\w+)\\s+(device|offline|no device)(\\sproduct:(.*)\\smodel:(.*)\\sdevice:(.*))*");
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            ADBDevice adbDevice = new ADBDevice();
            adbDevice.setSerialNumber(matcher.group(1));
            adbDevice.setState(ADBDevice.State.fromString(matcher.group(2)));
            return adbDevice;
        }
        return null;
    }

    /**
     * 获得安卓设备的某一项属性
     *
     * @param serialNumber 安卓设备序列号，不能为空
     * @param propName     属性名称，参见安卓prop文件定义
     * @return
     * @throws EnvironmentNotConfiguredException
     * @throws IOException
     */
    public static String getProp(@NotNull String serialNumber, String propName) throws EnvironmentNotConfiguredException, IOException {
        if (serialNumber == null) {
            throw new NullPointerException("输入的设备序列号为空");
        }
        String[] commands = {"adb", "-s", serialNumber, "shell", "getprop", propName};
        Runtime runtime = Runtime.getRuntime();
        Process process;
        try {
            process = runtime.exec(commands);

        } catch (IOException e) {
            throw new EnvironmentNotConfiguredException("adb环境未配置好", e);
        }
        InputStream is = process.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader bf = new BufferedReader(isr);
        String line;
        if ((line = bf.readLine()) != null) {
            return line;
        }
        return null;
    }

    public static boolean installApk(String serialNumber, File apk) {
        String []commands=new String[]{"adb","-s","serialNumber","install","-r",apk.getPath()};
        try {
            ProcessOutput processOutput = execute(commands);
            if(processOutput.errorString==null){
                return true;
            }
        } catch (EnvironmentNotConfiguredException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static Prop parseLineGetProp(String line) {
        Pattern pattern = Pattern.compile("\\[(.+)\\]:\\s*\\[(.*)\\]");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            return new Prop(matcher.group(1), matcher.group(2));
        }
        return null;
    }

    public static void main(String[] args) {
        try {
            getDevices().forEach(adbDevice -> System.out.println(adbDevice));
        } catch (EnvironmentNotConfiguredException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class Prop {
        public final String key;
        public final String value;

        public Prop(@NotNull String key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    public static ProcessOutput execute(String[] commands) throws EnvironmentNotConfiguredException, InterruptedException {
        Runtime runtime = Runtime.getRuntime();
        Process process;
        try {
            process = runtime.exec(commands);
        } catch (IOException e) {
            throw new EnvironmentNotConfiguredException("android sdk环境变量未配置好", e);
        }
        InputStream is = process.getInputStream();
        InputStream es = process.getErrorStream();
        InputStreamReader ssr = new InputStreamReader(is);
        InputStreamReader esr = new InputStreamReader(es);
        ProcessThread stdThread = new ProcessThread(ssr);
        ProcessThread errorThread = new ProcessThread(esr);
        stdThread.start();
        errorThread.start();
        stdThread.join();
        errorThread.join();
        String std = stdThread.getOutput().toString();
        String error = errorThread.getOutput().toString();
        return new ProcessOutput(std.equals("") ? null : std, error.equals("") ? null : error);
    }

    static class ProcessThread extends Thread {
        StringBuffer output = new StringBuffer();
        InputStreamReader inputStreamReader;

        @Override
        public void run() {
            super.run();
            int c;
            try {
                while ((c = inputStreamReader.read()) != -1) {
                    output.append((char) c);
                }
                System.out.println(output.toString());
                inputStreamReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public ProcessThread(InputStreamReader inputStreamReader) {
            this.inputStreamReader = inputStreamReader;
        }

        public StringBuffer getOutput() {
            return output;
        }
    }

    public static class ProcessOutput {
        String stdString;
        String errorString;

        public ProcessOutput(String stdString, String errorString) {
            this.stdString = stdString;
            this.errorString = errorString;
        }
    }
}
