package org.example.util;

public class Utf8 {
    private Utf8() {}

    public static byte[] bytesOf(String s) {
        return s.getBytes(java.nio.charset.StandardCharsets.UTF_8);

    }
    public static String stringOf(byte[] b) {
        return new String(b, java.nio.charset.StandardCharsets.UTF_8);
    }
}
