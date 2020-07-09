package org.mutabilitydetector.jdk;

import static org.mutabilitydetector.unittesting.AllowedReason.provided;
import static org.mutabilitydetector.unittesting.MutabilityAssert.assertInstancesOf;
import static org.mutabilitydetector.unittesting.MutabilityMatchers.areImmutable;
import static org.mutabilitydetector.unittesting.MutabilityMatchers.areNotImmutable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import net.ttsui.junit.rules.pending.PendingImplementation;
import net.ttsui.junit.rules.pending.PendingRule;
import org.junit.Rule;
import org.junit.Test;

public class Jdk8OptionalUsage {

	@Test
	public void java_util_optional() throws Exception {
		assertInstancesOf(Optional.class, areImmutable(), provided("T").isAlsoImmutable());
	}

	@Test
	public void usingAnOptionalFieldWithImmutableElementIsImmutable() throws Exception {
		assertInstancesOf(HasOptionalString.class, areImmutable());
	}

	@Test
	public void usingAnOptionalFieldWithMutableElementIsMutable() throws Exception {
		assertInstancesOf(HasOptionalDate.class, areNotImmutable());
	}


	@Test
	public void usingAnOptionalFieldWithGenericElementIsMutable() throws Exception {
		assertInstancesOf(HasOptionalGeneric.class, areNotImmutable());
	}


	@Test
	public void usingAnOptionalFieldWithGenericElementIsMutableButCanBeAllowed() throws Exception {
		assertInstancesOf(HasOptionalGeneric.class, areImmutable(), provided("MY_TYPE").isAlsoImmutable());
	}

	public static final class HasOptionalString {
		private final Optional<String> optionalStringField;

		public HasOptionalString(Optional<String> optionalStringField) {
			this.optionalStringField = optionalStringField;
		}
	}

	public static final class HasOptionalDate {
		private final Optional<Date> optionalDateField;

		public HasOptionalDate(Optional<Date> optionalDateField) {
			this.optionalDateField = optionalDateField;
		}
	}

	public static final class HasOptionalGeneric<MY_TYPE> {
		private final Optional<MY_TYPE> optionalGenericField;

		public HasOptionalGeneric(Optional<MY_TYPE> optionalGenericField) {
			this.optionalGenericField = optionalGenericField;
		}
	}

}
