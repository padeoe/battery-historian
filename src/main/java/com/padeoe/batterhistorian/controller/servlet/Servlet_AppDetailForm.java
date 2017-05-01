package com.padeoe.batterhistorian.controller.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by zafara on 2017/4/12.
 */

@WebServlet(urlPatterns = "/AppDetailForm")
public class Servlet_AppDetailForm extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String data = "";
        String appId = request.getParameter("appid");

        response.setContentType("application/json;charset=utf-8");
        String[][] tempdata= {{"苹果","23","31","32","35","31","34","32"},{"三星","21","32","36","43","32","31","32"},{"华为","22","13","23","33","43","33","23"},{"小米","25","11","27","31","23","23","26"}};
        // tempdata =  AppDetailForm (appId);

        data = " <table width=\"100%\" class=\"table table-striped table-bordered table-hover\" id=\"dataTables-example\">\n" +
                "                                        <thead>\n" +
                "                                        <tr>\n" +
                "                                            <th width=\"40\" align=\"center\">选择</th>\n" +
                "                                            <th>平台名称</th>\n" +
                "                                            <th>CPU</th>\n" +
                "                                            <th>radio</th>\n" +
                "                                            <th>wake</th>\n" +
                "                                            <th>wifi</th>\n" +
                "                                            <th>gps</th>\n" +
                "                                            <th>sensor</th>\n" +
                "                                            <th>camera</th>\n" +
                "                                        </tr>\n" +
                "                                        </thead>\n" +
                "                                        <tbody>\n";

        int i = 1;
        for(String[] temp:tempdata){
            data = data+
                    "                                        <tr>\n" +
                    "                                            <td align=\"center\"><label><input name=\"piechart\" type=\"radio\" value=\""+i+"\" /></label></td>\n" ;
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
