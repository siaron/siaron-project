package com.siaron.batch;

import org.springframework.batch.item.ItemProcessor;

/**
 * @author xielongwang
 * @create 2017-12-14 下午8:31
 * @email xielong.wang@nvr-china.com
 * @description 步骤2的处理器
 */
public class Step1Processor implements ItemProcessor<LxDeviceDataDTO, MyFileWriteDTO> {


    private static final String LX_VERSION_NUM = "lx01";

    private static final Long BETTEY_CONVERTER = 100L;
    @Override
    public MyFileWriteDTO process(LxDeviceDataDTO item) throws Exception {
        MyFileWriteDTO myFileWriteDTO = new MyFileWriteDTO();
        myFileWriteDTO.setId(item.getId());
        myFileWriteDTO.setDeviceNum(item.getDeviceNum());
        myFileWriteDTO.setLicenseId(item.getLicenseId());
        myFileWriteDTO.setVersionNum(LX_VERSION_NUM);
        myFileWriteDTO.setAddress(item.getAddress());
        myFileWriteDTO.setCity(item.getCity());
        myFileWriteDTO.setProvince(item.getProvince());
        myFileWriteDTO.setCitycode(item.getCitycode());
        myFileWriteDTO.setAmaplatitudeNum(item.getLatitude());
        myFileWriteDTO.setAmaplongitudeNum(item.getLongitude());
        myFileWriteDTO.setLocateDate(item.getLocateDate());
        myFileWriteDTO.setRecordTime(item.getRecordTime());

        myFileWriteDTO.setTotalDuration(0L);
        myFileWriteDTO.setStartTimes(0L);
        myFileWriteDTO.setRealTimeDuration(0L);
        myFileWriteDTO.setPressureMeter(0L);
        myFileWriteDTO.setEngineRotate(0L);
        myFileWriteDTO.setEngineTemperature(0L);
        myFileWriteDTO.setOilTemperature(0L);
        myFileWriteDTO.setGpsSpeed(0L);
        myFileWriteDTO.setGsmSignal(0L);
        myFileWriteDTO.setOilLevel(0L);

        myFileWriteDTO.setElecStatus("");
        myFileWriteDTO.setGprsSignal("");
        return myFileWriteDTO;
    }

}
