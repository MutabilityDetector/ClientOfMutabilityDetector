package org.mutabilitydetector.stackoverflow.Question_33401174;

import javax.annotation.concurrent.Immutable;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

@Immutable
public class ImmutableConnection implements ImmutableMessageConsumer {

    private final boolean isConnected;

    private final static Logger logger = Logger.getLogger(ConnectionMonitor.class.getName());

    public ImmutableConnection(final boolean isConnected) {
        this.isConnected = isConnected;
    }

    public boolean isConnected() {
        return isConnected;
    }

    @Override
    public ImmutableConnection onMessage(final IMessage message) {
        if (message.getType() == IMessage.Type.CONNECTION_STATUS) {
            final String content = message.getContent();
            logger.info("CONNECTION_STATUS message received. Content: " + content);
            return processConnectionMessageContent(content);
        } else {
            return this;
        }
    }

    private ImmutableConnection processConnectionMessageContent(final String messageContent) {
        if (messageContent.contains("Disconnected")) {
            logger.log(Level.WARNING, "Disconnected message received!");
            return new ImmutableConnection(false);
        } else if (messageContent.contains("Connected")) {
            logger.info("Connected message received.");
            return new ImmutableConnection(true);
        } else {
            return this;
        }
    }

    private static final class Monitor {
        private final MonitorObject monitorObject;
        private ImmutableConnection connection;

        Monitor(MonitorObject monitorObject) {
            this.connection = new ImmutableConnection(false);
            this.monitorObject = monitorObject;
        }

        public void onMessage(IMessage message) {
            connection = connection.onMessage(message);
            if (connection.isConnected()) {
                notifyOnConnect();
            }
        }

        public void waitForReconnect() {
            logger.info("Waiting for connection to be reestablished...");
            synchronized (monitorObject) {
                enterWaitLoop();
            }
        }


        private void enterWaitLoop() {
            while (!connection.isConnected) {
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
    }
}
