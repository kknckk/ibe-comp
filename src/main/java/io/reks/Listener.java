package io.reks;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import org.apache.log4j.Logger;
import org.joda.time.LocalDate;

public class Listener {

    private static final Logger logger = Logger.getLogger(App.class);

    public static void main(String[] args) {
        System.out.println( "Hello "+ getLocalCurrentDate());

    final SerialPort comPort = SerialPort.getCommPorts()[0];
    comPort.openPort();
comPort.addDataListener(new SerialPortDataListener() {
        @Override
        public int getListeningEvents() { return SerialPort.LISTENING_EVENT_DATA_AVAILABLE; }
        @Override
        public void serialEvent(SerialPortEvent event)
        {
            if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE)
                return;
            byte[] newData = new byte[comPort.bytesAvailable()];
            int numRead = comPort.readBytes(newData, newData.length);
            System.out.println("Read " + numRead + " bytes.");
        }
    });
    }

    private static String getLocalCurrentDate() {
        if (logger.isDebugEnabled()) {
            logger.debug("getLocalCurrentDate() is executed!");
        }
        LocalDate date = new LocalDate();
        return date.toString();
    }
}
