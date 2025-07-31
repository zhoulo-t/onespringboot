package com.example.demo.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SteamTest {

    public static void main(String[] args) {

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.stream();

        String[] array = {"One", "Two", "Three"};
        Stream<String> streamFromArray = Arrays.stream(array);

        Stream.of("One", "Two", "Three").forEach(System.out::println);

        List<List<String>> listOfLists = new ArrayList<>();
        listOfLists.add(Arrays.asList("A", "B"));
        listOfLists.add(Arrays.asList("C", "D"));
        Stream<List<String>> streamOfLists = listOfLists.stream();
        streamOfLists.flatMap(List::stream).forEach(System.out::println);

        List<String> names = Arrays.asList("Charlie", "Alice", "Bob1");
        List<String> sortedByLength = names.stream().sorted(Comparator.comparingInt(String::length)).collect(Collectors.toList());


//        IntStream intStream = names.stream().mapToInt(String::length);
//        intStream.forEach(System.out::println);

//        IntStream intStream = IntStream.range(1, 5);
//        Stream<Integer> boxedStream = intStream.boxed();
//        boxedStream.onClose(() -> System.out.println("Stream closed"))
//                   .forEach(System.out::println);
        boolean b = names.stream().min(Comparator.comparingInt(String::length))
                .map(s -> s.length() > 3)
                .orElse(false);
        System.out.println(b);


        String[] array1 = names.stream().toArray(String[]::new);
        Map<Boolean, List<String>> collect = names.stream().collect(Collectors.partitioningBy(
                name -> name.length() > 5
        ));
        System.out.println("Partitioned Map: " + collect);

        List<String> result = names.stream().collect(Collectors.collectingAndThen(Collectors.toList(),
                list -> {
                    list.add("New Element");
                    return list;
                }));
        System.out.println("Collected List: " + result);


    }

}
