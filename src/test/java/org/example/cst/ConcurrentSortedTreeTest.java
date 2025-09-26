package org.example.cst;

import org.example.util.Utf8;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

public class ConcurrentSortedTreeTest {
    @Test
    public void testPutAndGetSingleThread() {
        IConcurrentSortedTree tree = new ConcurrentSortedTree();
        String key = "testKey";
        String value = "testValue";
        tree.put(Utf8.bytesOf(key), Utf8.bytesOf(value));
        Optional<byte[]> result = tree.get(Utf8.bytesOf(key));
        assertTrue(result.isPresent());
        assertEquals(value, Utf8.stringOf(result.get()));
    }

    @Test
    public void testGetMissingKey() {
        IConcurrentSortedTree tree = new ConcurrentSortedTree();
        Optional<byte[]> result = tree.get(Utf8.bytesOf("missing"));
        assertFalse(result.isPresent());
    }

    @Test
    public void testConcurrentPutAndGet() throws InterruptedException {
        IConcurrentSortedTree tree = new ConcurrentSortedTree();
        int threads = 10;
        int itemsPerThread = 100;
        ExecutorService executor = Executors.newFixedThreadPool(threads);
        CountDownLatch latch = new CountDownLatch(threads);
        for (int t = 0; t < threads; t++) {
            final int threadId = t;
            executor.submit(() -> {
                for (int i = 0; i < itemsPerThread; i++) {
                    String key = "T" + threadId + "-" + i;
                    String value = "V" + threadId + "-" + i;
                    tree.put(Utf8.bytesOf(key), Utf8.bytesOf(value));
                }
                latch.countDown();
            });
        }
        latch.await();
        for (int t = 0; t < threads; t++) {
            for (int i = 0; i < itemsPerThread; i++) {
                String key = "T" + t + "-" + i;
                String value = "V" + t + "-" + i;
                Optional<byte[]> result = tree.get(Utf8.bytesOf(key));
                assertTrue(result.isPresent(), "Missing: " + key);
                assertEquals(value, Utf8.stringOf(result.get()), "Mismatch: " + key);
            }
        }
        executor.shutdown();
    }
}

