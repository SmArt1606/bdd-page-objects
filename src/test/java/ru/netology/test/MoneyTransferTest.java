package ru.netology.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTransferTest {

    private static final int FIRST_CARD = 0;
    private static final int SECOND_CARD = 1;

    private DashboardPage dashboardPage;

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");

        var loginPage = new LoginPage();

        var verificationPage =
                loginPage.validLogin(
                        DataHelper.getAuthInfo()
                );

        dashboardPage =
                verificationPage.validVerify(
                        DataHelper.getVerificationCode()
                );
    }

    @Test
    void shouldSuccessfullyTransferMoneyFromFirstCardToSecondCard() {

        int firstCardBalance =
                dashboardPage.getCardBalance(FIRST_CARD);

        int secondCardBalance =
                dashboardPage.getCardBalance(SECOND_CARD);

        int amount =
                DataHelper.getValidAmount(firstCardBalance);

        var transferPage =
                dashboardPage.selectCardToDeposit(SECOND_CARD);

        dashboardPage = transferPage.transfer(
                amount,
                DataHelper.getCardNumber(FIRST_CARD)
        );

        assertAll(
                () -> assertEquals(
                        firstCardBalance - amount,
                        dashboardPage.getCardBalance(FIRST_CARD)
                ),
                () -> assertEquals(
                        secondCardBalance + amount,
                        dashboardPage.getCardBalance(SECOND_CARD)
                )
        );
    }

    @Test
    void shouldNotTransferMoreThanSecondCardBalanceToFirstCard() {

        int secondCardBalance =
                dashboardPage.getCardBalance(SECOND_CARD);

        int invalidAmount =
                DataHelper.getInvalidAmount(secondCardBalance);

        var transferPage =
                dashboardPage.selectCardToDeposit(FIRST_CARD);

        transferPage.transferExpectingError(
                invalidAmount,
                DataHelper.getCardNumber(SECOND_CARD)
        );

        transferPage.verifyErrorMessage(
                "Недостаточно средств на карте"
        );
    }
}