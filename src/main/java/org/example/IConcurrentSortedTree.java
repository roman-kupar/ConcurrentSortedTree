package org.example;

import java.util.Optional;

public interface IConcurrentSortedTree {

    Optional<byte[]> get(byte[] key);

    Optional<byte[]> put(byte[] key, byte[] value);

}
