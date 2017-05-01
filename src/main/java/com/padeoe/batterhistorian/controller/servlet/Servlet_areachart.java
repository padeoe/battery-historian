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

    /**
     * 调用String[][] AppVersionDetail(String appId, String platformId)
     用于传递一个app在不同平台下不同版本下的各模块电量消耗变更
     返回值[i][j],[j0]是总电量，后续的[j1][j2]...按照cpu,radio,wake,wifi,gps,sensor,camera顺序
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String appId = request.getParameter("appId");
        String platform = request.getParameter("platform");
        response.setContentType("application/json;charset=utf-8");

        String tempdata[][]={{"201023","43","14","23","22","14","25","23","14"},{"201026","48","12","21","19","12","23","21","17"}};//test
        // tempdata = AppVersionDetail(appId,platform);
        if(platform.equals("三星")){
            tempdata[0][1] = "52";
        }
        else{
            tempdata[0][1] = "42";
        }

        String data = "[";
        for(int i = 0;i<tempdata.length;i++) {
            data = data + "{\"x\":\"" + tempdata[i][0] + "\",\"a\":" + tempdata[i][1] + ",\"b\":"+tempdata[i][2]+ ",\"c\":"+tempdata[i][3]+ ",\"d\":"+tempdata[i][4]+ ",\"e\":"+tempdata[i][5]+ ",\"f\":"+tempdata[i][6]+ ",\"g\":"+tempdata[i][7]+ ",\"h\":"+tempdata[i][8]+"}";
            if(i+1<tempdata.length){
                data = data + ",";
            }
    }
        data = data+"]";


        response.getWriter().write(data);
    }
}
