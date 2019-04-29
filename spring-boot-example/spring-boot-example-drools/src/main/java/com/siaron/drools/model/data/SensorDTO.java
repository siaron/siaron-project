package com.siaron.drools.model.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author xielongwang
 * @create 2019-04-2914:28
 * @email xielong.wang@nvr-china.com
 * @description
 */
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class SensorDTO {

    /**
     * 设备号
     */
    private String deviceNum;

    /**
     * 版本号
     */
    private String versionNum;

    /**
     * 接收时间
     */
    private Date receiverDate;

    /**
     * 定位时间
     */
    private Date locateDate;

    /**
     * 工作状态
     */
    private String accStatus;

    /**
     * gps速度
     */
    private Long gpsSpeed;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 纬度
     */
    private String latitude;

    /**
     * 发动机转速
     */
    private Long engineRotate;

    /**
     * 发动机水温
     */
    private Long engineTemp;

    /**
     * 发动机机油压力
     */
    private Long oilPress;
}