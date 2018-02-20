package org.mutabilitydetector.issues;

import org.junit.Test;
import org.mutabilitydetector.unittesting.MutabilityAssert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mutabilitydetector.unittesting.AllowedReason.provided;
import static org.mutabilitydetector.unittesting.MutabilityMatchers.areImmutable;

public class GitHub_109_RecursiveClassesNeedAllowedReason {

    // Throws:
    //    org.mutabilitydetector.unittesting.MutabilityAssertionError:
    //    Expected: org.mutabilitydetector.unittesting.evaluation.TreeTest$Node to be IMMUTABLE
    //    but: org.mutabilitydetector.unittesting.evaluation.TreeTest$Node is actually NOT_IMMUTABLE
    //    Reasons:
    //    Field can have collection with mutable element type (java.util.List<org.mutabilitydetector.unittesting.evaluation.TreeTest$Node>) assigned to it. [Field: children, Class: org.mutabilitydetector.unittesting.evaluation.TreeTest$Node]
    //    Allowed reasons:
    //    None.
    @Test
    public void notHelpfulFailure() {
        MutabilityAssert.assertInstancesOf(Node.class, areImmutable());
    }

    @Test
    public void workaround() {
        MutabilityAssert.assertInstancesOf(Node.class, areImmutable(), provided(Node.class).isAlsoImmutable());
    }

    public static final class Node {
        private final List<Node> children;

        public Node(final List<Node> children) {
            this.children = Collections.unmodifiableList(new ArrayList<>(children));
        }

        public List<Node> getChildren() {
            return children;
        }
    }
}
