package org.mutabilitydetector.stackoverflow;

import org.junit.Test;

import static org.mutabilitydetector.unittesting.MutabilityAssert.assertImmutable;

/**
 * http://stackoverflow.com/questions/30240358/how-to-make-the-below-java-class-immutable
 */
public class Question_30240358 {

    @Test
    public void isImmutable() {
        assertImmutable(ImmutableClass.class);
    }
}
