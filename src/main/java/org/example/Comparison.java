package org.example;

import java.util.concurrent.ConcurrentSkipListMap;

public class Comparison {
    private static void benchmarkConcurrentTreeMap(int size) {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        String prefix = "key";
        String value = "value";
        long startPut = System.nanoTime();
        for (int i = 0; i < size; i++) {
            String k = prefix + i;
            String v = value + i;
            map.put(k, v);
        }
        long endPut = System.nanoTime();
        System.out.printf("ConcurrentSkipListMap Put %d elements: %.2f ms\n", size, (endPut - startPut) / 1_000_000.0);

        long startGet = System.nanoTime();
        int found = 0;
        for (int i = 0; i < size; i++) {
            String k = prefix + i;
            String v = map.get(k);
            if (v != null && v.equals(value + i)) {
                found++;
            }
        }
        long endGet = System.nanoTime();
        System.out.printf("ConcurrentSkipListMap Get %d elements: %.2f ms\n", size, (endGet - startGet) / 1_000_000.0);
    }

    public static void main(String[] args) {
        int[] sizes = {100, 100_00};
        for (int size : sizes) {
            System.out.println("\n--- Benchmark: " + size + " elements ---");
            TreeBenchmark.benchmark(size);
            benchmarkConcurrentTreeMap(size);
        }
    }
}
