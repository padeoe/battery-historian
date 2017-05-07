package com.padeoe.platformtools.extract;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * 从具体地址获得txt文件
 * Created by zafara on 2017/4/4.
 */
public class Read_Batterystats {
    static private String pathOfBatterystats = "F:\\sdk\\platform-tools";
    static private String nameOfBatterystats = "batterystats.txt";
    static private String finalpath;

    //修改name为文件名称，path为路径
    public void setNameAndPath(String name,String path){
        if (name!=null){
            nameOfBatterystats = name;
        }
        if (path!=null){
            pathOfBatterystats = path;
        }
        finalpath = pathOfBatterystats+System.getProperty("file.separator")+nameOfBatterystats;

    }

//取batterystats文件，并返回每行组成的stringp[]
   public List<String> read(){
       List<String> stringList = null;
       try {
           stringList = Files.readAllLines(Paths.get(finalpath), StandardCharsets.UTF_8);
       } catch (IOException e) {
           e.printStackTrace();
       }
       return stringList;
   }
}
