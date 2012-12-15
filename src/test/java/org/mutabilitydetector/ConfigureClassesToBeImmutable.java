package org.mutabilitydetector;

import static org.mutabilitydetector.unittesting.MutabilityAsserter.configured;
import static org.mutabilitydetector.unittesting.MutabilityMatchers.areNotImmutable;

import javax.annotation.concurrent.Immutable;

import org.junit.Test;
import org.mutabilitydetector.unittesting.MutabilityAsserter;

public class ConfigureClassesToBeImmutable {
    
    @Test
    public void canConfigureClassesToBeOverrideMutabilityDetectorsResult() throws Exception {
        MUTABILITY.assertImmutable(DigitsOfPiFormatter.class);
    }
    
    @Test
    public void hardcodedResultsAreIgnoredWhenTestingClassDirectly() throws Exception {
        MUTABILITY.assertInstancesOf(DigitsOfPi.class, areNotImmutable());
    }

    @Test
    public void defaultHardcodedConfigurationSolvesTheStringProblem() throws Exception {
        MUTABILITY.assertImmutable(HasAStringField.class);
    }

    @Test
    public void stringIsStillMutableWhenTestingDirectly() throws Exception {
        MUTABILITY.assertInstancesOf(String.class, areNotImmutable());
    }

    @Test
    public void canMergeHardcodedResultsFromAnExistingConfiguration() throws Exception {
        MUTABILITY.assertImmutable(HasAFieldWithImmutable3rdPartyType.class);
    }
    
    public static final Configuration CONFIGURATION_FOR_3RD_PARTY_LIBRARY = new ConfigurationBuilder() {
        @Override public void configure() {
            hardcodeAsDefinitelyImmutable(ClassFrom3rdPartyLibrary.class);
        }
    }.build();
    
    public static final MutabilityAsserter MUTABILITY = configured(new ConfigurationBuilder() {
        @Override public void configure() {
            hardcodeAsDefinitelyImmutable(DigitsOfPi.class);
            mergeHardcodedResultsFrom(JDK);
            mergeHardcodedResultsFrom(CONFIGURATION_FOR_3RD_PARTY_LIBRARY);
        }
    });
    
    
    /**
     * MutabilityDetector is wrong, {@link DigitsOfPi} is immutable.
     */
    @Immutable
    static final class DigitsOfPi {
        private final int[] sillyWayToStorePi = new int[] { 3, 141 };
        
        public int piDigitsLeftOfDecimalPlace() {
            return sillyWayToStorePi[0];
        }

        public int piDigitsRightOfDecimalPlace() {
            return sillyWayToStorePi[1];
        }
    }
    
    /**
     * {@link DigitsOfPiFormatter} is considered mutable because of the
     * transitivity of mutability, and the incorrect analysis performed
     * on {@link DigitsOfPi}.
     */
    @Immutable
    static final class DigitsOfPiFormatter {
        private final DigitsOfPi other;

        public DigitsOfPiFormatter(DigitsOfPi other) {
            this.other = other;
        }
        
        public String formatPi() {
            return other.piDigitsLeftOfDecimalPlace() + "." + other.piDigitsRightOfDecimalPlace();
        }
        
    }
    
    @Immutable
    static final class HasAStringField {
        public final String stringField;
        public HasAStringField(String stringField) {
            this.stringField = stringField;
        }
    }
    
    @Immutable
    static final class HasAFieldWithImmutable3rdPartyType {
        public final ClassFrom3rdPartyLibrary field;
        public HasAFieldWithImmutable3rdPartyType(ClassFrom3rdPartyLibrary field) {
            this.field = field;
        }
    }
    
    static final class ClassFrom3rdPartyLibrary { }
}
