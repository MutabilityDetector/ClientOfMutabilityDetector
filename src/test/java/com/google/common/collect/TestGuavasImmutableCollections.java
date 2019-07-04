package com.google.common.collect;

import net.ttsui.junit.rules.pending.PendingImplementation;
import net.ttsui.junit.rules.pending.PendingRule;
import org.junit.Rule;
import org.junit.Test;

import static org.mutabilitydetector.unittesting.AllowedReason.allowingForSubclassing;
import static org.mutabilitydetector.unittesting.AllowedReason.assumingFields;
import static org.mutabilitydetector.unittesting.AllowedReason.provided;
import static org.mutabilitydetector.unittesting.MutabilityAssert.assertInstancesOf;
import static org.mutabilitydetector.unittesting.MutabilityMatchers.areImmutable;

public class TestGuavasImmutableCollections {

    @Rule public PendingRule rule = new PendingRule();

    @Test
    public void com_google_common_collect_ImmutableSortedAsList() throws Exception {
        assertInstancesOf(ImmutableSortedAsList.class, areImmutable(),
                provided("E").isAlsoImmutable());
    }

    @Test
    public void com_google_common_collect_RegularImmutableList() throws Exception {
        assertInstancesOf(RegularImmutableList.class, areImmutable(),
                allowingForSubclassing(),
                assumingFields("array").areNotModifiedAndDoNotEscape());
    }

    @Test
    public void com_google_common_collect_ImmutableAsList() throws Exception {
        assertInstancesOf(ImmutableAsList.class, areImmutable(),
                provided("E").isAlsoImmutable(),
                allowingForSubclassing());
    }

    @Test
    public void com_google_common_collect_ContiguousSet() throws Exception {
        assertInstancesOf(com.google.common.collect.ContiguousSet.class, areImmutable(),
                allowingForSubclassing(),
                provided(DiscreteDomain.class).isAlsoImmutable());
    }
    @Test
    public void com_google_common_collect_DiscreteDomain() throws Exception {
        assertInstancesOf(com.google.common.collect.DiscreteDomain.class, areImmutable(),
                allowingForSubclassing());
    }


    @Test
    public void com_google_common_collect_ImmutableCollection() throws Exception {
        assertInstancesOf(com.google.common.collect.ImmutableCollection.class, areImmutable(),
                allowingForSubclassing(),
                provided("E").isAlsoImmutable());
    }


    @PendingImplementation("Nested generic, doesn't see Class<? extends B> correctly.")
    @Test
    public void com_google_common_collect_ImmutableClassToInstanceMap() throws Exception {
        assertInstancesOf(com.google.common.collect.ImmutableClassToInstanceMap.class, areImmutable(),
                provided("B").isAlsoImmutable());
    }


    @Test
    public void com_google_common_collect_ImmutableBiMap() throws Exception {
        assertInstancesOf(com.google.common.collect.ImmutableBiMap.class, areImmutable(),
                allowingForSubclassing());
    }


    @Test
    public void com_google_common_collect_ImmutableList() throws Exception {
        assertInstancesOf(com.google.common.collect.ImmutableList.class, areImmutable(),
                allowingForSubclassing());
    }


    @PendingImplementation("Nested generics errors, among others.")
    @Test
    public void com_google_common_collect_ImmutableListMultimap() throws Exception {
        assertInstancesOf(com.google.common.collect.ImmutableListMultimap.class, areImmutable(),
                allowingForSubclassing(),
                provided("K").isAlsoImmutable(),
                provided("V").isAlsoImmutable());
    }


    @PendingImplementation("Many errors")
    @Test
    public void com_google_common_collect_ImmutableMap() throws Exception {
        assertInstancesOf(com.google.common.collect.ImmutableMap.class, areImmutable());
    }


    @PendingImplementation("Cannot deal with nested generic types")
    @Test
    public void com_google_common_collect_ImmutableMultimap() throws Exception {
        assertInstancesOf(com.google.common.collect.ImmutableMultimap.class, areImmutable(),
                allowingForSubclassing(),
                provided("K").isAlsoImmutable(),
                provided("V").isAlsoImmutable());
    }


    @PendingImplementation
    @Test
    public void com_google_common_collect_ImmutableMultiset() throws Exception {
        assertInstancesOf(com.google.common.collect.ImmutableMultiset.class, areImmutable());
    }


    @PendingImplementation("Cannot deal with nested generic types")
    @Test
    public void com_google_common_collect_ImmutableRangeMap() throws Exception {
        assertInstancesOf(com.google.common.collect.ImmutableRangeMap.class, areImmutable(),
                allowingForSubclassing(),
                provided(Range.class).isAlsoImmutable(),
                provided("K").isAlsoImmutable(),
                provided("V").isAlsoImmutable());
    }


    @PendingImplementation
    @Test
    public void com_google_common_collect_ImmutableRangeSet() throws Exception {
        assertInstancesOf(com.google.common.collect.ImmutableRangeSet.class, areImmutable());
    }


    @PendingImplementation("Lazily computed field 'asList'")
    @Test
    public void com_google_common_collect_ImmutableSet() throws Exception {
        assertInstancesOf(com.google.common.collect.ImmutableSet.class, areImmutable(),
                provided("E").isAlsoImmutable(),
                allowingForSubclassing());
    }


    @PendingImplementation
    @Test
    public void com_google_common_collect_ImmutableSetMultimap() throws Exception {
        assertInstancesOf(com.google.common.collect.ImmutableSetMultimap.class, areImmutable());
    }


    @PendingImplementation
    @Test
    public void com_google_common_collect_ImmutableSortedMap() throws Exception {
        assertInstancesOf(com.google.common.collect.ImmutableSortedMap.class, areImmutable());
    }


    @PendingImplementation
    @Test
    public void com_google_common_collect_ImmutableSortedMultiset() throws Exception {
        assertInstancesOf(com.google.common.collect.ImmutableSortedMultiset.class, areImmutable());
    }


    @PendingImplementation
    @Test
    public void com_google_common_collect_ImmutableSortedSet() throws Exception {
        assertInstancesOf(com.google.common.collect.ImmutableSortedSet.class, areImmutable(),
                provided("E").isAlsoImmutable());
    }


    @Test
    public void com_google_common_collect_ImmutableTable() throws Exception {
        assertInstancesOf(com.google.common.collect.ImmutableTable.class, areImmutable(),
                allowingForSubclassing());
    }

    @Test
    public void com_google_common_collect_Range() throws Exception {
        assertInstancesOf(com.google.common.collect.Range.class, areImmutable(),
                provided("com.google.common.collect.Cut").isAlsoImmutable());
    }


    @Test
    public void com_google_common_base_Optional() throws Exception {
        assertInstancesOf(com.google.common.base.Optional.class, areImmutable(),
                allowingForSubclassing());
    }
}
