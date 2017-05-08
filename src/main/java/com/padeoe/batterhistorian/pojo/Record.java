package com.padeoe.batterhistorian.pojo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by padeoe on 2017/4/28.
 */
@Entity
public class Record implements Serializable{
    private int id;
    private Date time;
    private App app;
    private Device device;
    private double msapp;
    private double cpuPower;
    private double radioPower;
    private double wakePower;
    private double wifiPower;
    private double gpsPower;
    private double sensorPower;
    private double camerPower;


    public Record() {
    }

    public Record(int id, Date time, App app, Device device, double msapp, double cpuPower, double radioPower, double wakePower, double wifiPower, double gpsPower, double sensorPower, double camerPower) {
        this.id = id;
        this.time = time;
        this.app = app;
        this.device = device;
        this.msapp = msapp;
        this.cpuPower = cpuPower;
        this.radioPower = radioPower;
        this.wakePower = wakePower;
        this.wifiPower = wifiPower;
        this.gpsPower = gpsPower;
        this.sensorPower = sensorPower;
        this.camerPower = camerPower;
    }

    public Record(Date time, App app, Device device, double msapp, double cpuPower, double radioPower, double wakePower, double wifiPower, double gpsPower, double sensorPower, double camerPower) {
        this.time = time;
        this.app = app;
        this.device = device;
        this.msapp = msapp;
        this.cpuPower = cpuPower;
        this.radioPower = radioPower;
        this.wakePower = wakePower;
        this.wifiPower = wifiPower;
        this.gpsPower = gpsPower;
        this.sensorPower = sensorPower;
        this.camerPower = camerPower;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public double getMsapp() {
        return msapp;
    }

    public void setMsapp(double msapp) {
        this.msapp = msapp;
    }

    public double getCpuPower() {
        return cpuPower;
    }

    public void setCpuPower(double cpuPower) {
        this.cpuPower = cpuPower;
    }

    public double getRadioPower() {
        return radioPower;
    }

    public void setRadioPower(double radioPower) {
        this.radioPower = radioPower;
    }

    public double getWakePower() {
        return wakePower;
    }

    public void setWakePower(double wakePower) {
        this.wakePower = wakePower;
    }

    public double getWifiPower() {
        return wifiPower;
    }

    public void setWifiPower(double wifiPower) {
        this.wifiPower = wifiPower;
    }

    public double getGpsPower() {
        return gpsPower;
    }

    public void setGpsPower(double gpsPower) {
        this.gpsPower = gpsPower;
    }

    public double getSensorPower() {
        return sensorPower;
    }

    public void setSensorPower(double sensorPower) {
        this.sensorPower = sensorPower;
    }

    public double getCamerPower() {
        return camerPower;
    }

    public void setCamerPower(double camerPower) {
        this.camerPower = camerPower;
    }

    public double allPower(){
        return getCpuPower()+getCamerPower()+getGpsPower()+getRadioPower()+
                getSensorPower()+getWakePower()+getWifiPower();
    }


}

