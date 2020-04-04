package io.reks.fakeCOM;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;

import java.io.*;
import java.util.*;
import javax.comm.*;

public class App
{
    private static final Logger logger = Logger.getLogger(App.class);

    static CommPortIdentifier portId;
    static Enumeration portList;

    InputStream inputStream;
    SerialPort serialPort;
    Thread readThread;

    public static void main(String[] args) {
        System.out.println( "Hello "+ getLocalCurrentDate());

        Enumeration portList = CommPortIdentifier.getPortIdentifiers();

        while(portList.hasMoreElements()) {
            CommPortIdentifier portId = (CommPortIdentifier) portList.nextElement();
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                logger.debug("Serial port: " + portId.getName());
            } else if (portId.getPortType() == CommPortIdentifier.PORT_PARALLEL) {
                logger.debug("Parallel port " + portId.getName());
            } else {
                logger.debug("Other port: " + portId.getName());
            }
        }
    }

    private static String getLocalCurrentDate() {
        if (logger.isDebugEnabled()) {
            logger.debug("getLocalCurrentDate() is executed!");
        }
        LocalDate date = new LocalDate();
        return date.toString();
    }
}
