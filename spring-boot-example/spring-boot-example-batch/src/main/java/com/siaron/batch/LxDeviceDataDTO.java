package com.siaron.batch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xielongwang
 * @create 2017-10-12 上午10:24
 * @email xielong.wang@nvr-china.com
 * @description 南京理学车辆运行实时数据模板
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LxDeviceDataDTO implements Serializable {

    private Long id;

    /**
     * 设备编号
     */
    private String deviceNum;

    /**
     * 车辆编号
     */
    private String licenseId;

    /**
     * 通讯时间
     */
    private Date communicationTime;

    /**
     * 定位时间
     */
    private Date locateDate;

    /**
     * 定位状态 0无效1有效
     */
    private Integer locateStatus;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 纬度
     */
    private String latitude;

    /**
     * 车辆电压 V
     */
    private Double vehicleVoltage;

    /**
     * 电池电压 V
     */
    private Double cellVoltage;

    /**
     * 天线断线 0:未断线 1：断线
     */
    private Integer antenna;

    /**
     * 开盖锁车 0:未锁 1：开盖锁车
     */
    private Integer capLock;


    /**
     * 主电源低电压 0:正常 1：报警
     */
    private Integer mainPowerLowVoltage;


    /**
     * 远程锁车 0:解锁 1：锁车
     */
    private Integer lockStatus;

    /**
     * GPS被拆或无外电 0: 未被拆或有电 1：被拆或无外电
     */
    private Integer dismantle;

    /**
     * 备用电池低电压 0:正常 1：报警
     */
    private Integer standByLowVoltage;

    /**
     * 密码激活 0:非激活 1：激活
     */
    private Integer cipher;

    /**
     * 永久解锁 0:非永久解锁 1：永久解锁
     */
    private Integer perpetualLock;

    /**
     * 点火 0:未点火 1:点火
     */
    private Integer ignition;

    /**
     * 平台记录时间
     */
    private Date recordTime;

    /**
     * 经纬度换算出来的地理位置
     */
    private String address;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 城市编码
     */
    private String citycode;

}
