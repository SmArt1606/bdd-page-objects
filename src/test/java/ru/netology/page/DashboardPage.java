package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {

    private final ElementsCollection cards =
            $$(".list__item");

    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    public DashboardPage() {
        cards.first()
                .$("[data-test-id='action-deposit']")
                .shouldBe(
                        Condition.visible,
                        Duration.ofSeconds(15)
                );
    }

    public int getCardBalance(int index) {
        String text = cards.get(index).text();
        return extractBalance(text);
    }

    public TransferPage selectCardToDeposit(int index) {
        cards.get(index)
                .$("[data-test-id='action-deposit']")
                .click();

        return new TransferPage();
    }

    private int extractBalance(String text) {
        int start = text.indexOf(balanceStart);
        int finish = text.indexOf(balanceFinish);

        String value = text.substring(
                start + balanceStart.length(),
                finish
        );

        return Integer.parseInt(value);
    }
}