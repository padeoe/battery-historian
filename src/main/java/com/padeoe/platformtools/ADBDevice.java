package com.padeoe.platformtools;

import java.io.*;

/**
 * 该类描述了adb获得的安卓手机基本信息，包括序列号，状态，品牌，型号
 * Created by padeoe on 2017/4/20.
 */
public class ADBDevice {
    private String serialNumber;
    private State state;
    private String brand;
    private String model;

    public ADBDevice() {
    }

    public ADBDevice(String serialNumber, State state, String brand, String model) {
        this.serialNumber = serialNumber;
        this.state = state;
        this.brand = brand;
        this.model = model;
    }

    public  String getProp(String propName) throws IOException, EnvironmentNotConfiguredException {
        return ADBTool.getProp(this.serialNumber,propName);
    }

    public boolean installAPK(File apkFile) throws EnvironmentNotConfiguredException, NoDeviceException {
        String []commands=new String[]{"adb","-s",this.serialNumber,"install","-r",apkFile.getPath()};
        Runtime runtime = Runtime.getRuntime();
        Process process;
        try {
            process = runtime.exec(commands);
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader bf = new BufferedReader(isr);
            String line;
            while ((line = bf.readLine()) != null) {
             //   System.out.println(line);
                if(line.indexOf("Success")!=-1){
                    return true;
                }
                if(line.indexOf("Failure")!=-1){
                    return false;
                }
                if(line.indexOf("not found")!=-1){
                    process.destroy();
                    throw new NoDeviceException("设备未找到:"+line);
                }
            }
        } catch (IOException e) {
            throw new EnvironmentNotConfiguredException("android sdk环境变量未配置好", e);
        }
        try {
            process.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }

    public ADBDevice(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return "ADBDevice{" +
                "serialNumber='" + serialNumber + '\'' +
                ", state=" + state +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                '}';
    }

    public enum State{
        CONNECTED("device"),OFFLINE("offline"),NODEVICE("no device");
        private String state;
        State(String state) {
            this.state=state;
        }
        public static State fromString(String state) {
            for (State s: State.values()) {
                if (s.state.equals(state)) {
                    return s;
                }
            }
            return null;
        }
    }

    public static void main(String[] args) {
        try {
            new ADBDevice("cff038b5b02d3500").installAPK(new File("C:\\Users\\padeoe\\Desktop\\app-release.apk"));
        } catch (EnvironmentNotConfiguredException e) {
            e.printStackTrace();
        } catch (NoDeviceException e) {
            e.printStackTrace();
        }
    }
}

