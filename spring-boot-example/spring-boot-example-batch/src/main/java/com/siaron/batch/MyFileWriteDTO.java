package com.siaron.batch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author xielongwang
 * @create 2017-12-20 上午11:35
 * @email xielong.wang@nvr-china.com
 * @description 写入文件的模板, step1 和step2 都处理成此文件格式的模板
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyFileWriteDTO {

    private Long id;

    private String deviceNum;

    private String licenseId;

    private String versionNum;

    private Date locateDate;

    private Date recordTime;

    private Long totalDuration;

    private String accStatus;

    private String commStatus;

    private Long startTimes;

    private String locateStatus;

    private String amaplatitudeNum;

    private String amaplongitudeNum;

    private String address;

    private String province;

    private String city;

    private String citycode;

    private String elecStatus;

    private Long engineRotate;

    private Long engineTemperature;

    private Long oilTemperature;

    private String gprsSignal;

    private String gpsPowerStatus;

    private Long gpsSpeed;

    private Long gsmSignal;

    private Long imitate1;

    private Long imitate2;

    private Long oilLevel;

    private Long pressureMeter;

    private Long realTimeDuration;

}
