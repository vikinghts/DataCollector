package com.soldev;

import gnu.io.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

public class Collect {
    private static final Logger LOG = LoggerFactory.getLogger(Collect.class);
    private static String serverUrl;

    public static void main( String[] args ) {
        serverUrl = "http://192.168.8.1:3232/DataManager-0.1/api/DataManagerService";
        try {
            ( new Collect() ).connect( "/dev/ttyUSB0" );
        } catch( Exception e ) {
            LOG.error("Exception: ", e);
        }
    }

    void connect(String portName) throws UnsupportedCommOperationException, NoSuchPortException, PortInUseException, IOException {
      CommPortIdentifier portIdentifier = CommPortIdentifier
              .getPortIdentifier(portName);
      if (portIdentifier.isCurrentlyOwned()) {
          LOG.error("Error: Port is currently in use");
      } else {
          int timeout = 2000;
          CommPort commPort = portIdentifier.open(this.getClass().getName(), timeout);

          if (commPort instanceof SerialPort) {
              SerialPort serialPort = (SerialPort) commPort;
              serialPort.setSerialPortParams(9600,
                      SerialPort.DATABITS_7,
                      SerialPort.STOPBITS_1,
                      SerialPort.PARITY_EVEN);

              InputStream in = serialPort.getInputStream();

              (new Thread(new SerialReader(in, serverUrl))).start();

          } else {
              LOG.error("Error: Only serial ports are handled.");
          }
      }
  }
}
