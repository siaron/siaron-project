package com.siraon.controller;

import com.siraon.bean.Person;
import com.siraon.enums.LoadDataToDbEnum;
import com.siraon.service.ICRUDService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xielongwang
 * @create 2019-05-1313:32
 * @email xielong.wang@nvr-china.com
 * @description
 */
@Slf4j
@RestController
@RequestMapping("/hbase")
public class PersonController {

    @Autowired
    ICRUDService icrudService;

    @GetMapping("/test")
    public String testConnect(@ModelAttribute("message") String message, @ModelAttribute("attribute") String attribute) {
        log.info("This is ModelAttribute message value {} ", message);
        log.info("This is ModelAttribute attribute value {} ", attribute);
        return icrudService.testConnect();
    }

    @GetMapping("/create-table")
    public String createTable() {
        return icrudService.createPersonTable();
    }

    @PostMapping("/generate-data")
    public String loadDataToHBase(LoadDataToDbEnum loadDataToDbEnum) {
        return icrudService.loadDataToHBase(loadDataToDbEnum);
    }

    @GetMapping("/person/{startRowKey}/{stopRowKey}")
    public List<Person> queryPersons(
            @PathVariable("startRowKey") String startRowKey,
            @PathVariable("stopRowKey") String stopRowKey) {
        return icrudService.queryPersonList(startRowKey, stopRowKey);
    }

    @GetMapping("/person/{rowKey}")
    public Person queryPerson(
            @PathVariable("rowKey") String rowKey) {
        return icrudService.queryPerson(rowKey);
    }

    @DeleteMapping("/person/{rowKey}/del")
    public Person delPerson(
            @PathVariable("rowKey") String rowKey) {
        return icrudService.delPerson(rowKey);
    }


    @PutMapping("/person/{rowKey}/update")
    public Person updPerson(
            @PathVariable("rowKey") String rowKey) {
        return icrudService.updatePerson(rowKey);
    }
}