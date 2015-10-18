package org.mutabilitydetector.multithreaded;

import org.mutabilitydetector.AnalysisErrorReporter;
import org.mutabilitydetector.AnalysisResult;
import org.mutabilitydetector.AnalysisSession;
import org.mutabilitydetector.locations.Dotted;

import java.util.Map;
import java.util.function.Supplier;

public class ThreadLocalAnalysisSession implements AnalysisSession {

    private final Supplier<AnalysisSession> analysisSessionSupplier;
    private final ThreadLocal<AnalysisSession> perThreadSessions = new ThreadLocal<>();

    public ThreadLocalAnalysisSession(Supplier<AnalysisSession> analysisSessionSupplier) {
        this.analysisSessionSupplier = analysisSessionSupplier;
    }

    private AnalysisSession lazyGetThreadAnalysisSession() {
        if (perThreadSessions.get() == null) {
            AnalysisSession newSession = analysisSessionSupplier.get();
            perThreadSessions.set(newSession);
        }
        return perThreadSessions.get();
    }

    @Override
    public AnalysisResult resultFor(Dotted dotted) {
        return lazyGetThreadAnalysisSession().resultFor(dotted);
    }

    @Override
    public AnalysisErrorReporter errorReporter() {
        return lazyGetThreadAnalysisSession().errorReporter();
    }

    @Override
    public Iterable<AnalysisResult> getResults() {
        return lazyGetThreadAnalysisSession().getResults();
    }

    @Override
    public Map<Dotted, AnalysisResult> resultsByClass() {
        return lazyGetThreadAnalysisSession().resultsByClass();
    }

    @Override
    public Iterable<AnalysisErrorReporter.AnalysisError> getErrors() {
        return lazyGetThreadAnalysisSession().getErrors();
    }
}
