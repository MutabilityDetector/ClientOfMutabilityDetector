/*
 * Mutability Detector
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 * Further licensing information for this project can be found in 
 *         license/LICENSE.txt
 */

package org.mutabilitydetector.unittesting.assertionbenchmarks;

import java.math.BigDecimal;

import org.mutabilitydetector.cli.CommandLineOptions;
import org.mutabilitydetector.cli.NamesFromClassResources;
import org.mutabilitydetector.cli.RunMutabilityDetector;
import org.mutabilitydetector.internal.com.google.classpath.ClassPath;
import org.mutabilitydetector.internal.com.google.classpath.ClassPathFactory;


public final class CheckSomeClass {

    private CheckSomeClass() {
        // Hide Utility Class Constructor - Utility classes should not have a public or default constructor
    }
    public static void main(String[] args) {
        checkClass(BigDecimal.class);
    }

    private static void checkClass(Class<?> toAnalyse) {
        ClassPath cp = new ClassPathFactory().createFromJVM();
        String match = toAnalyse.getName().replace("$", "\\$");
        CommandLineOptions options = new CommandLineOptions(System.out, "-v", "-match", match);
        new RunMutabilityDetector(cp, options, new NamesFromClassResources(options.match())).run();
    }

}
