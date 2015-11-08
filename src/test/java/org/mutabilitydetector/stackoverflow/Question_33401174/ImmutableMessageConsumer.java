package org.mutabilitydetector.stackoverflow.Question_33401174;

public interface ImmutableMessageConsumer<T extends ImmutableMessageConsumer> {
    T onMessage(final IMessage message);
}
