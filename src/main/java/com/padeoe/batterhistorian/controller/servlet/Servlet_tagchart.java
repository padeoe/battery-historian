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
@WebServlet(urlPatterns = "/tagchart")
public class Servlet_tagchart extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
/*
* 调用String[][] MultipleAppForm (String appId, String tag)
  根据appId和tag，返回在此tag下，所有APP在所有平台下每个模块的耗电平均值
  返回值[i][j],[j0]是APP名称，后续的[j1][j2]...按照合计,cpu,radio,wake,wifi,gps,sensor,camera的顺序
  [i0]是此tag下所有app的平均值，[i0][j0]=”平均”，[i1]是测试应用的信息，[i2]之后随便排列同tag下的app数据。
* */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String data = "";
        response.setContentType("application/json;charset=utf-8");
        String appId = request.getParameter("appid");
        String tag = request.getParameter("tag");

        String[][] tempdata= {{"平均","23","31","32","35","31","34","32","30"},{"测试应用","21","32","36","43","32","31","32","31"},{"测试1","22","13","23","33","43","33","23","13"},{"测试1","25","11","27","31","23","23","26","21"}};
        // tempdata =  MultipleAppForm (appId,tag);

        if(tag.equals("社交软件")){
        }
else {
            tempdata[0][0]= "23";
        }

        data = "                                    <table width=\"100%\" class=\"table table-striped table-bordered table-hover\" id=\"dataTables-example2\">\n" +
                "                                        <thead>\n" +
                "                                        <tr>\n" +
                "                                            <th width=\"40\" align=\"center\">选择</th>\n" +
                "                                            <th>APP名称</th>\n" +
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
        for(String[] temp:tempdata){
            data = data+
                    "                                        <tr>\n" ;
                    if(i <3){
                        data = data +" <td align=\"center\"></td>\n";
                    }
                    else{
                        data = data +   "<td align=\"center\"><label><input name=\"datatables2\" type=\"radio\" value=\""+i+"\" /></label></td>\n";
                    }
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
