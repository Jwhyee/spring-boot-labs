package com.spring.labs.functional;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertTrue;

public class FunctionalTest {
    @Test
    void supplierTest() {
        Set<String> stringSet = new HashSet<>();
        for (int i = 1; i <= 100; i++) {
            stringSet.add("string " + i);
        }

        final int idx = CustomFunctionalFactory.findIdx(stringSet, () -> "string 20");

        int i = 0;
        for (String s : stringSet) {
            if (i == idx) {
                assertTrue("string 20".equals(s));
            }
            i++;
        }
    }

    @Test
    void consumerFunctionalTest() {
        List<String> stringList = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            stringList.add("string " + i);
        }

        CustomFunctionalFactory.forEachRemaining(stringList, (item) -> System.out.println(item));
    }

    @Test
    void predicateFunctionalTest() {
        List<String> stringList = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            stringList.add("string " + i);
        }

        final List<String> filteredList = CustomFunctionalFactory
                .filter(stringList, (item) -> item.contains("1"));

        for (String s : filteredList) {
            System.out.println(s);
        }

    }

    @Test
    void functionFunctionalTest() {
        List<String> stringList = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            stringList.add("string " + i);
        }

        final List<String> filteredList = CustomFunctionalFactory
                .map(stringList, (item) -> item.concat("^_^"));

        for (String s : filteredList) {
            System.out.println(s);
        }
    }
}
