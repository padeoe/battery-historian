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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String appId = request.getParameter("appId");
        String type = request.getParameter("type");
        String detail = request.getParameter("detail");
        response.setContentType("application/json;charset=utf-8");
        String datatemp[][]={{"cpu","2500","2430"},{"radio","2321","2230"},{"wake","2323","2220"},{"wifi","2336","2258"},{"gps","2191","2250"},{"sensor","2241","2460"},{"camera","2141","2250"}};//test


        System.out.print(appId);
        if(type.equals("platform")){
            // datatemp = AppDetailChart(appId, true,detail)：
            switch (detail) {//test
                case("1号"):
                    datatemp[0][1] = "2400";
                    break;
                case("2号"):
                    datatemp[0][1] = "2200";
                    break;
            }
        }
        else{
            // datatemp = AppDetailChart(appId, false,detail)：

        }


        String data = "[";
        for(int i = 0;i<datatemp.length;i++) {
            data = data + "{\"x\":\"" + datatemp[i][0] + "\",\"y\":" + datatemp[i][1] + ",\"ya\":"+datatemp[i][2]+"}";
            if(i+1<datatemp.length){
                data = data + ",";
            }
        }
        data = data+"]";


        response.getWriter().write(data);
    }
}
