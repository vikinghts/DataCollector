package com.soldev;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;


/**
 * Created by kjansen on 22/12/14.
 * This class parses the meter data and extracts it values to post them to a web socket.
 */
class DataHandler {
    public static final int TIMEOUT = 5000;
    public static final int WATTTOKW = 1000;
    private static final Logger LOG = LoggerFactory.getLogger(DataHandler.class);

    public boolean postCollectedData(String rawMeterOutput) {
        String parsedOutput = parseLines(rawMeterOutput);
        Boolean status;
        DateTime measureDateTime = new DateTime();
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyyMMddHHmmss");
        parsedOutput = "{\"MeasureDataTime\":" + fmt.print(measureDateTime) + "," + parsedOutput + "}";
        LOG.debug(parsedOutput);

        JSONObject jsonObject = new JSONObject(parsedOutput);

        // Send the json data to the rest service as JSON.
        try {
            URL url = new URL("http://192.168.8.1:3232/DataManager-0.1/api/DataManagerService");
            URLConnection connection = url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setConnectTimeout(TIMEOUT);
            connection.setReadTimeout(TIMEOUT);
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
            out.write(jsonObject.toString());
            out.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));

            while (in.readLine() != null) {
                LOG.debug(in.readLine());
            }
            LOG.debug("\nREST Service Invoked Successfully..");
            status = true;
            in.close();
        } catch (Exception e) {
            LOG.error("\nError while calling REST Service", e);
            status = false;
        }
        return status;
    }

    public String parseLines(String rawMeterOutput) {
        Float currentPower = 0.0f;
        Float totalGas = 0.0f;
        Float totalDalPower = 0.0f;
        Float totalPiekPower = 0.0f;
        for (String line : rawMeterOutput.split("\\r?\\n")) {
            //current power usage : "1-0:1.7.0"
            if (line.contains("1-0:1.7.0")) {
                currentPower = Float.parseFloat(line.substring(10, 17)) * WATTTOKW;
            }
            if (line.startsWith("(")) {
                totalGas = Float.parseFloat(line.substring(1, 10)) * WATTTOKW;
            }
            if (line.contains("1-0:1.8.1")) {
                totalDalPower = Float.parseFloat(line.substring(10, 15));
            }
            if (line.contains("1-0:1.8.2")) {
                totalPiekPower = Float.parseFloat(line.substring(10, 15));
            }
        }
        return "\"CurrentPower\":" + currentPower.toString() + "," +
                "\"totalGas\":" + totalGas.toString() + "," +
                "\"totalDalPower\":" + totalDalPower.toString() + "," +
                "\"totalPiekPower\":" + totalPiekPower.toString();
    }

}
