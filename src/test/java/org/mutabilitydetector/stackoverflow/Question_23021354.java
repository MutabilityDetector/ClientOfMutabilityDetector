package org.mutabilitydetector.stackoverflow;

import org.junit.Test;
import org.mutabilitydetector.unittesting.MutabilityAssert;

public class Question_23021354 {

    @Test 
    public void studentIsImmutable() {
        MutabilityAssert.assertImmutable(Student.class);
    }
}
