package org.mutabilitydetector.multithreaded;

import com.google.common.collect.Lists;
import org.hamcrest.Matcher;
import org.mutabilitydetector.AnalysisResult;
import org.mutabilitydetector.AnalysisSession;
import org.mutabilitydetector.Configuration;
import org.mutabilitydetector.ConfigurationBuilder;
import org.mutabilitydetector.Configurations;
import org.mutabilitydetector.MutableReasonDetail;
import org.mutabilitydetector.ThreadUnsafeAnalysisSession;
import org.mutabilitydetector.locations.Dotted;
import org.mutabilitydetector.unittesting.MutabilityAssert;
import org.mutabilitydetector.unittesting.internal.AssertionReporter;
import org.mutabilitydetector.unittesting.matchers.reasons.WithAllowedReasonsMatcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.mutabilitydetector.unittesting.MutabilityMatchers.areImmutable;
import static org.mutabilitydetector.unittesting.matchers.reasons.WithAllowedReasonsMatcher.withAllowedReasons;
import static org.mutabilitydetector.unittesting.matchers.reasons.WithAllowedReasonsMatcher.withNoAllowedReasons;

public class ThreadSafeMutabilityAsserter {

    private final AssertionReporter reporter;
    private final AnalysisSession analysisSession;

    private ThreadSafeMutabilityAsserter(AssertionReporter reporter, AnalysisSession analysisSession) {
        this.reporter = reporter;
        this.analysisSession = analysisSession;
    }

    /**
     * Create a new asserter with an existing {@link Configuration}.
     * <p>
     * Example:
     * <pre><code>
     * MutabilityAsserter.configured(MyConfigurations.DEFAULT_CONFIGURATIONS);
     * </code></pre>
     * @see Configurations
     * @see Configurations#JDK_CONFIGURATION
     * @see Configurations#NO_CONFIGURATION
     * @see Configurations#OUT_OF_THE_BOX_CONFIGURATION
     */
    public static ThreadSafeMutabilityAsserter configured(Configuration configuration) {
        return new ThreadSafeMutabilityAsserter(
                new AssertionReporter(),
                new ThreadLocalAnalysisSession(() -> ThreadUnsafeAnalysisSession.createWithCurrentClassPath(configuration)));
    }

    /**
     * Create a new asserter with a {@link Configuration} as built by the given
     * {@link ConfigurationBuilder}.
     * <p>
     * Use this method when you want to build a one-time Configuration inline..
     * <p>
     * Example:
     *
     * <pre>
     * <code>
     *  MutabilityAsserter.configured(new ConfigurationBuilder() {
     *   &#064;Override public void configure() {
     *     hardcodeAsDefinitelyImmutable(ActuallyImmutable.class);
     *   }
     * });
     * </code>
     * </pre>
     */
    public static ThreadSafeMutabilityAsserter configured(ConfigurationBuilder configuration) {
        return new ThreadSafeMutabilityAsserter(new AssertionReporter(),
                new ThreadLocalAnalysisSession(() -> ThreadUnsafeAnalysisSession.createWithCurrentClassPath(configuration.build())));
    }

    /**
     * @see MutabilityAssert#assertImmutable(Class)
     */
    public void assertImmutable(Class<?> expectedImmutableClass) {
        reporter.assertThat(getResultFor(expectedImmutableClass), withNoAllowedReasons(areImmutable()));
    }

    /**
     * @see MutabilityAssert#assertInstancesOf(Class, Matcher)
     */
    public void assertInstancesOf(Class<?> clazz, Matcher<AnalysisResult> mutabilityMatcher) {
        reporter.assertThat(getResultFor(clazz), withNoAllowedReasons(mutabilityMatcher));
    }

    /**
     * @see MutabilityAssert#assertInstancesOf(Class, Matcher, Matcher)
     */
    @SuppressWarnings("unchecked")
    public void assertInstancesOf(Class<?> clazz, Matcher<AnalysisResult> mutabilityMatcher,
                                  Matcher<MutableReasonDetail> allowing) {
        WithAllowedReasonsMatcher areImmutable_withReasons = withAllowedReasons(mutabilityMatcher, Collections.singletonList((allowing)));
        reporter.assertThat(getResultFor(clazz), areImmutable_withReasons);
    }

    /**
     * @see MutabilityAssert#assertInstancesOf(Class, Matcher, Matcher, Matcher)
     */
    @SuppressWarnings("unchecked")
    public void assertInstancesOf(Class<?> clazz, Matcher<AnalysisResult> mutabilityMatcher,
                                  Matcher<MutableReasonDetail> allowingFirst,
                                  Matcher<MutableReasonDetail> allowingSecond) {

        WithAllowedReasonsMatcher areImmutable_withReasons = withAllowedReasons(mutabilityMatcher,
                asList(allowingFirst, allowingSecond));
        reporter.assertThat(getResultFor(clazz), areImmutable_withReasons);
    }

    /**
     * @see MutabilityAssert#assertInstancesOf(Class, Matcher, Matcher, Matcher, Matcher)
     */
    @SuppressWarnings("unchecked")
    public void assertInstancesOf(Class<?> clazz, Matcher<AnalysisResult> mutabilityMatcher,
                                  Matcher<MutableReasonDetail> allowingFirst,
                                  Matcher<MutableReasonDetail> allowingSecond,
                                  Matcher<MutableReasonDetail> allowingThird) {

        WithAllowedReasonsMatcher areImmutable_withReasons = withAllowedReasons(mutabilityMatcher,
                asList(allowingFirst,
                        allowingSecond,
                        allowingThird));

        reporter.assertThat(getResultFor(clazz), areImmutable_withReasons);
    }

    /**
     * @see MutabilityAssert#assertInstancesOf(Class, Matcher, Matcher, Matcher, Matcher, Matcher...)
     */
    public void assertInstancesOf(Class<?> clazz, Matcher<AnalysisResult> mutabilityMatcher,
                                  Matcher<MutableReasonDetail> allowingFirst,
                                  Matcher<MutableReasonDetail> allowingSecond,
                                  Matcher<MutableReasonDetail> allowingThird,
                                  Matcher<MutableReasonDetail>... allowingRest) {

        List<Matcher<MutableReasonDetail>> allowedReasonMatchers = new ArrayList<>();
        allowedReasonMatchers.add(allowingFirst);
        allowedReasonMatchers.add(allowingSecond);
        allowedReasonMatchers.add(allowingThird);
        allowedReasonMatchers.addAll(asList(allowingRest));

        WithAllowedReasonsMatcher areImmutable_withReasons = withAllowedReasons(mutabilityMatcher, allowedReasonMatchers);
        reporter.assertThat(getResultFor(clazz), areImmutable_withReasons);
    }

    /**
     * @see MutabilityAssert#assertInstancesOf(Class, Matcher, Iterable)
     */
    public void assertInstancesOf(Class<?> clazz,
                                  Matcher<AnalysisResult> mutabilityMatcher,
                                  Iterable<Matcher<MutableReasonDetail>> allowingAll) {
        Iterable<Matcher<MutableReasonDetail>> allowedReasonMatchers = Lists.newArrayList(allowingAll);

        WithAllowedReasonsMatcher areImmutable_withReasons = withAllowedReasons(mutabilityMatcher, allowedReasonMatchers);
        reporter.assertThat(getResultFor(clazz), areImmutable_withReasons);
    }

    private AnalysisResult getResultFor(Class<?> clazz) {
        return analysisSession.resultFor(Dotted.fromClass(clazz));
    }

}
