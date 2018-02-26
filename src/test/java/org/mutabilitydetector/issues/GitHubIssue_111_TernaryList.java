package org.mutabilitydetector.issues;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mutabilitydetector.unittesting.MutabilityAssert.assertInstancesOf;
import static org.mutabilitydetector.unittesting.MutabilityMatchers.areImmutable;

public class GitHubIssue_111_TernaryList {


    @Test public void ternary() {
        assertInstancesOf(ImmutableWithListFieldAssignedWithTernary.class, areImmutable());
    }

    @Test public void ifStatement() {
        assertInstancesOf(ImmutableWithListFieldAssignedWithIfStatement.class, areImmutable());
    }

    public static final class ImmutableWithListFieldAssignedWithTernary {
        private final List<String> roles;

        ImmutableWithListFieldAssignedWithTernary(List<String> roles) {
            this.roles = roles == null ? null : Collections.unmodifiableList(new ArrayList<>(roles));
        }
    }

    public static final class ImmutableWithListFieldAssignedWithIfStatement {
        private final List<String> roles;

        ImmutableWithListFieldAssignedWithIfStatement(List<String> roles) {
            if (roles == null) {
                this.roles = null;
            } else {
                this.roles = Collections.unmodifiableList(new ArrayList<>(roles));
            }
        }
    }
}
