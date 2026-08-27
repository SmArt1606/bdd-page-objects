package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;

public class TransferPage {

    private final SelenideElement amount =
            $("[data-test-id='amount'] input");

    private final SelenideElement from =
            $("[data-test-id='from'] input");

    private final SelenideElement transferButton =
            $("[data-test-id='action-transfer']");

    private final SelenideElement error =
            $("[data-test-id='error-notification'] .notification__content");

    public TransferPage() {
        amount.shouldBe(
                Condition.visible,
                Duration.ofSeconds(15)
        );
    }

    public DashboardPage transfer(
            int transferAmount,
            String sourceCard) {

        fillTransferForm(transferAmount, sourceCard);

        return new DashboardPage();
    }

    public void transferExpectingError(
            int transferAmount,
            String sourceCard) {

        fillTransferForm(transferAmount, sourceCard);
    }

    public void verifyErrorMessage(String expectedMessage) {
        error
                .shouldBe(
                        Condition.visible,
                        Duration.ofSeconds(15)
                )
                .shouldHave(
                        Condition.exactText(expectedMessage)
                );
    }

    private void fillTransferForm(
            int transferAmount,
            String sourceCard) {

        amount.setValue(String.valueOf(transferAmount));
        from.setValue(sourceCard);
        transferButton.click();
    }
}