package org.mutabilitydetector.issues;

import static org.mutabilitydetector.unittesting.AllowedReason.assumingFields;
import static org.mutabilitydetector.unittesting.MutabilityAssert.assertInstancesOf;
import static org.mutabilitydetector.unittesting.MutabilityMatchers.areImmutable;

import java.util.Date;

import org.junit.Test;

public class GitHubIssue_28 {

    public static final class Claim {
        public final double amount;
        private final Date failureDate;

        Claim(double amount, Date failureDate) {
            this.amount = amount;
            this.failureDate = new Date(failureDate.getTime());
        }

        public Date getFailureDate() {
            return new Date(failureDate.getTime());
        }
    }

    @Test
    public void isImmutable() {
        assertInstancesOf(Claim.class, 
                          areImmutable(),
                          assumingFields("failureDate").areNotModifiedAndDoNotEscape());
    }
    
    public static final class ClaimStoringTimeOnly {
        public final double amount;
        private final long failureTime;
        
        ClaimStoringTimeOnly(double amount, Date failureDate) {
            this.amount = amount;
            this.failureTime = failureDate.getTime();
        }
        
        public Date getFailureDate() {
            return new Date(failureTime);
        }
    }

    @Test
    public void isImmutable_whenUsingLongFieldInsteadOfDate() {
        assertInstancesOf(ClaimStoringTimeOnly.class, areImmutable());
    }
    
}
