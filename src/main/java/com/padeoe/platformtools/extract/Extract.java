package com.padeoe.platformtools.extract;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zafara on 2017/4/4.
 */


/**
 * 使用方法
 * 先用Read_Batterystats中setNameAndPath把目标文件和地址修正好
 * 在用它的read获得数据list
 * 把这个list传给 Find_topAppUid的findUid方法，获得topApp的uid
 * 把uid和数据list传给 parse_batteryStatics 的setinfo()
 *
 * 通过执行parse_batteryStatics的parsePoweruse方法，可以获得APP耗电情况，具体信息见类中方法注释
 * 通过执行parse_batteryStatics的parseMobile_ms_Per_Packet方法，可以获得APP的MMPP数据
 */
 class Extract {
   static Read_Batterystats rb = new Read_Batterystats();
    static Find_topAppUid ft = new Find_topAppUid();
    static parse_batteryStatics pb = new parse_batteryStatics();

    static List<String> rawFile;
    static String uid;

    static Map<String,Double> powerUseResult = new HashMap<>();
    static double mmppResult;


    public static void extract(String name,String path) {
        rb.setNameAndPath(name,path);
       rawFile = rb.read();
       uid = ft.findUid(rawFile);
        //System.out.print(uid);

        pb.setinfo(uid,rawFile);
        powerUseResult = pb.parsePoweruse();//得到一个app的所有poweruse的信息
        //显示poweruse数据
        for (Map.Entry<String,Double> temp: powerUseResult.entrySet()){
            System.out.println(temp.getKey()+" : "+temp.getValue());
        }
        mmppResult = pb.parseMobile_ms_Per_Packet();
        System.out.println("mobile ms per packet: "+mmppResult);
    }

    public static Map<String,Double> getpowerUseResult(){
        return powerUseResult;
    }

    public static double getmmppResult(){
        return mmppResult;
    }

    public static void main(String[] args) {
        Extract.extract("b.txt","D:\\Android\\sdk\\platform-tools");
    }
}
