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
@WebServlet(urlPatterns = "/multipleVersionchart")
public class Servlet_multipleVersionchart extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String data = "";
        response.setContentType("application/json;charset=utf-8");
        String appId = request.getParameter("appId");
        String platform = request.getParameter("platform");

        String datatemp[][]={{"201023","43","14","23","22","14","25","23","14"},{"201026","48","12","21","19","12","23","21","17"}};//test
        // datatemp = AppVersionDetail(appId,platform);
        System.out.print("test");
        if(platform.equals("三星")){
            datatemp[0][1] = "52";
        }
        else{
            datatemp[0][1] = "42";
        }

        data = "                                    <table width=\"100%\" class=\"table\" id=\"multipleVersionchart_table\">\n" +
                "                                        <thead>\n" +
                "                                        <tr>\n" +
                "                                            <th>版本</th>\n" +
                "                                            <th>整体</th>\n" +
                "                                            <th>CPU</th>\n" +
                "                                            <th>radio</th>\n" +
                "                                            <th>wake</th>\n" +
                "                                            <th>wifi</th>\n" +
                "                                            <th>gps</th>\n" +
                "                                            <th>sensor</th>\n" +
                "                                            <th>camera</th>\n" +
                "                                        </tr>\n" +
                "                                        </thead>\n" +
                "                                        <tbody>\n" ;
        int i = 1;
        for(String[] temp:datatemp){
            data = data+
                    "                                        <tr>\n" ;
            for(String temp2: temp){
                data = data +                    "<td>"+temp2+"</td>\n";
            }
            data = data+ "  </tr>\n" ;
            i++;
        }
        data = data+
                "                                        </tbody>\n" +
                "                                    </table>";
        response.getWriter().write(data);
    }
}

