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

	@Test
	public void usingAListFieldWithListOfIsImmutable() throws Exception {
		assertInstancesOf(CopyListIntoNewListUsingListOf.class, areImmutable());
	}

	@Test
	public void usingASafelyWrappedListFieldWithImmutableElementTypeIsImmutable() throws Exception {
		assertInstancesOf(HasListOfImmutableThings.class, areImmutable());
	}

	@Test
	public void usingAConcreteCollectionFieldWithMutableElementTypeIsMutable() throws Exception {
		assertInstancesOf(ConcreteCollectionWithMutableElementType.class, areNotImmutable());
	}

	@Test
	public void usingAConcreteCollectionFieldWithImmutableElementTypeIsMutable() throws Exception {
		assertInstancesOf(ConcreteCollectionWithImmutableElementType.class, areImmutable());
	}

	@Test
	public void canSuppressWarningsForGenericType() throws Exception {
		assertInstancesOf(HasImmutableGenericField.class, areNotImmutable());
		assertInstancesOf(HasImmutableGenericField.class, areImmutable(), provided("T").isAlsoImmutable());
	}

	@Test
	public void canSuppressWarningsForCollectionContainingGenericType() throws Exception {
		assertInstancesOf(HasCollectionFieldContainingGenericElementTypes.class, areNotImmutable());
		assertInstancesOf(HasCollectionFieldContainingGenericElementTypes.class, areImmutable(), provided("MY_TYPE").isAlsoImmutable());
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

	public static final class ConcreteCollectionWithMutableElementType {
		private final Set<Date> dates;

		public ConcreteCollectionWithMutableElementType(TreeSet<Date> dates) {
			this.dates = Collections.unmodifiableSet(new TreeSet(dates));
		}
	}

	public static final class ConcreteCollectionWithImmutableElementType {
		private final Set<String> strings;

		public ConcreteCollectionWithImmutableElementType(TreeSet<String> strings) {
			this.strings = Collections.unmodifiableSet(new TreeSet(strings));
		}
	}

	public static final class CopyListIntoNewListUsingListOf {
		public final List<String> strings;

		public CopyListIntoNewListUsingListOf(List<String> strings) {
			this.strings = List.of(strings.toArray(new String[0]));
		}
	}

	public static final class HasListOfImmutableThings {
		public final List<String> strings;

		public HasListOfImmutableThings(List<String> strings) {
			this.strings = Collections.unmodifiableList(new ArrayList(strings));
		}
	}

	public static final class HasImmutableGenericField<T> {
		public final T t;

		public HasImmutableGenericField(T t) {
			this.t = t;
		}
	}

	public static final class HasCollectionFieldContainingGenericElementTypes<MY_TYPE> {
		public final List<MY_TYPE> myTypes;

		public HasCollectionFieldContainingGenericElementTypes(List<MY_TYPE> myTypes) {
			this.myTypes = Collections.unmodifiableList(new ArrayList(myTypes));
		}
	}

}
