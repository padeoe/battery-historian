package com.padeoe.batterhistorian.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by padeoe on 2017/4/28.
 */
@Entity
public class Record {
    private int id;
    private Date time;
    private String appId;
    private String deviceId;
    private String module;
    private String consumption;

    public Record() {
    }

    public Record(int id, Date time, String appId, String deviceId, String module, String consumption) {
        this.id = id;
        this.time = time;
        this.appId = appId;
        this.deviceId = deviceId;
        this.module = module;
        this.consumption = consumption;
    }

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

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getConsumption() {
        return consumption;
    }

    public void setConsumption(String consumption) {
        this.consumption = consumption;
    }
}

