package com.soldev;

import java.io.IOException;
import java.io.InputStream;

/**
 *
 * Created by kjansen on 16/12/14.
 * This class Reads data from the smart meter through a com port.
 */
public class SerialReader implements Runnable {
    InputStream in;

    public SerialReader( InputStream in ) {
        this.in = in;
    }

    public void run() {
        byte[] buffer = new byte[1024];
        //int len = -1;
        int len;
        String rawMeterData = "";
        DataHandler dataHandler = new DataHandler();
        try {
            while ((len = this.in.read(buffer)) > -1) {
                //System.out.print(new String(buffer, 0, len));
                rawMeterData = rawMeterData + new String(buffer, 0, len);
                if (new String(buffer, 0, len).contains("!")) {
                    dataHandler.postData(rawMeterData);
                    rawMeterData = "";
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}