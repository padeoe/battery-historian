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

    public void installAPK(File apkFile) throws InstallFailureException {
         ADBTool.installApk(this.serialNumber,apkFile);
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

}

