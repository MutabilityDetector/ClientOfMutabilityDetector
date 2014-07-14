package org.mutabilitydetector.issues;

import static org.mutabilitydetector.unittesting.MutabilityAssert.assertImmutable;

import java.time.Instant;

import org.junit.Test;

public class GitHubIssue56_Jdk8Classes {

	@Test
	public void java_time_instant_asAFieldThrowsException() throws Exception {
		assertImmutable(HasInstantField.class);
	}
	
	public final static class HasInstantField {
		public final Instant instant;

		public HasInstantField(Instant instant) {
			this.instant = instant;
		}
	}
}
