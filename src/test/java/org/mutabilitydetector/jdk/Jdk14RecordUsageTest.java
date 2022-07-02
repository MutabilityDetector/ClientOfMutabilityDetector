package org.mutabilitydetector.jdk;

import static org.mutabilitydetector.unittesting.MutabilityAssert.*;

import java.util.List;
import java.util.Set;

import org.junit.Test;

public class Jdk14RecordUsageTest {

    public record WrapsImmutableObjects(String name, int count) {
    }

    public record WrapsMutableObjects(List<String> list, Set<String> set) {
        public WrapsMutableObjects(List<String> list, Set<String> set) {
            this.list = List.copyOf(list);
            this.set = Set.copyOf(set);
        }
    }

    @Test
    public final void immutable_record() {
        assertImmutable(WrapsImmutableObjects.class);
    }

    @Test
    public final void immutable_wrapper() {
        assertImmutable(WrapsMutableObjects.class);
    }
}
