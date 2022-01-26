package org.mutabilitydetector.jdk;

import org.junit.Test;

import java.util.*;

import static org.mutabilitydetector.unittesting.AllowedReason.provided;
import static org.mutabilitydetector.unittesting.MutabilityAssert.assertInstancesOf;
import static org.mutabilitydetector.unittesting.MutabilityMatchers.areImmutable;
import static org.mutabilitydetector.unittesting.MutabilityMatchers.areNotImmutable;

public class JdkImmutableCollectionsTest {

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
