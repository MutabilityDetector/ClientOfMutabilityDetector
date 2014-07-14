package org.mutabilitydetector.stackoverflow;

import org.junit.Test;
import org.mutabilitydetector.unittesting.MutabilityAssert;
import org.mutabilitydetector.unittesting.MutabilityAssertionError;

public class Question_23021354 {

	@Test(expected = MutabilityAssertionError.class)
	public void studentIsImmutable() {
		MutabilityAssert.assertImmutable(Student.class);
	}
}
