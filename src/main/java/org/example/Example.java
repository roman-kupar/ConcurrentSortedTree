package org.example;

import org.example.cst.ConcurrentSortedTree;
import org.example.cst.ITree;
import org.example.util.Utf8;

import java.util.*;
import java.util.concurrent.*;

import static org.example.util.Utf8.bytesOf;

public class Example {
    public static void main(String[] args) {

        /* Real-life example : YouTrack Issues DB */

        /* Data structure */
        ConcurrentSortedTree tree = new ConcurrentSortedTree();

        /* Add some YouTrack issues */
        tree.put(bytesOf("YT-101"), bytesOf("Login page is broken"));
        tree.put(bytesOf("YT-102"), bytesOf("Signup page is broken"));
        tree.put(bytesOf("YT-200"), bytesOf("Add API endpoints for reports"));

        /* Retrieve existing issue */
        tree.get(bytesOf("YT-101"))
                .ifPresent(value -> System.out.println("Issue retrieved: " + Utf8.stringOf(value)));

        /* Try retrieving non-existing issue */
        tree.get(bytesOf("YT-999"))
                .ifPresentOrElse(
                        /* Should not be true */
                        value -> System.out.println("Issue retrieved: " + Utf8.stringOf(value))
                        ,
                        /* Expected */
                        () -> System.out.println("Issue YT-999 not found")
                );

        /* Update existing issue */
        tree.put(bytesOf("YT-101"), bytesOf("Login button does not respond"))
                .ifPresent(oldValue -> System.out.println("Issue YT-101 updated from: " + Utf8.stringOf(oldValue) + " to 'Login button does not respond'"));

        /* Retrieve updated issue */
        tree.get(bytesOf("YT-101"))
                .ifPresent(value -> System.out.println("Issue retrieved: " + Utf8.stringOf(value)));

        /* Try retrieving non-existing issue again */
        tree.get(bytesOf("YT-999"))
                .ifPresentOrElse(
                        /* Should not be true */
                        value -> System.out.println("Issue retrieved: " + Utf8.stringOf(value))
                        ,
                        /* Expected */
                        () -> System.out.println("Issue YT-999 not found")
                );



        ///// Concurrency test /////
        System.out.println("\n--- Concurrency test ---");

        final int THREADS = 8;
        final int KEYS    = 10;

        /* Thread pool */
        var pool  = Executors.newFixedThreadPool(THREADS);

        /* Latch to start threads together */
        var start = new CountDownLatch(1);

        /* Latch to wait for all threads*/
        var done  = new CountDownLatch(THREADS);

        /* Launch workers */
        for (int t = 0; t < THREADS; t++) {
            final int tid = t;
            pool.submit(() -> {
                try {
                    /* Wait until all are ready, then start together */
                    start.await();
                    var rnd = ThreadLocalRandom.current();

                    /* Each thread adds KEYS entries */
                    for (int i = 0; i < KEYS; i++) {
                        String k = "Issue" + tid + "-" + i;
                        String v = "Value-" + i;
                        tree.put(bytesOf(k), bytesOf(v));

                        /* Random small pause to increase chance of interleaving */
                        if (rnd.nextInt(3) == 0) Thread.sleep(rnd.nextInt(1, 6));
                    }
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                } finally {
                    /* Mark this thread as done */
                    done.countDown();
                }
            });
        }

        /* Start all threads */
        start.countDown();

        /* Wait until all threads finish, with timeout to avoid infinite wait */
        try {
            if (!done.await(30, TimeUnit.SECONDS)) {
                System.out.println("Timeout waiting for threads to finish");
            }
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }

        /* Shutdown thread pool */
        pool.shutdown();

        /* Verify all entries are present and correct */
        int ok = 0, bad = 0;
        List<String> mismatches = new ArrayList<>();
        for (int t = 0; t < THREADS; t++) {
            for (int i = 0; i < KEYS; i++) {
                String k = "Issue" + t + "-" + i;
                String expect = "Value-" + i;
                String actual = tree.get(bytesOf(k)).map(Utf8::stringOf).orElse("<missing>");
                if (expect.equals(actual)) {
                    ok++;
                } else {
                    bad++;
                    mismatches.add(String.format("Mismatch: key=%s expected=%s actual=%s", k, expect, actual));
                }
            }
        }

        /* Print mismatches if any */
        if (!mismatches.isEmpty()) {
            System.out.println("\n--- Mismatches ---");
            mismatches.forEach(System.out::println);
        } else {
            System.out.println("All concurrent entries verified successfully.");
        }

        /* Summary */
        System.out.printf("Verification summary: OK=%d BAD=%d\n", ok, bad);
    }
}