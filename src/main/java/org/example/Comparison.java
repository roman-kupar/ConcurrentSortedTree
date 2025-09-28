package org.example;

import org.example.cst.ConcurrentSortedTree;
import org.example.util.Utf8;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class Comparison {
    private static void benchmarkCopyOnWriteArrayList(int size) {
        CopyOnWriteArrayList<Map.Entry<String, String>> list = new CopyOnWriteArrayList<>();
        String prefix = "key";
        String value = "value";
        long startPut = System.nanoTime();
        for (int i = 0; i < size; i++) {
            String k = prefix + i;
            String v = value + i;

            int pos = 0;
            while (pos < list.size() && list.get(pos).getKey().compareTo(k) < 0) pos++;
            list.add(pos, new AbstractMap.SimpleEntry<>(k, v));
        }
        long endPut = System.nanoTime();
        System.out.printf("CopyOnWriteArrayList Put %d elements: %.2f ms\n", size, (endPut - startPut) / 1_000_000.0);

        long startGet = System.nanoTime();
        int found = 0;
        for (int i = 0; i < size; i++) {
            String k = prefix + i;
            for (Map.Entry<String, String> entry : list) {
                if (entry.getKey().equals(k) && entry.getValue().equals(value + i)) {
                    found++;
                    break;
                }
            }
        }
        long endGet = System.nanoTime();
        System.out.printf("CopyOnWriteArrayList Get %d elements: %.2f ms (found: %d)\n", size, (endGet - startGet) / 1_000_000.0, found);
    }

    public static void main(String[] args) {
        int[] sizes = {100, 100_00};
        for (int size : sizes) {
            System.out.println("\n--- Benchmark: " + size + " elements ---");
            TreeBenchmark.benchmark(size);
            benchmarkCopyOnWriteArrayList(size);
        }
    }
}
