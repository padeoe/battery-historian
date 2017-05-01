package com.padeoe.batterhistorian.controller;

import com.padeoe.batterhistorian.dao.AppRepository;
import com.padeoe.batterhistorian.pojo.App;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by padeoe on 2017/4/24.
 */

@Controller
@RequestMapping(path="/app")
public class AppController {

    @Autowired
    private AppRepository appRepository;

    @GetMapping(path="/add") // Map ONLY GET Requests
    public @ResponseBody
    String addNewUser (@RequestParam String name
            , @RequestParam String packageName
    ,@RequestParam String versionCode
    ,@RequestParam String versionName
    ,@RequestParam String description) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request
        App app = new App(packageName,Integer.parseInt(versionCode),name,versionName,description);
        appRepository.save(app);
        return "Saved";
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<App> getAllApps() {
        return appRepository.findAll();
    }

    @GetMapping(path="/myapp")
    public @ResponseBody Iterable<App> getMyApp() {
        return appRepository.myApp();
    }
}
