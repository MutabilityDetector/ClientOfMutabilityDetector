package org.mutabilitydetector.issues;

import javax.annotation.concurrent.Immutable;

@Immutable
public final class GitHubIssue_25_AnalysisCausesClassToBeLoaded {
    static {
        String obnoxiousMessage = "WOOOOHOOOOOO!!!!\n" +
            "===============================\n" +
            "Look at my big great annoying static initialisation output.\n" +
            "In your face! Neener neener!\n" +
            "===============================\n";
        System.out.println(obnoxiousMessage);
        Object o = null;
        o.toString();
    }

    private final String myField;

    public GitHubIssue_25_AnalysisCausesClassToBeLoaded() {
        this.myField = new StringBuilder("hey ho").toString();
    }
}
