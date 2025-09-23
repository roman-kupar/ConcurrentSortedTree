package org.example;

import java.util.Optional;

public class ConcurrentSortedTree<K,V> implements IConcurrentSortedTree<K,V> {

    @Override
    public Optional<V> get(K key) {
        return Optional.empty();
    }

    @Override
    public Optional<V> put(K key, V value) {
        return Optional.empty();
    }

}

