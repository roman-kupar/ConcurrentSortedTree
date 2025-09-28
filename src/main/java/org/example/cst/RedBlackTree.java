package org.example.cst;

import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;

final class RedBlackTree<K,V> implements ITree<K,V> {

    /* Constants */
    private static final boolean RED = true, BLACK = false;

    /* Node in a tree */
    private static final class Node<K,V> {
        final K key;
        V value;

        Node<K,V> left, right;

        /* True for red, false for black */
        boolean isRed;

        public Node(K key, V value, boolean isRed) {
            this.key = key;
            this.value = value;
            this.isRed = isRed;
        }
    }

    private final Comparator<? super K> cmp;

    private Node<K,V> root;


    public RedBlackTree(Comparator<? super K> cmp) {
        this.cmp = Objects.requireNonNull(cmp, "comparator must not be null");
    }

    @Override
    public Optional<V> get(K key) {
        Objects.requireNonNull(key, "key must not be null");

        Node<K,V> x = root;

        while (x != null) {
            K k = x.key;
            int c = cmp.compare(key, k);
            if (c == 0) {

                V v = x.value;

                return Optional.of(v);
            }
            x = (c < 0) ? x.left : x.right;

        }
        return Optional.empty();

    }

    @Override
    public Optional<V> put(K key, V value) {

        Objects.requireNonNull(key, "key must not be null");
        Objects.requireNonNull(value, "value must not be null");
        Holder<V> prev = new Holder<>();
        root = insert(root, key, value, prev);
        root.isRed = BLACK; /* root must always be black */
        return Optional.ofNullable(prev.val);
    }

    private static final class Holder<V> { V val; }

    private Node<K,V> insert(Node<K,V> h, K key, V value, Holder<V> old) {
        if (h == null) {
            return new Node<>(key, value, RED);
        }

        int c = cmp.compare(key, h.key);

        if (c < 0) {
            h.left = insert(h.left, key, value, old);
        } else if (c > 0) {
            h.right = insert(h.right, key, value, old);
        } else {

            old.val = h.value;
            h.value = value;
            return h;
        }

        if (isRed(h.right) && !isRed(h.left))      h = rotateLeft(h);
        if (isRed(h.left)  && isRed(h.left.left))  h = rotateRight(h);
        if (isRed(h.left)  && isRed(h.right))      flipColors(h);

        return h;
    }

    private static <K,V> boolean isRed(Node<K,V> x) {
        return x != null && x.isRed == RED;
    }

    private static <K,V> Node<K,V> rotateLeft(Node<K,V> h) {
        Node<K,V> x = h.right;
        h.right = x.left;
        x.left = h;
        x.isRed = h.isRed;
        h.isRed = RED;
        return x;
    }

    private static <K,V> Node<K,V> rotateRight(Node<K,V> h) {
        Node<K,V> x = h.left;
        h.left = x.right;
        x.right = h;
        x.isRed = h.isRed;
        h.isRed = RED;
        return x;
    }

    private static <K,V> void flipColors(Node<K,V> h) {
        h.isRed = !h.isRed;
        if (h.left  != null) h.left.isRed  = !h.left.isRed;
        if (h.right != null) h.right.isRed = !h.right.isRed;
    }

}
