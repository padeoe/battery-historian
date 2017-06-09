package com.padeoe.batterhistorian.controller;

import com.padeoe.batterhistorian.dao.DeviceRepository;
import com.padeoe.batterhistorian.pojo.App;
import com.padeoe.batterhistorian.pojo.Device;
import com.padeoe.batterhistorian.pojo.Record;
import com.padeoe.batterhistorian.pojo.Tag;
import com.padeoe.batterhistorian.service.AppService;
import com.padeoe.batterhistorian.service.DeviceService;
import com.padeoe.batterhistorian.service.RecordService;
import com.padeoe.platformtools.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by zafara on 2017/5/5
 */
@Controller
//@RequestMapping(path="/")
public class FrontendController {
    @Autowired
    private RecordService recordService;
    @Autowired
    private AppService appService;
    @Autowired
    private DeviceService deviceService;

    /**
     * 调用AppDetailForm(String appId)
     * 根据appid返回一个app在各个平台下所有模块的耗电量
     * 返回值[i][j],[j0]是平台，后续的[j1][j2]...按照cpu,radio,wake,wifi,gps,sensor,camera顺序
     */
    @PostMapping(path = "/UploadApp")
    public @ResponseBody
    String UploadApp(@RequestParam("file") MultipartFile file, @RequestParam String TagContent, @RequestParam String DetailContent, @RequestParam String usePlatform) throws IOException, EnvironmentNotConfiguredException, InterruptedException {
        String fileName = file.getOriginalFilename();

        byte[] bytes = file.getBytes();
        File apkFile = new File(fileName);
        BufferedOutputStream stream =
                new BufferedOutputStream(new FileOutputStream(apkFile));
        stream.write(bytes);
        stream.close();
        ApkReader apkReader = new ApkReader(apkFile);
        ApkInfo apkInfo = apkReader.getApkInfo();

        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    List<ADBDevice> devices = ADBTool.getDevices();
                    devices.stream().forEach(adbDevice -> {
                        try {
                            Device device = Device.fromADBDevice(adbDevice);
                            if (usePlatform.indexOf(device.getSerialNumber()) != -1) {
                                adbDevice.installAPK(apkFile);
                                App app = App.fromApkInfo(apkInfo);
                                app.setTags(new HashSet<Tag>(Arrays.asList(TagContent.split(";")).stream().map(tagString -> new Tag(tagString)).collect(Collectors.toList())));
                                app.setDescription(DetailContent);
                                //   appService.save(app);
                                recordService.testApp(device, app, apkInfo);
                            }
                        } catch (InstallFailureException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (EnvironmentNotConfiguredException e) {
                            e.printStackTrace();
                        } catch (StatsInfoNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                } catch (EnvironmentNotConfiguredException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        return "正在安装与测试";
    }


    @GetMapping(path = "/AppDetailForm")
    public @ResponseBody
    String AppDetailForm(@RequestParam String appId) {
        String data = "";
        String[][] tempdata = {{"苹果", "23", "31", "32", "35", "31", "34", "32"}};

        int j = 0;
        Iterator<Record> iterator = recordService.getPowerByAppId(appId).iterator();
        while (iterator.hasNext()) {
            Record record = iterator.next();
            tempdata[j][0] = record.getDevice().getBrand();
            tempdata[j][1] = String.valueOf(record.getCpuPower());
            tempdata[j][2] = String.valueOf(record.getRadioPower());
            tempdata[j][3] = String.valueOf(record.getWakePower());
            tempdata[j][4] = String.valueOf(record.getWifiPower());
            tempdata[j][5] = String.valueOf(record.getGpsPower());
            tempdata[j][6] = String.valueOf(record.getSensorPower());
            tempdata[j][7] = String.valueOf(record.getCamerPower());
            j++;
        }


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
        for (String[] temp : tempdata) {
            data = data +
                    "                                        <tr>\n" +
                    "                                            <td align=\"center\"><label><input name=\"piechart\" type=\"radio\" value=\"" + i + "\" /></label></td>\n";
            for (String temp2 : temp) {
                data = data + "<td>" + temp2 + "</td>\n";
            }
            data = data + "  </tr>\n";
            i++;
        }
        data = data +
                "                                        </tbody>\n" +
                "                                    </table>";

        return data;
    }

    /**
     * 调用String[][] AppVersionDetail(String appId, String platformId)
     * 用于传递一个app在不同平台下不同版本下的各模块电量消耗变更
     * 返回值[i][j],[j0]是总电量，后续的[j1][j2]...按照cpu,radio,wake,wifi,gps,sensor,camera顺序
     */
    @GetMapping(path = "/areachart")
    public @ResponseBody
    String areachart(@RequestParam String appId, @RequestParam String platform) {
        String tempdata[][] = {{"0", "0", "0", "0", "0", "0", "0", "0", "0"}};//test

        Iterator<Record> iterator = recordService.getAppPowerVersionLineWithDeviceName(appId, platform).iterator();

        int j = 0;
        while (iterator.hasNext()) {
            Record record = iterator.next();
            Device tmpDevice = record.getDevice();
            String deviceName = tmpDevice.getBrand() + " " + tmpDevice.getModel() + " " + tmpDevice.getAndroidVersion();
            tempdata[j][0] = record.getApp().getVersionName();
            tempdata[j][1] = String.valueOf(record.getCpuPower());
            tempdata[j][2] = String.valueOf(record.getRadioPower());
            tempdata[j][3] = String.valueOf(record.getWakePower());
            tempdata[j][4] = String.valueOf(record.getWifiPower());
            tempdata[j][5] = String.valueOf(record.getGpsPower());
            tempdata[j][6] = String.valueOf(record.getSensorPower());
            tempdata[j][7] = String.valueOf(record.getCamerPower());
            j++;

        }


        String data = "[";
        for (int i = 0; i < tempdata.length; i++) {
            data = data + "{\"x\":\"" + tempdata[i][0] + "\",\"a\":" + tempdata[i][1] + ",\"b\":" + tempdata[i][2] + ",\"c\":" + tempdata[i][3] + ",\"d\":" + tempdata[i][4] + ",\"e\":" + tempdata[i][5] + ",\"f\":" + tempdata[i][6] + ",\"g\":" + tempdata[i][7] + ",\"h\":" + tempdata[i][8] + "}";
            if (i + 1 < tempdata.length) {
                data = data + ",";
            }
        }
        data = data + "]";


        return data;
    }


    /**
     * 调用  String[][] AppDetailChart(String appId, boolean PlatFormOrModule,String detail)：
     * 用于传递一个APP在相同平台下不同模块的，或是相同模块下不同平台，最新版本APP的耗电量和所有版本APP耗电量的平均值
     * PlatFormOrModule 是true就是同平台不同模块的信息，是false就是同模块不同平台的信息。id用于寻找APP，detail用于传平台ID或者模块名称
     * 返回值[i][j]，[j0]是平台/模块名称，[j1]是最新版本APP耗电量，[j2]是所有版本平均耗电量
     */
    @GetMapping(path = "/barchart1")
    public @ResponseBody
    String barchart1(@RequestParam String appId, @RequestParam String type, @RequestParam String detail) {
        String tempdata[][] = {{"cpu", "2500", "2430"}, {"radio", "2321", "2230"}, {"wake", "2323", "2220"}, {"wifi", "2336", "2258"}, {"gps", "2191", "2250"}, {"sensor", "2241", "2460"}, {"camera", "2141", "2250"}};//test
        // tempdata = AppDetailChart(appId, true,detail)：

        if (type.equals("platform")) {
            Iterator<Record> iterator = recordService.getAppPowerVersionLine(appId, type).iterator();

            Record record_new = iterator.next();
            Record record_old = iterator.next();

            tempdata[0][0] = "cpu";
            tempdata[0][1] = String.valueOf(record_new.getCpuPower());
            tempdata[0][2] = String.valueOf(record_old.getCpuPower());

            tempdata[0][0] = "radio";
            tempdata[0][1] = String.valueOf(record_new.getRadioPower());
            tempdata[0][2] = String.valueOf(record_old.getRadioPower());

            tempdata[0][0] = "wake";
            tempdata[0][1] = String.valueOf(record_new.getWakePower());
            tempdata[0][2] = String.valueOf(record_old.getWakePower());

            tempdata[0][0] = "wifi";
            tempdata[0][1] = String.valueOf(record_new.getWifiPower());
            tempdata[0][2] = String.valueOf(record_old.getWifiPower());

            tempdata[0][0] = "gps";
            tempdata[0][1] = String.valueOf(record_new.getGpsPower());
            tempdata[0][2] = String.valueOf(record_old.getGpsPower());

            tempdata[0][0] = "sensor";
            tempdata[0][1] = String.valueOf(record_new.getSensorPower());
            tempdata[0][2] = String.valueOf(record_old.getSensorPower());

            tempdata[0][0] = "camera";
            tempdata[0][1] = String.valueOf(record_new.getCamerPower());
            tempdata[0][2] = String.valueOf(record_old.getCamerPower());

        } else {
            // tempdata = AppDetailChart(appId, false,detail)：

        }


        String data = "[";
        for (int i = 0; i < tempdata.length; i++) {
            data = data + "{\"x\":\"" + tempdata[i][0] + "\",\"y\":" + tempdata[i][1] + ",\"ya\":" + tempdata[i][2] + "}";
            if (i + 1 < tempdata.length) {
                data = data + ",";
            }
        }
        data = data + "]";


        return data;
    }

    /**
     * 调用 GetAppPlatform(String appId)
     * 获得一个 app 所有测试过的平台名称，如”苹果5S_1号”
     * 此名称包括：品牌+型号+编号，既能把最描述的信息准确给用户，又能让数据库根据此文本查找到对应的平台
     */
    @GetMapping(path = "/choosePlatform2")
    public @ResponseBody
    String choosePlatform2(@RequestParam String appId) {
        String data = "";
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("                                            <select id=\"choosePlatform_2\">\n");
        stringBuffer.append("                                                <option value=\"1\" selected=\"selected\">选择平台</option>\n");

        List<Device> devices = recordService.getTestedDevices(appId);
        int i = 2;
        for (Device device : devices) {
            stringBuffer.append("<option value=\"" + i + "\" >" + device.getBrand() + " " + device.getModel() + " " + device.getAndroidVersion() + "</option>\n");
            i++;
        }
        stringBuffer.append("</select>");
        return stringBuffer.toString();

    }

    @GetMapping(path = "/choosePlatform1")
    public @ResponseBody
    String choosePlatform1(@RequestParam String appId) {
        String data = "";
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("                                            <select id=\"choosePlatform_1\">\n");
        stringBuffer.append("                                                <option value=\"1\" selected=\"selected\">选择平台</option>\n");

        List<Device> devices = recordService.getTestedDevices(appId);
        int i = 2;
        for (Device device : devices) {
            stringBuffer.append("<option value=\"" + i + "\" >" + device.getBrand() + " " + device.getModel() + " " + device.getAndroidVersion() + "</option>\n");
            i++;
        }
        stringBuffer.append("</select>");
        return stringBuffer.toString();

/*        String tempdata[] = {"小米","三星","华为"};
        //tagdata =
        // (appId);
        data="" +
                "                                            <select id=\"choosePlatform_1\">\n" +
                "                                                <option value=\"1\" selected=\"selected\">选择平台</option>\n";
        int i  = 2;

        for(String temp:tempdata){
            data = data + "<option value=\""+ i +"\" >" + temp + "</option>\n";
            i++;
        }
        data = data+"</select>";
       return data;*/
    }

    /**
     * 调用String[] GetAppTag(String appId)
     * 获得一个app所有标签，且获得的是标签内容，如“社交软件”，“游戏”等等
     */
    @GetMapping(path = "/getAppTag")
    public @ResponseBody
    String getAppTag(@RequestParam String appId) {
//多应用对比图表获得tag
        List<Tag> tags = appService.GetAppTags(appId);
        String data = "";
        String tempdata[] = {"社交软件", "即时通讯软件", "直播软件"};
        //tempdata = GetAppTag(appId);
        data = "选择TAG\n" +
                "                                            <select id=\"tag_choose\">\n" +
                "                                                <option value=\"1\" selected=\"selected\">选择TAG</option>\n";
        int i = 2;
        StringBuffer stringBuffer = new StringBuffer();
        for (Tag tag : tags) {
            stringBuffer.append("<option value=\"" + i + "\" >" + tag + "</option>\n");
            i++;
        }
        stringBuffer.append("</select>");
/*        for(String temp:tempdata){
            data = data + "<option value=\""+ i +"\" >" + temp + "</option>\n";
            i++;
        }*/
        data = data + "</select>";
        return stringBuffer.toString();
    }

    /**
     * 调用  String[][] GetMSPP(String appId)
     * 获得所有平台下一个app的Per-app mobile ms per packet数据
     * [j0]为平台名称，[j1]为数据
     */
    @GetMapping(path = "/getMSPP")
    public @ResponseBody
    String getMSPP(@RequestParam String appId) {
        Iterable<Record> powerByAppId = recordService.getPowerByAppId(appId);
        String datatemp[][] = {{"华为", "1.3"}, {"小米", "1.2"}};
        // datatemp=GetMSPP(appId);

        String data = "[";
        StringBuffer stringBuffer = new StringBuffer();
        Iterator<Record> recordIterator = powerByAppId.iterator();
        stringBuffer.append(recordIterator.next());
        while (recordIterator.hasNext()) {
            Record record = recordIterator.next();
            stringBuffer.append(",");
            stringBuffer.append("{\"x\":\"" + record.getDevice().getBrand() + " " + record.getDevice().getModel() + "\",\"y\":" + record.getMsapp() + "}");
        }
/*        for(int i = 0;i<datatemp.length;i++) {
            data = data + "{\"x\":\"" + datatemp[i][0] + "\",\"y\":" + datatemp[i][1] + "}";
            if(i+1<datatemp.length){
                data = data + ",";
            }
        }*/
        stringBuffer.append("]");
        data = data + "]";

        return stringBuffer.toString();
    }

    /**
     * 调用String[][] GetAllPlatformDetail()
     * 获得所有注册的平台信息
     * 目前信息有[j0]平台名称 [j1]平台描述
     * 后台还能获得到什么平台信息，可以在这边修改还能显示啥
     */
    @GetMapping(path = "/getPlatformData")
    public @ResponseBody
    String getPlatformData() throws IOException, EnvironmentNotConfiguredException {
        String data = "";

        String[][] tempdata = {{"苹果5S", "32M内存"}, {"小米", "62M内存"}};
        Iterable<Device> allDevices = deviceService.detectAllDeviceConnected();
        Iterator<Device> iterator = allDevices.iterator();


        data = "   <table width=\"100%\" class=\"table\" id=\"choosePlatformForm\">\n" +
                "                                    <thead>\n" +
                "                                    <tr>\n" +
                "                                        <th width=\"45\" align=\"center\">选择</th>\n" +
                "                                        <th width=\"200\">序号</th>\n" +
                "                                        <th width=\"200\">平台名称</th>\n" +
                "                                        <th width=\"200\">型号</th>\n" +
                "                                        <th width=\"200\">安卓版本</th>\n" +
                "                                    </tr>\n" +
                "                                    </thead>\n" +
                "                                    <tbody>";

        int i = 0;
        while (iterator.hasNext()) {
            data = data +
                    "                                        <tr>\n" +
                    "                                               <td align=\"center\"><label><input name=\"Platform\" type=\"checkbox\" value=\"1\"  checked=\"checked\" /></label> </td>\n";

  /*          iterator.forEachRemaining(device -> {
                data = data+ "<td>" + device.getBrand() + "</td>\n";
            });*/

            Device device = iterator.next();
            data = data + "<td>" + device.getSerialNumber() + "</td>\n";
            data = data + "<td>" + device.getBrand() + "</td>\n";
            data = data + "<td>" + device.getModel() + "</td>\n";
            data = data + "<td>" + device.getAndroidVersion() + "</td>\n";
            data = data + "  </tr>\n";
            i++;
        }
        data = data +
                "                                        </tbody>\n" +
                "                                    </table>";

        return data;
    }

    /*
调用  String[][] AppVersionDetail(String appId, String platformId)
  用于传递一个app在不同平台下不同版本下的各模块电量消耗变更
  返回值[i][j],[j0]是总电量，后续的[j1][j2]...按照总电量,cpu,radio,wake,wifi,gps,sensor,camera顺序
 */
    @GetMapping(path = "/multipleVersionchart")
    public @ResponseBody
    String multipleVersionchart(@RequestParam String appId, @RequestParam String platform) {
        String data = "";
        Iterable<Record> appPowerVersionLine = recordService.getAppPowerVersionLineWithDeviceName(appId, platform);

/*        String datatemp[][]={{"201023","43","14","23","22","14","25","23","14"},{"201026","48","12","21","19","12","23","21","17"}};//test
        // datatemp = AppVersionDetail(appId,platform);
        System.out.print("test");
        if(platform.equals("三星")){
            datatemp[0][1] = "52";
        }
        else{
            datatemp[0][1] = "42";
        }*/

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
                "                                        <tbody>\n";
/*        int i = 1;
        for(String[] temp:datatemp){
            data = data+
                    "                                        <tr>\n" ;
            for(String temp2: temp){
                data = data +                    "<td>"+temp2+"</td>\n";
            }
            data = data+ "  </tr>\n" ;
            i++;
        }*/

        for (Record record : appPowerVersionLine) {
            data = data +
                    "                                        <tr>\n";

            data = data + "<td>" + record.getApp().getVersionName() + "</td>\n";
            data = data + "<td>" + record.allPower() + "</td>\n";
            data = data + "<td>" + record.getCpuPower() + "</td>\n";
            data = data + "<td>" + record.getRadioPower() + "</td>\n";
            data = data + "<td>" + record.getWakePower() + "</td>\n";
            data = data + "<td>" + record.getWifiPower() + "</td>\n";
            data = data + "<td>" + record.getGpsPower() + "</td>\n";
            data = data + "<td>" + record.getSensorPower() + "</td>\n";
            data = data + "<td>" + record.getCamerPower() + "</td>\n";
            data = data + "  </tr>\n";
        }
        data = data +
                "                                        </tbody>\n" +
                "                                    </table>";
        return data;
    }

    /*
    调用String[][] SearchApp(String detail)
    获得所有与查找内容相符的内容。根据关键词，查找名称中含有关键词的应用，并返回信息。
    [j0]为应用ID，[j1]为应用名称，[j2]为最新版本号，[j3]为详细描述
     */
    @GetMapping(path = "/searchapp")
    public @ResponseBody
    String searchapp(@RequestParam String detail) {
        String data = "";
        System.out.println(detail);
        List<App> apps = appService.search(detail);
        // String tempdata[][] = {{"32123","拍拍神器","202312314","一款用于自拍的APP"},{"32125","嘟嘟噜","142312314","一款用于看番的APP"}};
        //tempdata = SearchApp(detail);

        data = data + "              <table width=\"100%\" class=\"table\" id=\"searchTable\">\n" +
                "                                    <thead>\n" +
                "                                    <tr>\n" +
                "                                        <th width=\"45\" align=\"center\">选择</th>\n" +
                "                                        <th width=\"100\">ID</th>\n" +
                "                                        <th width=\"150\">APP应用名称</th>\n" +
                "                                        <th width=\"100\">最新版本号</th>\n" +
                "                                        <th width=\"100\">包名</th>\n" +
                "                                        <th>描述</th>\n" +
                "                                    </tr>\n" +
                "                                    </thead>\n" +
                "                                    <tbody>\n";

        int i = 1;
/*       for(String[] temp:tempdata){
            data = data+
                    "                                        <tr>\n" +
                    "                                        <td align=\"center\"><label><input name=\"ChooseApp\" type=\"radio\" value=\""+i+"\" /></label> </td>\n";

            for(String temp2: temp){
                data = data +                    "<td>"+temp2+"</td>\n";
            }
            data = data+ "  </tr>\n" ;
            i++;
        }*/

        for (App app : apps) {
            data = data +
                    "                                        <tr>\n" +
                    "                                        <td align=\"center\"><label><input name=\"ChooseApp\" type=\"radio\" value=\"" + i + "\" /></label> </td>\n";


            data = data + "<td>" + app.getId() + "</td>\n";
            data = data + "<td>" + app.getName() + "</td>\n";
            data = data + "<td>" + app.getVersionName() + "</td>\n";
            data = data + "<td>" + app.getPackageName() + "</td>\n";
            data = data + "<td>" + app.getDescription() + "</td>\n";
            data = data + "  </tr>\n";
            i++;
        }

        data = data +
                "                                    </tbody>\n" +
                "                                </table>\n";


        return data;
    }

    /*
* 调用String[][] MultipleAppForm (String appId, String tag)
  根据appId和tag，返回在此tag下，所有APP在所有平台下每个模块的耗电平均值
  返回值[i][j],[j0]是APP名称，后续的[j1][j2]...按照合计,cpu,radio,wake,wifi,gps,sensor,camera的顺序
  [i0]是此tag下所有app的平均值，[i0][j0]=”平均”，[i1]是测试应用的信息，[i2]之后随便排列同tag下的app数据。
* */
    @GetMapping(path = "/tagchart")
    public @ResponseBody
    String tagchart(@RequestParam String appid, @RequestParam String tag) {
        String data = "";

        String[][] tempdata = {{"平均", "23", "31", "32", "35", "31", "34", "32", "30"}, {"测试应用", "21", "32", "36", "43", "32", "31", "32", "31"}, {"测试1", "22", "13", "23", "33", "43", "33", "23", "13"}, {"测试1", "25", "11", "27", "31", "23", "23", "26", "21"}};
        // tempdata =  MultipleAppForm (appid,tag);

        if (tag.equals("社交软件")) {
        } else {
            tempdata[0][0] = "23";
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
                "                                        <tbody>\n";
        int i = 1;
        for (String[] temp : tempdata) {
            data = data +
                    "                                        <tr>\n";
            if (i < 3) {
                data = data + " <td align=\"center\"></td>\n";
            } else {
                data = data + "<td align=\"center\"><label><input name=\"datatables2\" type=\"radio\" value=\"" + i + "\" /></label></td>\n";
            }
            for (String temp2 : temp) {
                data = data + "<td>" + temp2 + "</td>\n";
            }
            data = data + "  </tr>\n";
            i++;
        }
        data = data +
                "                                        </tbody>\n" +
                "                                    </table>";
        return data;
    }
}
