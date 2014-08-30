package org.mutabilitydetector.jdk;

import static org.mutabilitydetector.unittesting.AllowedReason.provided;
import static org.mutabilitydetector.unittesting.MutabilityAssert.assertInstancesOf;
import static org.mutabilitydetector.unittesting.MutabilityMatchers.areImmutable;

import java.util.Optional;

import org.junit.Test;

public class JdkClasses {

	@Test
	public void java_util_optional() throws Exception {
		assertInstancesOf(Optional.class, areImmutable(), provided("T").isAlsoImmutable());
	}
}
