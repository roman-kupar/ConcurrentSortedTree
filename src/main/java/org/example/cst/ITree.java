package org.example.cst;

import java.util.Optional;

public interface ITree<K,V> {

    /**
     * Retrieves the value for the given key, if present.
     *
     * @param key the key to search for.
     * @return an Optional with the value, or empty if not found.
     */
    Optional<V> get(K key);

    /**
     * Adds or updates the value for the given key.
     *
     * @param key the key to associate with the value.
     * @param value the value to store.
     * @return an Optional with the previous value, or empty if none existed.
     */
    Optional<V> put(K key, V value);

}
