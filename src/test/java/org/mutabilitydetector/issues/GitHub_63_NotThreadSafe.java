package org.mutabilitydetector.issues;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.junit.Ignore;
import org.junit.Test;
import org.mutabilitydetector.Configurations;
import org.mutabilitydetector.multithreaded.ThreadSafeMutabilityAsserter;
import org.mutabilitydetector.unittesting.MutabilityAssert;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;

@Ignore
@SuppressWarnings("unused")
public final class GitHub_63_NotThreadSafe {

    final class DummyClass {
        private final ImmutableList<String> list = ImmutableList.of();
        private final ImmutableSortedSet<String> sortedSet = ImmutableSortedSet.of();
    }

    /* Configure the amount of concurrency. */
    final int threads = Runtime.getRuntime().availableProcessors() * 32;
    final int triesPerThread = 100;

    @Test
    public void concurrentMutabilityTest() throws Exception {
        Function<Class<?>, Void> asserter = (clazz) -> {
            MutabilityAssert.assertImmutable(clazz);
            return null;
        };
        executeAssertionAcrossThreads(asserter);
    }

    @Test
    public void concurrentMutabilityTestWithThreadsafeAsserter() throws Exception {
        final ThreadSafeMutabilityAsserter ASSERTER = ThreadSafeMutabilityAsserter.configured(Configurations.OUT_OF_THE_BOX_CONFIGURATION);
        Function<Class<?>, Void> asserter = (clazz) -> {
            ASSERTER.assertImmutable(clazz);
            return null;
        };
        executeAssertionAcrossThreads(asserter);
    }



    private void executeAssertionAcrossThreads(Function<Class<?>, Void> asserter) throws InterruptedException, BrokenBarrierException, ExecutionException {
    /* We'll start analysis on multiple threads at roughly the same time. */
        final CyclicBarrier barrier = new CyclicBarrier(threads);
        final ListeningExecutorService es = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(threads));

        final List<ListenableFuture<List<Throwable>>> futures = new ArrayList<>();
        for (int i = 0; i < threads; i++) {
            futures.add(es.submit(() -> {
                /* Await the start signal. */
                barrier.await();

                /* Repeatedly run the analysis; collect failures. */
                final List<Throwable> failures = new ArrayList<>();
                for (int j = 0; j < triesPerThread; j++) {
                    try {
                        asserter.apply(DummyClass.class);
                    } catch (final Throwable t) {
                        failures.add(t);
                    }
                }

                return failures;
            }));
        }

        /* Wait for all threads to complete, collecting any failures. */
        final Iterable<Throwable> failures = Iterables.concat(Futures.allAsList(futures).get());

        /* Print the failures, if any. */
        failures.forEach(Throwable::printStackTrace);

        /* Fail if there are any. */
        assertEquals(Lists.newArrayList(failures), Lists.newArrayList());
    }
}
