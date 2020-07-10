package com.kotoumi.sifcapapi.util;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Component
public class LoggerHelper {

    private static final String MODULE_NAME = "AI-SECRETARY";
    private static final int LOG_ID_DIGITS  = 10;
    private static final SimpleDateFormat SDF_LOG_ID   = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final SimpleDateFormat SDF_LOG_TIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final Logger loggerMonitor = LoggerFactory.getLogger("Monitor");

    /**
     * 生成logId
     * @return logId
     */
    public static String generateLogId() {
        return String.format("%s_%s_%s", MODULE_NAME, SDF_LOG_ID.format(new Date()), getRandomDigits(LOG_ID_DIGITS));
    }

    /**
     * 生成随机数字
     * @return logId
     */
    public static String getRandomDigits(int digits) {

        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < digits; i ++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();

    }

    /**
     * monitor log
     *
     * @param unitLogId unitLogId
     * @param apiName apiName
     * @param stageMessage stageMessage
     * @param elapsedTime elapsedTime
     * @param returnCode returnCode
     * @param returnMessage returnMessage
     */
    public static void logMonitor(String unitLogId, String apiName, String stageMessage, long elapsedTime,
                                  String returnCode, String returnMessage) {

        JSONObject unitLogMonitor = new JSONObject();

        unitLogMonitor.put("unit_log_id", unitLogId);
        unitLogMonitor.put("log_timestamp", SDF_LOG_TIME.format(new Date()));
        unitLogMonitor.put("api_name", apiName);
        unitLogMonitor.put("stage_message", stageMessage);
        unitLogMonitor.put("elapsed_time", elapsedTime);
        unitLogMonitor.put("return_code", returnCode);
        unitLogMonitor.put("return_message", returnMessage);

        loggerMonitor.info("monitor=" + unitLogMonitor.toJSONString());

    }

}
