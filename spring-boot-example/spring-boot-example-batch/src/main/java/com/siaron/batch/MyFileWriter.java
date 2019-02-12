package com.siaron.batch;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.InitializingBean;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.Date;
import java.util.List;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;


/**
 * @author xielongwang
 * @create 2017-12-14 下午8:31
 * @email xielong.wang@nvr-china.com
 * @description 写文件的控制器, 文件以追加的形式写入
 */
public class MyFileWriter implements ItemWriter<MyFileWriteDTO>, InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(MyFileWriter.class);

    String DEFAULT_LINE_SEPARATOR = System.getProperty("line.separator");
    String COPY_DEVICE_DATA_FILE_PATH = "/opt/lx_batch_file/lx_device_data_test.txt";
    String DATEP_ATTERN = "YYYY-MM-dd HH:mm:ss.SSS";
    private static final String DELIMITER = ",";

    @Override
    public void afterPropertiesSet() throws Exception {
    }

    /**

     */
    @Override
    public void write(List<? extends MyFileWriteDTO> items) {
        /**
         * TODO 文件写入以追加的方式写入数据,注意线程安全问题,写文件锁开销大.可以用Disruptor 转成单线程写文件, 参考log4j 日志写文件
         */
        logger.info("current threadName  ==[{}] ", currentThread().getName());
        FileOutputStream fos = null;
        FileChannel fc = null;
        FileLock fl = null;
        try {
            fos = new FileOutputStream(COPY_DEVICE_DATA_FILE_PATH, true);
            fc = fos.getChannel();
            while (true) {
                try {
                    fl = fc.tryLock();//不断的请求锁，如果请求不到，等一秒再请求
                    break;
                } catch (Exception e) {
                    logger.info("lock is exist ...... current threadName  ==[{}]", currentThread().getName());
                    sleep(500);
                }
            }

            StringBuffer sb = new StringBuffer();
            for (MyFileWriteDTO fw : items) {
                sb.append(aggregateItem(fw)).append(DEFAULT_LINE_SEPARATOR);
            }
            byte[] bytes = sb.toString().getBytes();
            ByteBuffer buffer = ByteBuffer.allocateDirect(bytes.length);
            buffer.clear();
            buffer.put(bytes);
            buffer.flip();
            fc.write(buffer);

            logger.info("current threadName ==[{}] write success", currentThread().getName());
            fl.release();
            logger.info("current threadName ==[{}] release lock", currentThread().getName());

            fc.close();
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("current threadName ==[{}]  write failed", currentThread().getName());
        } finally {
            if (fl != null && fl.isValid()) {
                try {
                    fl.release();
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.error("current threadName ==[{}]  release failed", currentThread().getName());
                }
            }
        }

    }


    /**
     * id,device_num,license_id,version_num,locate_date,record_time, total_duration,acc_status,comm_status,start_times,locate_status,amaplatitude_num,amaplongitude_num,address,
     * province,city,citycode,elec_status,engine_rotate,engine_temperature,
     * oil_temperature,gprs_signal,gps_power_status,gps_speed, gsm_signal,imitate1,imitate2,oil_level,pressure_meter,real_time_duration
     * <p>
     * 返回要写入的一行数据
     *
     * @param mfw 类
     * @return 一行数据
     */
    private String aggregateItem(MyFileWriteDTO mfw) {
        StringBuffer sb = new StringBuffer();
        sb.append(mfw.getId())
                .append(DELIMITER)
                .append(mfw.getDeviceNum())
                .append(DELIMITER)
                .append(mfw.getLicenseId())
                .append(DELIMITER)
                .append(mfw.getVersionNum())
                .append(DELIMITER)
                .append(dateTimeFormat(mfw.getLocateDate(), DATEP_ATTERN))
                .append(DELIMITER)
                .append(dateTimeFormat(mfw.getRecordTime(), DATEP_ATTERN))
                .append(DELIMITER)
                .append(mfw.getTotalDuration())
                .append(DELIMITER)
                .append(mfw.getAccStatus())
                .append(DELIMITER)
                .append(mfw.getCommStatus())
                .append(DELIMITER)
                .append(mfw.getStartTimes())
                .append(DELIMITER)
                .append(mfw.getLocateStatus())
                .append(DELIMITER)
                .append(mfw.getAmaplatitudeNum())
                .append(DELIMITER)
                .append(mfw.getAmaplongitudeNum())
                .append(DELIMITER)
                .append(mfw.getAddress())
                .append(DELIMITER)
                .append(mfw.getProvince())
                .append(DELIMITER)
                .append(mfw.getCity())
                .append(DELIMITER)
                .append(mfw.getCitycode())
                .append(DELIMITER)
                .append(mfw.getElecStatus())
                .append(DELIMITER)
                .append(mfw.getEngineRotate())
                .append(DELIMITER)
                .append(mfw.getEngineTemperature())
                .append(DELIMITER)
                .append(mfw.getOilTemperature())
                .append(DELIMITER)
                .append(mfw.getGprsSignal())
                .append(DELIMITER)
                .append(mfw.getGpsPowerStatus())
                .append(DELIMITER)
                .append(mfw.getGpsSpeed())
                .append(DELIMITER)
                .append(mfw.getGsmSignal())
                .append(DELIMITER)
                .append(mfw.getImitate1())
                .append(DELIMITER)
                .append(mfw.getImitate2())
                .append(DELIMITER)
                .append(mfw.getOilLevel())
                .append(DELIMITER)
                .append(mfw.getPressureMeter())
                .append(DELIMITER)
                .append(mfw.getRealTimeDuration());
        return sb.toString();
    }


    /**
     * 日期格式化
     *
     * @param date        日期
     * @param datePattern 格式
     * @return 格式化后的日期字符串
     */
    private String dateTimeFormat(Date date, String datePattern) {
        return new DateTime(date).toString(datePattern);
    }


}
