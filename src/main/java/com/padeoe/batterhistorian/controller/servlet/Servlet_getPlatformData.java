package com.padeoe.batterhistorian.controller.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by zafara on 2017/5/1.
 */
@WebServlet(urlPatterns = "/Servlet_getPlatformData")
public class Servlet_getPlatformData extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    /**
     * 调用String[][] GetAllPlatformDetail()
     * 获得所有注册的平台信息
     * 目前信息有[j0]平台名称 [j1]平台描述
     * 后台还能获得到什么平台信息，可以在这边修改还能显示啥
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String data = "";
        response.setContentType("application/json;charset=utf-8");

        String[][] tempdata= {{"苹果5S","32M内存"},{"小米","62M内存"}};
        // tempdata =  GetAllPlatformDetail();

        data = "   <table width=\"100%\" class=\"table\" id=\"choosePlatformForm\">\n" +
                "                                    <thead>\n" +
                "                                    <tr>\n" +
                "                                        <th width=\"45\" align=\"center\">选择</th>\n" +
                "                                        <th width=\"200\">平台名称</th>\n" +
                "                                        <th>描述</th>\n" +
                "                                    </tr>\n" +
                "                                    </thead>\n" +
                "                                    <tbody>";

        int i = 0;
        for(String[] temp:tempdata){
            data = data+
                    "                                        <tr>\n" +
                    "                                               <td align=\"center\"><label><input name=\"Platform\" type=\"checkbox\" value=\"1\"  checked=\"checked\" /></label> </td>\n" ;
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
