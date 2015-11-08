package org.mutabilitydetector.stackoverflow.Question_33401174;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionMonitor implements MessageConsumer {

    private final MonitorObject monitorObject;
    private boolean isConnected = true;

    private final static Logger logger = Logger.getLogger(ConnectionMonitor.class.getName());

    public ConnectionMonitor(final MonitorObject monitorObject) {
        this.monitorObject = monitorObject;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void waitForReconnect() {
        logger.info("Waiting for connection to be reestablished...");
        synchronized (monitorObject) {
            enterWaitLoop();
        }
    }

    private void enterWaitLoop() {
        while (!isConnected()) {
            try {
                monitorObject.wait();
            } catch (final InterruptedException e) {
                logger.log(Level.SEVERE, "Exception occured while waiting for reconnect! Message: " + e.getMessage());
            }
        }
    }

    private void notifyOnConnect() {
        synchronized (monitorObject) {
            monitorObject.notifyAll();
        }
    }

    @Override
    public void onMessage(final IMessage message) {
        if (message.getType() == IMessage.Type.CONNECTION_STATUS) {
            final String content = message.getContent();
            logger.info("CONNECTION_STATUS message received. Content: " + content);
            processConnectionMessageContent(content);
        }
    }

    private void processConnectionMessageContent(final String messageContent) {
        if (messageContent.contains("Disconnected")) {
            logger.log(Level.WARNING, "Disconnected message received!");
            isConnected = false;
        } else if (messageContent.contains("Connected")) {
            logger.info("Connected message received.");
            isConnected = true;
            notifyOnConnect();
        }
    }
}