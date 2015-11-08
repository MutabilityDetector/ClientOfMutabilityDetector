package org.mutabilitydetector.stackoverflow.Question_33401174;

import java.util.Random;

public class MessageProducer {
    private final Random random = new Random();

    public IMessage nextMessage() {
        return new IMessage() {
            @Override
            public String getContent() {
                return random.nextInt() % 2 == 0
                        ? "Whoops, you were Disconnected"
                        : "Yay, you are now Connected";
            }

            @Override
            public Type getType() {
                return Type.CONNECTION_STATUS;
            }
        };
    }
}
