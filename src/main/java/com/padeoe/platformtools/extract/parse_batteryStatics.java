package com.padeoe.platformtools.extract;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 根据topApp的Uid和Read_Batterystats获得的内容，得到文件中MMPP和电池耗用信息
 * Created by zafara on 2017/4/4.
 */
public class parse_batteryStatics {
    String uid = null;
    List<String> batterystats = null;
    String findkey = null;
    String MMPP = "Per-app mobile ms per packet:";
    String EPS = "Estimated power use (mAh):";

    public void setinfo(String uid,List<String> batterystats){
        this.uid = uid;
        this.batterystats = batterystats;
        findkey = "Uid "+uid;
    }

    //Radio-awake-time divided by packets sent. An efficient app will transfer all its traffic in batches, so the lower this number the better.
    // 返回一个double数据，为mmpp的值

    public double parseMobile_ms_Per_Packet(){
        double result = -1;
        boolean founded = false;
        for(String temp:batterystats){
            int test = 0;
            if(founded == false) {
                test = temp.indexOf(MMPP);
                if (test != -1) {
                    founded = true;
                }
                continue;
            }
                test = temp.indexOf(findkey);
                if (test == -1) continue;

                int start = temp.indexOf(':', test);
                int end = temp.indexOf('(', start);
                result = Double.parseDouble(temp.substring(start + 1, end));
                break;
        }
        return result;
    }

    /**
     * 返回map，String为key，double为数值。Key一共可能有：total,cpu,radio,wake,wifi,gps,sensor,camera这些项。真正返回的值中，可能会缺失几个key。比如app用不到camera，就没有camera耗电数据。
     *
     * @return
     */

    public Map<String,Double> parsePoweruse(){

        Map<String,Double> result = new HashMap<>();
        int start,end = 0;
        boolean founded = false;
        Pattern compile = Pattern.compile("(\\w+?)=([\\d\\.]+?) ");

        for(String temp:batterystats){
            int test = 0;
            if(founded == false) {
                test = temp.indexOf(EPS);
                if (test != -1) {
                    founded = true;
                }
                continue;
            }

            test= temp.indexOf(findkey);
            if (test == -1) continue;

            //总电量耗费能力
            start = temp.indexOf(':', test);
             end = temp.indexOf('(',start);
            double total = Double.parseDouble(temp.substring(start + 1, end));
            result.put("total",total);

            Matcher matcher = compile.matcher(temp.substring(end));
            while (matcher.find()) {
                result.put(matcher.group(1), Double.parseDouble(matcher.group(2)));
            }
            break;
        }
        return result;
    }
}
