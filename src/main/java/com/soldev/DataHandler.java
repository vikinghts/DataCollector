package com.soldev;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by kjansen on 22/12/14.
 * This class parses the meter data and extracts it values to post them to a web socket.
 */
public class DataHandler {
    public boolean postData(String rawMeterOutput) {
        System.out.println("Post");
        DateTime measureDateTime = new DateTime();
        Float currentPower = 0.0f;
        for (String line : rawMeterOutput.split("\\r?\\n")) {
            //current power usage : "1-0:1.7.0"
            if (line.contains("1-0:1.7.0")) {
                currentPower = Float.parseFloat(line.substring(10, 17)) * 1000;
            }
        }
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyyMMddHHmmss");
        String urlToPost = "?MeasureDataTime=" + fmt.print(measureDateTime) + "?CurrentPower=" + currentPower.toString();
        System.out.println(urlToPost);
        return true;
    }
}
