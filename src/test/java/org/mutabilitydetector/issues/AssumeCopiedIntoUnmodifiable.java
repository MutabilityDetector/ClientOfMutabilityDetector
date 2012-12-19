package org.mutabilitydetector.issues;

import static org.mutabilitydetector.unittesting.MutabilityAssert.assertInstancesOf;
import static org.mutabilitydetector.unittesting.MutabilityMatchers.areImmutable;
import static org.mutabilitydetector.unittesting.matchers.reasons.AssumeCopiedIntoUnmodifiable.assuming;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.concurrent.Immutable;

import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

/**
 * The <code>AssumeCopiedIntoUnmodifiable</code> allowed reason is provided
 * out-of-the-box since v0.9.
 * <p>
 * Since this class was linked to as an example of solving an issue, it now
 * shows the intended usage.
 * 
 */
public class AssumeCopiedIntoUnmodifiable {

    /*
     * A non-JDK collection type/constructor is used to perform the copy, before wrapping in
     * an unmodifiable collection.
     */
    @Test
    public void canAllowAnUnknownWrapperMethod() throws Exception {
        assertInstancesOf(SafelyWrapsFieldWithUnknownMethod.class, 
                          areImmutable(),
                          assuming("myStrings").isSafelyCopiedUnmodifiableCollectionWithImmutableTypes());
    }

    @Immutable
    public static final class SafelyWrapsFieldWithUnknownMethod {
        public final List<String> myStrings;

        public SafelyWrapsFieldWithUnknownMethod(Collection<String> myStrings) {
            this.myStrings = Collections.unmodifiableList(Lists.newArrayList(myStrings));
        }
        
    }

    /*
     * Uses a non-JDK collection type, which is known to the developer, but not
     * Mutability Detector be unmodifiable.
     */
    @Test
    public void canAllowUsingACustomImmutableCollectionType() throws Exception {
        assertInstancesOf(UsesImmutableAbstractCollectionType.class, 
                          areImmutable(),
                          assuming("myStrings").isSafelyCopiedUnmodifiableCollectionWithImmutableTypes());
    }
    

    @Immutable
    public static final class UsesImmutableAbstractCollectionType {
        public final List<String> myStrings;

        public UsesImmutableAbstractCollectionType(List<String> myStrings) {
            this.myStrings = ImmutableList.<String>builder().addAll(myStrings).build();
        }
    }

    /*
     * Uses a mutable element type in a collection field. Should be used when
     * the developer knows no elements are modified.
     */
    @Test
    public void canAllowUnmodifiableListOfMutableElementType() throws Exception {
        assertInstancesOf(SafelyWrappedAndCopiedWithMutableElementType.class, 
                          areImmutable(),
                          assuming("myDates").isSafelyCopiedUnmodifiableCollectionWithImmutableTypes());
    }
    
    @Immutable
    public static final class SafelyWrappedAndCopiedWithMutableElementType {
        private final List<Date> myDates;
        
        public SafelyWrappedAndCopiedWithMutableElementType(List<Date> myDates) {
            this.myDates = Collections.unmodifiableList(new ArrayList<Date>(myDates));
        }
        
        public long timeOfFirstDate() {
            return myDates.get(0).getTime();
        }
    }
}
