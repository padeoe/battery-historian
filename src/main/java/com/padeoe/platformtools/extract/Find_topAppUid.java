package com.padeoe.platformtools.extract;

import java.util.List;

/**
 * 通过Read_Batterystats返回的内容，找到topApp的uid
 * Created by zafara on 2017/4/4.
 */
public class Find_topAppUid {

    public String findUid(List<String> batterystats){
        String Uid = null;
        boolean findUid= false;
        for(String temp:batterystats){
            int start= temp.indexOf("top=");
            if (start == -1) continue;
            int end = temp.indexOf(':', start);
            Uid = temp.substring(start + 4, end);
            findUid= true;
            break;
        }
        if(findUid == false){
            System.out.println("未找到top UID");
        }
        return Uid;
    }

}
