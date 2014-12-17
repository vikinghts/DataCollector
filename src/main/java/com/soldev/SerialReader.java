package com.soldev;

import java.io.IOException;
import java.io.InputStream;
import gnu.io.*;

/**
 * Created by kjansen on 16/12/14.
 */
public class SerialReader implements Runnable {
    InputStream in;

    public SerialReader( InputStream in ) {
        this.in = in;
    }

    public void run() {
        byte[] buffer = new byte[1024];
        int len = -1;
        String rawMeterData = "";
        ParseMeterData parseMeterData = new ParseMeterData();
        try {
            while ((len = this.in.read(buffer)) > -1) {
                System.out.print(new String(buffer, 0, len));
                rawMeterData = rawMeterData + new String(buffer, 0, len);
                if (new String(buffer, 0, len).contains("!")) {
                    break;
                }
            }
            parseMeterData.parse(rawMeterData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}