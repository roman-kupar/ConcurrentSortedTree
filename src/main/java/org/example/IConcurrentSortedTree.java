package org.example;

import java.util.Optional;

public interface IConcurrentSortedTree<K,V>  {

    Optional<V> get(K key);

    Optional<V> put(K key, V value);

}
