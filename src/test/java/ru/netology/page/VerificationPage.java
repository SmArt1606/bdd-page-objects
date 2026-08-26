package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {

    private final SelenideElement code =
            $("[data-test-id='code'] input");

    private final SelenideElement verifyButton =
            $("[data-test-id='action-verify']");

    public VerificationPage() {
        code.shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    public DashboardPage validVerify(
            DataHelper.VerificationCode verificationCode) {

        code.setValue(verificationCode.getCode());
        verifyButton.click();

        return new DashboardPage();
    }
}