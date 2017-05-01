package com.padeoe.batterhistorian.pojo;

import com.padeoe.platformtools.ADBDevice;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by padeoe on 2017/4/28.
 */
@Entity
public class Device {
    private String serialNumber;
    private String brand;
    private String model;
    private String androidVersion;

    public Device() {
    }

    public Device(String serialNumber, String brand, String model) {
        this.serialNumber = serialNumber;
        this.brand = brand;
        this.model = model;
    }

    public Device(String serialNumber, String brand, String model, String androidVersion) {
        this.serialNumber = serialNumber;
        this.brand = brand;
        this.model = model;
        this.androidVersion = androidVersion;
    }

    @Id
    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
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

    public String getAndroidVersion() {
        return androidVersion;
    }

    public void setAndroidVersion(String androidVersion) {
        this.androidVersion = androidVersion;
    }
}
