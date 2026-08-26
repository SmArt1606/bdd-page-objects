package ru.netology.data;

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
        return Math.max(1, Math.min(1000, balance / 2));
    }

    public static int getInvalidAmount(int balance) {
        return Math.abs(balance) + 1000;
    }

    public static class AuthInfo {
        private final String login;
        private final String password;

        public AuthInfo(String login, String password) {
            this.login = login;
            this.password = password;
        }

        public String getLogin() {
            return login;
        }

        public String getPassword() {
            return password;
        }
    }

    public static class VerificationCode {
        private final String code;

        public VerificationCode(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }
}