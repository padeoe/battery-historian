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
@WebServlet(urlPatterns = "/searchapp")
public class Servlet_searchApp extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
/*
调用String[][] SearchApp(String detail)
获得所有与查找内容相符的内容。根据关键词，查找名称中含有关键词的应用，并返回信息。
[j0]为应用ID，[j1]为应用名称，[j2]为最新版本号，[j3]为详细描述
 */

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         String data = "";
        response.setContentType("application/json;charset=utf-8");
        String detail = request.getParameter("detail");

        System.out.println(detail);
        String tempdata[][] = {{"32123","拍拍神器","202312314","一款用于自拍的APP"},{"32125","嘟嘟噜","142312314","一款用于看番的APP"}};
        //tempdata = SearchApp(detail);

        data =data+"              <table width=\"100%\" class=\"table\" id=\"searchTable\">\n" +
                "                                    <thead>\n" +
                "                                    <tr>\n" +
                "                                        <th width=\"45\" align=\"center\">选择</th>\n" +
                "                                        <th width=\"100\">ID</th>\n" +
                "                                        <th width=\"150\">APP应用名称</th>\n" +
                "                                        <th width=\"100\">最新版本号</th>\n" +
                "                                        <th>描述</th>\n" +
                "                                    </tr>\n" +
                "                                    </thead>\n" +
                "                                    <tbody>\n";

        int i = 1;
        for(String[] temp:tempdata){
            data = data+
                    "                                        <tr>\n" +
                    "                                        <td align=\"center\"><label><input name=\"ChooseApp\" type=\"radio\" value=\""+i+"\" /></label> </td>\n";

            for(String temp2: temp){
                data = data +                    "<td>"+temp2+"</td>\n";
            }
            data = data+ "  </tr>\n" ;
            i++;
        }
                data = data+
                "                                    </tbody>\n" +
                "                                </table>\n";


        response.getWriter().write(data);
    }
}
