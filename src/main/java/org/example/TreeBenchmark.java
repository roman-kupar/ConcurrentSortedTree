package org.example;

import org.example.cst.ConcurrentSortedTree;
import org.example.cst.IConcurrentSortedTree;
import org.example.util.Utf8;

import java.util.Optional;

public class TreeBenchmark {
    private static void benchmark(int size) {
        IConcurrentSortedTree tree = new ConcurrentSortedTree();
        String prefix = "key";
        String value = "value";
        long startPut = System.nanoTime();
        for (int i = 0; i < size; i++) {
            tree.put(Utf8.bytesOf(prefix + i), Utf8.bytesOf(value + i));
        }
        long endPut = System.nanoTime();
        System.out.printf("Put %d elements: %.2f ms\n", size, (endPut - startPut) / 1_000_000.0);

        long startGet = System.nanoTime();
        int found = 0;
        for (int i = 0; i < size; i++) {
            Optional<byte[]> result = tree.get(Utf8.bytesOf(prefix + i));
            if (result.isPresent() && Utf8.stringOf(result.get()).equals(value + i)) {
                found++;
            }
        }
        long endGet = System.nanoTime();
        System.out.printf("Get %d elements: %.2f ms (found: %d)\n", size, (endGet - startGet) / 1_000_000.0, found);
    }

    public static void main(String[] args) {
        System.out.println("Benchmark: Small tree (100 elements)");
        benchmark(100);
        System.out.println();
        System.out.println("Benchmark: Large tree (100,000 elements)");
        benchmark(100_000);
    }
}

