package org.example.cst;

import java.util.Optional;

public interface IConcurrentSortedTree {

    /**
     * Retrieves the value for the given key, if present.
     *
     * @param key the key to search for.
     * @return an Optional with the value, or empty if not found.
     */
    Optional<byte[]> get(byte[] key);

    /**
     * Adds or updates the value for the given key.
     *
     * @param key the key to associate with the value.
     * @param value the value to store.
     * @return an Optional with the previous value, or empty if none existed.
     */
    Optional<byte[]> put(byte[] key, byte[] value);

}
