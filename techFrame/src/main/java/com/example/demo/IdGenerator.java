package com.example.demo;

public class IdGenerator {
    public static String generateId(String prefix, int idLength, int counter) {
        if (prefix == null || idLength <= 0 || counter < 0) {
            return null;
        }

        try {
            String idNumber = String.format("%0" + idLength + "d", counter);
            return prefix + idNumber;
        } catch (Exception e) {
            return null;
        }
    }
}