package com.racetalk.util;

public class PasswordValidator {
    public static boolean isValid(String password) {
        if (password == null || password.length() < 6 || password.length() > 30) {
            return false;
        }
        boolean hasLetter = false;
        boolean hasDigit = false;

        for (char c : password.toCharArray()) {
            if (Character.isLetter(c)) hasLetter = true;
            if (Character.isDigit(c)) hasDigit = true;
            if (hasLetter && hasDigit) {
                return true;
            }
        }
        return false;
    }
}

