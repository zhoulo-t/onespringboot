package com.example.demo.test;


import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MapTest {

    public static void main(String[] args) {

        List<String> list = Lists.newArrayList("a", "b", "c", "d", "e");
        List<String> list2 = Lists.newArrayList("ac", "bc", "cc", "dc", "ec");
        //合并
        List<String> stringList = Stream.of(list, list2)
                .flatMap(List::stream)
                .collect(Collectors.toList());


        stringList.stream().collect(Collectors.groupingBy(String::length, Collectors.mapping(String::toUpperCase, Collectors.toSet())))
                .forEach((key, value) -> {
                    System.out.println("Key: " + key + ", Values: " + value);
                });

        stringList.stream().collect(Collectors.partitioningBy(s -> s.length() > 1))
                .forEach((key, value) -> {
                    System.out.println("Key: " + key + ", Values: " + value);
                });

        List<String> names = Arrays.asList("Charlie", "Bob11", "Alice");
        names.stream().collect(Collectors.toMap(String::length, name -> name, (existing, rep)-> existing)).forEach(
                (key, value) -> {
                    System.out.println("Key: " + key + ", Value: " + value);
                }
        );

        IntSummaryStatistics stats = names.stream().collect(Collectors.summarizingInt(String::length));
        System.out.println("Count: " + stats.getCount());
        System.out.println("Sum: " + stats.getSum());
        System.out.println("Min: " + stats.getMin());
        System.out.println("Max: " + stats.getMax());
        System.out.println("Average: " + stats.getAverage());

        Optional<String> concatenated = names.stream().collect(Collectors.reducing((s1, s2) -> s1 + s2));
        Optional<Integer> concatenated1 = names.stream().map(String::length).reduce(Integer::sum);
        concatenated.ifPresent(s -> System.out.println("Concatenated: " + s));
        concatenated1.ifPresent(s -> System.out.println("Concatenated1: " + s));

        ConcurrentMap<String, Integer> concurrentMap = names.stream().collect(Collectors.toConcurrentMap(name -> name, String::length, (existing, replacement) -> existing));
        concurrentMap.forEach((key, value) -> {
            System.out.println("Key: " + key + ", Value: " + value);
        });
    }

}
