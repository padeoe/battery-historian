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
@WebServlet(urlPatterns = "/Servlet_getMSPP")
public class Servlet_getMSPP extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    /**
     * 调用  String[][] GetMSPP(String appId)
     获得所有平台下一个app的Per-app mobile ms per packet数据
     [j0]为平台名称，[j1]为数据
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String appId = request.getParameter("appId");
        response.setContentType("application/json;charset=utf-8");
        String datatemp[][]={{"华为","1.3"},{"小米","1.2"}};
        // datatemp=GetMSPP(appId);

        String data = "[";
        for(int i = 0;i<datatemp.length;i++) {
            data = data + "{\"x\":\"" + datatemp[i][0] + "\",\"y\":" + datatemp[i][1] + "}";
            if(i+1<datatemp.length){
                data = data + ",";
            }
        }
        data = data+"]";

        response.getWriter().write(data);
    }
}
