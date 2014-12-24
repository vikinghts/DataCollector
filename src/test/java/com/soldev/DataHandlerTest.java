package com.soldev;

import org.junit.Test;

import static org.junit.Assert.assertTrue;


/**
 * Created by kjansen on 22/12/14.
 * This class test the datahandler class
 */
public class DataHandlerTest {
    @Test
    public void testPostMetaData() {
        // setup
        String meterResponse = "";
        meterResponse += "/ISk5\2MT382-1004\n";
        meterResponse += "\n";
        meterResponse += "0-0:96.1.1(5A424556303035313838353931393133)\n";
        meterResponse += "1-0:1.8.1(00897.515*kWh)\n";
        meterResponse += "1-0:1.8.2(00575.076*kWh)\n";
        meterResponse += "1-0:2.8.1(00000.000*kWh)\n";
        meterResponse += "1-0:2.8.2(00000.000*kWh)\n";
        meterResponse += "0-0:96.14.0(0002)\n";
        meterResponse += "1-0:1.7.0(0000.70*kW)\n";
        meterResponse += "1-0:2.7.0(0000.00*kW)\n";
        meterResponse += "0-0:17.0.0(0999.00*kW)\n";
        meterResponse += "0-0:96.3.10(1)\n";
        meterResponse += "0-0:96.13.1()\n";
        meterResponse += "0-0:96.13.0()\n";
        meterResponse += "0-1:24.1.0(3)\n";
        meterResponse += "0-1:96.1.0(4730303135353631313038363337323133)\n";
        meterResponse += "0-1:24.3.0(141222220000)(00)(60)(1)(0-1:24.2.1)(m3)\n";
        meterResponse += "(00367.857)\n";
        meterResponse += "0-1:24.4.0(1)\n";
        meterResponse += "!\n";

        final DataHandler dataHandler = new DataHandler();

        // execute/validate
        assertTrue(dataHandler.postData(meterResponse));

    }
}
