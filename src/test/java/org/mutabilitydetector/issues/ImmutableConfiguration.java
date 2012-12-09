package org.mutabilitydetector.issues;

import static org.mutabilitydetector.unittesting.AllowedReason.provided;
import static org.mutabilitydetector.unittesting.MutabilityAssert.assertInstancesOf;
import static org.mutabilitydetector.unittesting.MutabilityMatchers.areImmutable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.junit.Test;

@SuppressWarnings({ "rawtypes" })
public final class ImmutableConfiguration {

    private final Map<String, Object> propMap;

    public ImmutableConfiguration(AbstractConfiguration origConfiguration) {

        Map<String, Object> store = new HashMap<String, Object>();
        // we need the variables interpolated, which doesn't come at the getProperty level
        Configuration interpolated = origConfiguration.interpolatedConfiguration();
        final Iterator keyIter = interpolated.getKeys();
        while (keyIter.hasNext()) {
            String key = (String) keyIter.next();
            store.put(key, (Object) interpolated.getProperty(key));
        }
        this.propMap = Collections.unmodifiableMap(new HashMap<String, Object>(store));
    }
    
    public Object getSomeProperty(String key) {
        return propMap.get(key);
    }

    public static interface Configuration {
        Iterator getKeys();
        Object getProperty(String key);
    }

    public static abstract class AbstractConfiguration {
        public abstract Configuration interpolatedConfiguration();
    }
    
    public static class MutabilityTest {
        @Test
        public void immutableConfiguration_assumeAnyMapIsImmutable() throws Exception {
            assertInstancesOf(ImmutableConfiguration.class, areImmutable(),
                              provided(String.class, Object.class).isAlsoImmutable());
        }
    }
}
