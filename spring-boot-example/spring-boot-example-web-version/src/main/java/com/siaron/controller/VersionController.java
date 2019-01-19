package com.siaron.controller;

import com.siaron.version.ApiVersion;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xielongwang
 * @create 2019-01-199:35 AM
 * @email xielong.wang@nvr-china.com
 * @description
 */
@RestController
@RequestMapping("/{version}/test/")
public class VersionController {

    @ApiVersion(1)
    @RequestMapping("/one")
    public String getV1() {
        return "ok V1";
    }

    @ApiVersion(2)
    @RequestMapping("/one")
    public String getV2() {
        return "ok V2";
    }
}