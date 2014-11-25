package com.soldev;

import java.io.IOException;
import java.io.InputStream;
import gnu.io.*;

public class Collect {


    public static void main( String[] args ) {
        try {
            ( new Collect() ).connect( "/dev/ttyUSB0" );
        } catch( Exception e ) {
            e.printStackTrace();
        }
    }
 
  void connect( String portName ) throws Exception {
    CommPortIdentifier portIdentifier = CommPortIdentifier
        .getPortIdentifier( portName );
    if( portIdentifier.isCurrentlyOwned() ) {
      System.out.println( "Error: Port is currently in use" );
    } else {
      int timeout = 2000;
      CommPort commPort = portIdentifier.open( this.getClass().getName(), timeout );

      if( commPort instanceof SerialPort ) {
        SerialPort serialPort = ( SerialPort )commPort;
        serialPort.setSerialPortParams( 9600,
                                        SerialPort.DATABITS_7,
                                        SerialPort.STOPBITS_1,
                                        SerialPort.PARITY_EVEN );



        InputStream in = serialPort.getInputStream();

        ( new Thread( new SerialReader( in ) ) ).start();

      } else {
        System.out.println( "Error: Only serial ports are handled by this example." );
      }
    }
  }

  public static class SerialReader implements Runnable {

    InputStream in;

    public SerialReader( InputStream in ) {
      this.in = in;
    }

    public void run() {
      byte[] buffer = new byte[ 1024 ];
      int len = -1;
      try {
        while( ( len = this.in.read( buffer ) ) > -1 ) {
          System.out.print( new String( buffer, 0, len ) );
            if (new String( buffer, 0, len ).contains("!")) {
                break;
            }
        }
      } catch( IOException e ) {
        e.printStackTrace();
      }
    }
  }
}
