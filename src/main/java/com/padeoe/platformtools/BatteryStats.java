package com.padeoe.platformtools;

import java.util.Map;

/**
 * 电量消耗的记录。对"batterystats.txt"文本的对象化表示读，记录了手机各个部件的耗电情况。
 */
public class BatteryStats {
    private double mmpp;
    private Map<MobileComponet, Double> powerDetail;

    public double getMmpp() {
        return mmpp;
    }

    public void setMmpp(double mmpp) {
        this.mmpp = mmpp;
    }

    public Map<MobileComponet, Double> getPowerDetail() {
        return powerDetail;
    }

    public void setPowerDetail(Map<MobileComponet, Double> powerDetail) {
        this.powerDetail = powerDetail;
    }

    public BatteryStats(double mmpp, Map<MobileComponet, Double> powerDetail) {
        this.mmpp = mmpp;
        this.powerDetail = powerDetail;
    }

    public double getComponetPower(MobileComponet mobileComponet) {
        return this.getPowerDetail().get(mobileComponet);
    }

    /**
     * 手机部件。电量分析基于一个假设，电量被手机部件消耗，可以由各个部件的消耗分析手机耗电情况。
     */
    public enum MobileComponet {
        CPU("cpu", 0), RADIO("radio", 1), WAKE("wake", 2), WIFI("wifi", 3), GPS("gps", 4), SENSOR("sensor", 5), CAMERA("camera", 6);
        private String name;
        private int index;

        MobileComponet(String name, int index) {
            this.name = name;
            this.index = index;
        }

        public static MobileComponet fromString(String name){
            for(MobileComponet mobileComponet:MobileComponet.values()){
                if(name.equals(mobileComponet.getName())){
                    return mobileComponet;
                }
            }
            return null;
        }


        public String getName() {
            return name;
        }

        public int getIndex() {
            return index;
        }
    }
}
