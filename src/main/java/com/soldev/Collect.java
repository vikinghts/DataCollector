package com.soldev;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

public class Collect {
    private static final Logger LOG = LoggerFactory.getLogger(Collect.class);

    public static void main( String[] args ) {
        try {
            ( new Collect() ).connect( "/dev/ttyUSB0" );
        } catch( Exception e ) {
            e.printStackTrace();
        }
    }
 
  void connect( String portName ) throws Exception {
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

              (new Thread(new SerialReader(in))).start();

          } else {
              LOG.error("Error: Only serial ports are handled by this example.");
          }
      }
  }
}
