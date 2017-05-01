package com.padeoe.batterhistorian.controller.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by zafara on 2017/4/10.
 */
@WebServlet(urlPatterns = "/barchart1")
public class Servlet_barchart1 extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    /**
     * 调用  String[][] AppDetailChart(String appId, boolean PlatFormOrModule,String detail)：
     用于传递一个APP在相同平台下不同模块的，或是相同模块下不同平台，最新版本APP的耗电量和所有版本APP耗电量的平均值
     PlatFormOrModule 是true就是同平台不同模块的信息，是false就是同模块不同平台的信息。id用于寻找APP，detail用于传平台ID或者模块名称
     返回值[i][j]，[j0]是平台/模块名称，[j1]是最新版本APP耗电量，[j2]是所有版本平均耗电量

     */

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String appId = request.getParameter("appId");
        String type = request.getParameter("type");
        String detail = request.getParameter("detail");
        response.setContentType("application/json;charset=utf-8");

        String tempdata[][]={{"cpu","2500","2430"},{"radio","2321","2230"},{"wake","2323","2220"},{"wifi","2336","2258"},{"gps","2191","2250"},{"sensor","2241","2460"},{"camera","2141","2250"}};//test
        // tempdata = AppDetailChart(appId, true,detail)：

        if(type.equals("platform")){

            switch (detail) {//test
                case("1号"):
                    tempdata[0][1] = "2400";
                    break;
                case("2号"):
                    tempdata[0][1] = "2200";
                    break;
            }
        }
        else{
            // tempdata = AppDetailChart(appId, false,detail)：

        }


        String data = "[";
        for(int i = 0;i<tempdata.length;i++) {
            data = data + "{\"x\":\"" + tempdata[i][0] + "\",\"y\":" + tempdata[i][1] + ",\"ya\":"+tempdata[i][2]+"}";
            if(i+1<tempdata.length){
                data = data + ",";
            }
        }
        data = data+"]";


        response.getWriter().write(data);
    }
}
