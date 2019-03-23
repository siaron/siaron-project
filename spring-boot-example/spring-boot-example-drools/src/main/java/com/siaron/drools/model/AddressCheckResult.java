package com.siaron.drools.model;

import lombok.Data;

/**
 * @author xielongwang
 * @create 2019-03-236:11 PM
 * @email xielong.wang@nvr-china.com
 * @description
 */
@Data
public class AddressCheckResult {
    /**
     * true:通过校验；false：未通过校验
     */
    private boolean postCodeResult = false;
}