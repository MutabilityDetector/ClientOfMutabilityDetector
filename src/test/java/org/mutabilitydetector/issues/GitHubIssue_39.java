package org.mutabilitydetector.issues;

import com.google.common.collect.ImmutableList;
import net.ttsui.junit.rules.pending.PendingImplementation;
import net.ttsui.junit.rules.pending.PendingRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mutabilitydetector.ConfigurationBuilder;
import org.mutabilitydetector.Configurations;
import org.mutabilitydetector.unittesting.MutabilityAsserter;

import java.util.Date;
import java.util.List;

import static org.mutabilitydetector.unittesting.MutabilityAssert.assertInstancesOf;
import static org.mutabilitydetector.unittesting.MutabilityMatchers.areImmutable;
import static org.mutabilitydetector.unittesting.MutabilityMatchers.areNotImmutable;

public class GitHubIssue_39 {

    @Rule public PendingRule pendingRule = new PendingRule();

    @PendingImplementation
    @Test
    public void immutableListWithImmutableElementTypeIsImmutable() {
        assertInstancesOf(HasImmutableListWithImmutableElementType.class, areImmutable());
    }

    @PendingImplementation
    @Test
    public void immutableListWithMutableElementTypeIsImmutable() {
        assertInstancesOf(HasImmutableListWithMutableElementType.class, areImmutable());
    }

    @PendingImplementation
    @Test
    public void copiesIntoImmutableListIsImmutable() {
        assertInstancesOf(CopiesListIntoImmutableListField.class, areImmutable());
    }

    @Test
    public void customImmutableContainerWithMutableElementTypeIsMutable() {
        ASSERTER.assertInstancesOf(HasCustomImmutableContainerWithMutableElementType.class, areNotImmutable());
    }

    @Test
    public void customImmutableContainerWithImmutableElementTypeIsImmutable() {
        ASSERTER.assertInstancesOf(HasCustomImmutableContainerWithImmutableElementType.class, areImmutable());
    }

    static final class HasImmutableListWithImmutableElementType {
        public final ImmutableList<String> strings;
        HasImmutableListWithImmutableElementType(ImmutableList<String> strings) {
            this.strings = strings;
        }
    }

    static final class HasImmutableListWithMutableElementType {
        public final ImmutableList<Date> dates;
        HasImmutableListWithMutableElementType(ImmutableList<Date> dates) {
            this.dates = dates;
        }
    }

    static final class CopiesListIntoImmutableListField {
        public final List<String> strings;
        CopiesListIntoImmutableListField(List<String> strings) {
            this.strings = ImmutableList.copyOf(strings);
        }
    }


    private static interface MyCustomImmutableContainer<E> {
        E get();
    }

    static final class HasCustomImmutableContainerWithImmutableElementType {
        public final MyCustomImmutableContainer<String> strings;
        HasCustomImmutableContainerWithImmutableElementType(MyCustomImmutableContainer<String> strings) {
            this.strings = strings;
        }
    }

    static final class HasCustomImmutableContainerWithMutableElementType {
        public final MyCustomImmutableContainer<Date> dates;
        HasCustomImmutableContainerWithMutableElementType(MyCustomImmutableContainer<Date> dates) {
            this.dates = dates;
        }
    }

    static final MutabilityAsserter ASSERTER = MutabilityAsserter.configured(new ConfigurationBuilder() {

        @Override
        public void configure() {
            hardcodeAsImmutableContainerType(MyCustomImmutableContainer.class);

            mergeHardcodedResultsFrom(Configurations.JDK_CONFIGURATION);
        }
    });

}
