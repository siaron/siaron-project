package com.siraon.service;

import com.siraon.bean.Person;
import com.siraon.enums.LoadDataToDbEnum;

import java.util.List;

/**
 * @author xielongwang
 * @create 2019-05-1313:13
 * @email xielong.wang@nvr-china.com
 * @description
 */
public interface ICRUDService {

    String testConnect();

    String createPersonTable();

    String loadDataToHBase(LoadDataToDbEnum loadDataToDbEnum);

    List<Person> queryPersonList(String startRowKey, String stopRowKey);

    Person queryPerson(String rowKey);

    Person updatePerson(String rowKey);

    Person delPerson(String rowKey);
}