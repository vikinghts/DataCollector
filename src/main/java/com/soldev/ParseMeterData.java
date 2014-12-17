package com.soldev;

import org.joda.time.DateTime;

/**
 * Created by kjansen on 17/12/14.
 */
public class ParseMeterData {
    public void parse(String rawMeterOutput) {
        System.out.println(rawMeterOutput);
        MeterData meterData = new MeterData();
        meterData.currentWattage = 302;
        meterData.measureDateTime = new DateTime();
    }
}
