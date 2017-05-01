package com.padeoe.batterhistorian.controller.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by zafara on 2017/4/30.
 */
@WebServlet(urlPatterns = "/areachart")
public class Servlet_areachart extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String appId = request.getParameter("appId");
        String platform = request.getParameter("platform");
        response.setContentType("application/json;charset=utf-8");

        String datatemp[][]={{"201023","43","14","23","22","14","25","23","14"},{"201026","48","12","21","19","12","23","21","17"}};//test
        // datatemp = AppVersionDetail(appId,platform);
        if(platform.equals("三星")){
            datatemp[0][1] = "52";
        }
        else{
            datatemp[0][1] = "42";
        }

        String data = "[";
        for(int i = 0;i<datatemp.length;i++) {
            data = data + "{\"x\":\"" + datatemp[i][0] + "\",\"a\":" + datatemp[i][1] + ",\"b\":"+datatemp[i][2]+ ",\"c\":"+datatemp[i][3]+ ",\"d\":"+datatemp[i][4]+ ",\"e\":"+datatemp[i][5]+ ",\"f\":"+datatemp[i][6]+ ",\"g\":"+datatemp[i][7]+ ",\"h\":"+datatemp[i][8]+"}";
            if(i+1<datatemp.length){
                data = data + ",";
            }
    }
        data = data+"]";


        response.getWriter().write(data);
    }
}
