package com.soldev;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 *
 * Created by kjansen on 16/12/14.
 * This class Reads data from the smart meter through a com port.
 */
class SerialReader implements Runnable {
    private static final int BUFFERSIZE = 1024;
    private static final Logger LOG = LoggerFactory.getLogger(SerialReader.class);
    private final InputStream in;
    private final String serverUrl;

    public SerialReader(InputStream in, String serverUrl) {
        this.in = in;
        this.serverUrl = serverUrl;
    }

    public void run() {
        byte[] buffer = new byte[BUFFERSIZE];
        int len;
        String rawMeterData = "";
        DataHandler dataHandler = new DataHandler();
        try {
            while ((len = this.in.read(buffer)) > -1) {
                rawMeterData = rawMeterData + new String(buffer, 0, len);
                if (new String(buffer, 0, len).contains("!")) {
                    dataHandler.postCollectedData(rawMeterData, serverUrl);
                    rawMeterData = "";
                }
            }
        } catch (IOException e) {
            LOG.error("IOException :", e);
        }
    }
}