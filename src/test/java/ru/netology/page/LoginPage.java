package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    private final SelenideElement login =
            $("[data-test-id='login'] input");

    private final SelenideElement password =
            $("[data-test-id='password'] input");

    private final SelenideElement loginButton =
            $("[data-test-id='action-login']");

    public LoginPage() {
        login.shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    public VerificationPage validLogin(DataHelper.AuthInfo authInfo) {
        login.setValue(authInfo.getLogin());
        password.setValue(authInfo.getPassword());
        loginButton.click();

        return new VerificationPage();
    }
}