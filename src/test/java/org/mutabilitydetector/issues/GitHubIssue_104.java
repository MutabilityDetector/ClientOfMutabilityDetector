package org.mutabilitydetector.issues;

import net.ttsui.junit.rules.pending.PendingImplementation;
import net.ttsui.junit.rules.pending.PendingRule;
import org.junit.Rule;
import org.junit.Test;

import static org.mutabilitydetector.unittesting.AllowedReason.provided;
import static org.mutabilitydetector.unittesting.MutabilityAssert.assertInstancesOf;
import static org.mutabilitydetector.unittesting.MutabilityMatchers.areImmutable;

public class GitHubIssue_104 {

    @Rule
    public PendingRule pendingRule = new PendingRule();

    @PendingImplementation
    @Test
    public void assertImmutability() {
        assertInstancesOf(ClassWithGenericField.class, areImmutable(),
            provided(SomeInterface.class).isAlsoImmutable());
    }

    @Test
    public void assertImmutabilityOfGenericType() {
        assertInstancesOf(ClassWithGenericField.class, areImmutable(),
            provided("T").isAlsoImmutable());
    }


    private static final class ClassWithGenericField<T extends SomeInterface> {
        private final T field;

        public ClassWithGenericField(final T field) {this.field = field;}

        public T getField() {
            return field;
        }
    }

    private interface SomeInterface {

    }


}
