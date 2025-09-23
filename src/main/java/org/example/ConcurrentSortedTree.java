package org.example;

import java.util.Comparator;
import java.util.NavigableMap;
import java.util.Optional;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.jetbrains.annotations.Nullable;

import static java.util.Objects.requireNonNull;

public class ConcurrentSortedTree implements IConcurrentSortedTree {

    private final TreeMap<byte[],byte[]> map;
    private final ReentrantReadWriteLock lock;

    public ConcurrentSortedTree() {
        this.map = new TreeMap<>(LEXICOGRAPHIC);
        this.lock = new ReentrantReadWriteLock();
    }

    @Override
    public Optional<byte[]> get(byte[] key)  {
        requireNonNull(key, "key must not be null");

        lock.readLock().lock();

        try {
            byte[] value = map.get(key);

            return safeCopy(value);

        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public Optional<byte[]> put(byte[] key, byte[] value) {
        requireNonNull(key, "key must not be null");
        requireNonNull(value, "value must not be null");

        byte[] valueCopy = copyOf(value);
        byte[] keyCopy = copyOf(key);

        lock.writeLock().lock();

        try {
            byte[] oldValue = map.put(keyCopy, valueCopy);
            return safeCopy(oldValue);
        } finally {
            lock.writeLock().unlock();
        }
    }

    private static final Comparator<byte[]> LEXICOGRAPHIC = (a, b) -> {
        int min = Math.min(a.length, b.length);
        for (int i = 0; i < min; i++) {
            int ai = a[i] & 0xFF, bi = b[i] & 0xFF;
            if (ai != bi) return Integer.compare(ai, bi);
        }
        return Integer.compare(a.length, b.length);
    };

    private static Optional<byte[]> safeCopy(byte @Nullable [] arr) {
        return arr == null ? Optional.empty() : Optional.of(copyOf(arr));
    }


    private static byte[] copyOf(byte[] array) {
        byte[] copy = new byte[array.length];
        System.arraycopy(array, 0, copy, 0, array.length);
        return copy;
    }
}

