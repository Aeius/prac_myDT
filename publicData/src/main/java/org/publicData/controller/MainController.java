package org.publicData.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.Random;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    @GetMapping("/stream")
    public String stream () throws IOException {
        // 생성하기
        String[] arr = new String[]{"a", "b", "c"};
        Stream<String> stream = Arrays.stream(arr);

        List<String> list = Arrays.asList("d", "e", "f");
        Stream<String> stream2 = list.stream();
        Stream<String> stream3 = list.parallelStream(); // 병렬처리 스트림

        Stream<String> stream4 = Stream.<String>builder().add("g").add("h").add("i").build();
        
        Stream<String> stream5 = Stream.generate(()-> "l").limit(3);

        Stream<Integer> stream6 = Stream.iterate(10, n -> n + 2).limit(5);

        IntStream stream7 = IntStream.range(1, 5);
        LongStream stream8 = LongStream.rangeClosed(6, 10);
        Stream<Integer> stream9 = IntStream.range(5, 10).boxed();
        DoubleStream stream10 = new Random().doubles(3);

        IntStream stream11 = "abcd".chars();
        Stream<String> stream12 = Pattern.compile(", ").splitAsStream("a, b, c");

        //Stream<String> stream13 = Files.lines(Paths.get("file.txt"), Charset.forName("UTF-8"));

        class Product {
            String name;
            int amount;
            Product(int amount, String name){
                this.amount = amount;
                this.name = name;
            }
            Product(int amount){
                this.amount = amount;
            }
            public int getAmount() {
                return amount;
            }
            public String getName() {
                return name;
            }
        }
        List<Product> productList = Arrays.asList(new Product(100), new Product(20));
        Stream<Product> stream14 = productList.parallelStream();
        boolean isParallel = stream14.isParallel();

        boolean isMany = stream14.map(product -> product.amount).anyMatch(amount -> amount > 100);
        stream14.sequential();
        boolean isParallel2 = stream14.isParallel();

        Stream<String> stream15 = Stream.concat(stream, stream2);


        // 가공하기
        // -- 문자열 정리
        List<String> names = Arrays.asList("Eric", "Elena", "Java");
        // 특정 문자 포함한 문자열만 모으기
        Stream<String> stream16 = names.stream().filter(name -> name.contains("E"));
        // String의 함수를 이용한 문자열 치환
        Stream<String> stream17 = names.stream().map(String::toUpperCase);
        // 객체의 get메서드를 이용하여 해당 컬럼만 모으기
        Stream<Integer> stream18 = productList.stream().map(Product::getAmount);
        // 평균 계산 하기
        productList.stream().flatMapToInt(product -> IntStream.of(product.getAmount())).average().ifPresent(avg -> System.out.println(avg));
        // -- 정렬
        // 숫자 정렬
        IntStream.of(14,11,20,39,23).sorted().boxed().collect(Collectors.toList());
        List<String> langList = Arrays.asList("Java", "Scala", "Python", "Go", "Swift", "Groovy");
        // 문자 기준
        langList.stream().sorted().collect(Collectors.toList());
        langList.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        // 길이 기준
        langList.stream().sorted(Comparator.comparingInt(String::length)).collect(Collectors.toList());
        langList.stream().sorted((s1, s2) -> s2.length() - s1.length()).collect(Collectors.toList());

        // 결과 만들기
        long sum = IntStream.of(1,3,5,7,9).peek(System.out::println).sum();
        long count = LongStream.of(1,2,3,4,5).count();
        OptionalInt min = IntStream.of(11,3,9,23).min();
        OptionalInt max = IntStream.of(11,3,9,23).max();
        DoubleStream.of(1.1,2.2,3.3,4.4,5.5).average().ifPresent(System.out::println);

        IntStream.rangeClosed(1, 3).reduce((a,b) -> Integer.sum(a,b));
        IntStream.range(1, 4).reduce(10, Integer::sum);
        int num = Arrays.asList(1,2,3).parallelStream().reduce(10, Integer::sum, (a, b) -> a + b);

        List<Product> products = Arrays.asList(
            new Product(10, "apple"),
            new Product(20, "pear"),
            new Product(30, "banana"),
            new Product(20, "orange")
        );
        // Collecting
        // List 화
        List<String> prodNameList = products.stream().map(Product::getName).collect(Collectors.toList());
        // 문자열 화
        String listToString = products.stream().map(Product::getName).collect(Collectors.joining());
        String listToString2 = products.stream().map(Product::getName).collect(Collectors.joining(",", "<", ">"));
        // 평균
        double averageAmount = products.stream().collect(Collectors.averagingDouble(Product::getAmount));
        // 합계
        int sumAmount = products.stream().collect(Collectors.summingInt(Product::getAmount));
        int sumAmount2 = products.stream().mapToInt(Product::getAmount).sum();
        // average, count, sum, min, max 한번에 볼 수 있도록 해주는 객체
        IntSummaryStatistics statistics = products.stream().collect(Collectors.summarizingInt(Product::getAmount)); 
        // 기준에 따라 List로 모아줌
        Map<Integer, List<Product>> collectorMapOfLists = products.stream().collect(Collectors.groupingBy(Product::getAmount));
        // 조건에 따라 true, false 끼리 모아줌
        Map<Boolean, List<Product>> mapPartitioned = products.stream().collect(Collectors.partitioningBy(product -> product.getAmount() == 20));
        // 수정불가한 Set 으로 변환
        Set<Product> unmodifySet = products.stream().collect(Collectors.collectingAndThen(Collectors.toSet(), Collections::unmodifiableSet));
        
        // Matching
        boolean anyMatch = names.stream().anyMatch(name -> name.contains("a"));
        boolean allMatch = names.stream().allMatch(name -> name.length() > 3);
        boolean noneMatch = names.stream().noneMatch(name -> name.endsWith("s"));

        names.stream().forEach(System.out::println);
        products.stream().forEach(System.out::println);
        return "stream";
    }
}
