package com.siraon.controller;

import com.siraon.service.ICRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xielongwang
 * @create 2019-05-1313:32
 * @email xielong.wang@nvr-china.com
 * @description
 */
@RestController
public class PersonController {

    @Autowired
    ICRUDService icrudService;

    @GetMapping("/hbase/test")
    public String testCon() {
        return icrudService.test();
    }

}