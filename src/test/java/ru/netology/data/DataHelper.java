package ru.netology.data;

import lombok.Value;

public class DataHelper {

    private DataHelper() {
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static VerificationCode getVerificationCode() {
        return new VerificationCode("12345");
    }

    public static String getCardNumber(int index) {
        String[] cards = {
                "5559 0000 0000 0001",
                "5559 0000 0000 0002"
        };
        return cards[index];
    }

    public static int getValidAmount(int balance) {
        return Math.max(1, Math.abs(balance) / 2);
    }

    public static int getInvalidAmount(int balance) {
        return Math.abs(balance) + 1;
    }

    @Value
    public static class AuthInfo {
        String login;
        String password;
    }

    @Value
    public static class VerificationCode {
        String code;
    }
}