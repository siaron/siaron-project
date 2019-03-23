package com.siaron.drools.web;

import com.siaron.drools.model.Address;
import com.siaron.drools.model.AddressCheckResult;
import org.kie.api.runtime.KieSession;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author xielongwang
 * @create 2019-03-236:12 PM
 * @email xielong.wang@nvr-china.com
 * @description
 */
@RestController
@RequestMapping("/drools")
public class DroolsController {

    @Resource
    private KieSession kieSession;

    @ResponseBody
    @RequestMapping("/address")
    public void test() {
        Address address = new Address();
        address.setPostcode("99425");

        AddressCheckResult result = new AddressCheckResult();
        kieSession.insert(address);
        kieSession.insert(result);
        int ruleFiredCount = kieSession.fireAllRules();
        System.out.println("触发了" + ruleFiredCount + "条规则");

        if (result.isPostCodeResult()) {
            System.out.println("规则校验通过");
        }
    }
}