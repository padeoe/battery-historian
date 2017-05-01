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
@WebServlet(urlPatterns = "/choosePlatform1")
public class choosePlatform1 extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //单应用不同版本耗电对比获得平台列表
        String data = "";
        String appId = request.getParameter("appId");

        String tempdata[] = {"小米","三星","华为"};
        //tagdata = GetAppPlatform(appId);
        response.setContentType("application/json;charset=utf-8");
        data="选择TAG\n" +
                "                                            <select id=\"choosePlatform_1\">\n" +
                "                                                <option value=\"1\" selected=\"selected\">选择平台</option>\n";
        int i  = 2;
        for(String temp:tempdata){
            data = data + "<option value=\""+ i +"\" >" + temp + "</option>\n";
            i++;
        }
        data = data+"</select>";
        response.getWriter().write(data);
    }
}
