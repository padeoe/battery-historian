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
public class Servlet_choosePlatform1 extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    /**
     * 调用 GetAppPlatform(String appId)
     获得一个 app 所有测试过的平台名称，如”苹果5S_1号”
     此名称包括：品牌+型号+编号，既能把最描述的信息准确给用户，又能让数据库根据此文本查找到对应的平台
     */
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
