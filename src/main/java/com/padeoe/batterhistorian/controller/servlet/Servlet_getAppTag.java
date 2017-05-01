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
@WebServlet(urlPatterns = "/getAppTag")
public class Servlet_getAppTag extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //多应用对比图表获得tag
        String data = "";
        String appId = request.getParameter("appId");

        String tagdata[] = {"社交软件","即时通讯软件","直播软件"};
        //tagdata = GetAppTag(appId);
        response.setContentType("application/json;charset=utf-8");
        data="选择TAG\n" +
                "                                            <select id=\"tag_choose\">\n" +
                "                                                <option value=\"1\" selected=\"selected\">选择TAG</option>\n";
                int i  = 2;
                for(String temp:tagdata){
                    data = data + "<option value=\""+ i +"\" >" + temp + "</option>\n";
                    i++;
              }
                data = data+"</select>";
        response.getWriter().write(data);
    }
}
