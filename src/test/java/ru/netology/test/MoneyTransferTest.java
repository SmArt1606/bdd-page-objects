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

    private DashboardPage dashboardPage;

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");

        var loginPage = new LoginPage();

        var verificationPage =
                loginPage.validLogin(DataHelper.getAuthInfo());

        dashboardPage =
                verificationPage.validVerify(
                        DataHelper.getVerificationCode()
                );
    }

    @Test
    void shouldSuccessfullyTransferMoneyBetweenOwnCards() {

        int firstBalance =
                dashboardPage.getCardBalance(0);

        int secondBalance =
                dashboardPage.getCardBalance(1);

        int sourceIndex;
        int targetIndex;

        if (firstBalance >= secondBalance) {
            sourceIndex = 0;
            targetIndex = 1;
        } else {
            sourceIndex = 1;
            targetIndex = 0;
        }

        int sourceBalance =
                dashboardPage.getCardBalance(sourceIndex);

        int targetBalance =
                dashboardPage.getCardBalance(targetIndex);

        int amount =
                DataHelper.getValidAmount(sourceBalance);

        var transferPage =
                dashboardPage.selectCardToDeposit(targetIndex);

        dashboardPage = transferPage.transfer(
                amount,
                DataHelper.getCardNumber(sourceIndex)
        );

        assertAll(
                () -> assertEquals(
                        sourceBalance - amount,
                        dashboardPage.getCardBalance(sourceIndex)
                ),
                () -> assertEquals(
                        targetBalance + amount,
                        dashboardPage.getCardBalance(targetIndex)
                )
        );
    }

    @Test
    void shouldNotTransferMoreThanCardBalance() {

        int firstBalance =
                dashboardPage.getCardBalance(0);

        int secondBalance =
                dashboardPage.getCardBalance(1);

        int sourceIndex;
        int targetIndex;

        if (firstBalance >= secondBalance) {
            sourceIndex = 0;
            targetIndex = 1;
        } else {
            sourceIndex = 1;
            targetIndex = 0;
        }

        int sourceBalance =
                dashboardPage.getCardBalance(sourceIndex);

        int invalidAmount =
                DataHelper.getInvalidAmount(sourceBalance);

        var transferPage =
                dashboardPage.selectCardToDeposit(targetIndex);

        String errorMessage =
                transferPage.transferExpectingError(
                        invalidAmount,
                        DataHelper.getCardNumber(sourceIndex)
                );

        assertEquals(
                "Недостаточно средств на карте",
                errorMessage
        );
    }
}