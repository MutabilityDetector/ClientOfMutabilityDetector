package org.mutabilitydetector.stackoverflow.Question_33401174;

import java.util.Random;

public interface IMessage {
    String getContent();

    enum Type { CONNECTION_STATUS }

    Type getType();

}
