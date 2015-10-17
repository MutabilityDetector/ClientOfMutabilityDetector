package org.mutabilitydetector.stackoverflow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("unused")
public final class ImmutableClass {

    private final String name;

    private final List<Integer> listOfNumbers;

    public ImmutableClass(String name, List<Integer> listOfNumbers) {
        this.name = name;
        this.listOfNumbers = Collections.unmodifiableList(new ArrayList<>(listOfNumbers));
    }

    public String getName() {
        return name;
    }

    public List<Integer> getListOfNumbers() {
        return listOfNumbers;
    }
}
