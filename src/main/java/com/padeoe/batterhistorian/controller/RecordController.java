package com.padeoe.batterhistorian.controller;

import com.padeoe.batterhistorian.dao.DeviceRepository;
import com.padeoe.batterhistorian.dao.RecordRepository;
import com.padeoe.batterhistorian.pojo.Device;
import com.padeoe.batterhistorian.pojo.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by padeoe on 2017/5/1.
 */
@Controller
@RequestMapping(path = "/record")
public class RecordController {
    @Autowired
    private RecordRepository recordRepository;
    @GetMapping(path="/all")
    public @ResponseBody
    Iterable<Record> getAllApps() {
        return recordRepository.findAll();
    }
}
