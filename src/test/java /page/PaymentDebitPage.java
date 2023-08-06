package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import data.DataHelper;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class PaymentDebitPage {


    private SelenideElement cardNumber = $("input[placeholder = '0000 0000 0000 0000']");
    private SelenideElement month = $("[placeholder = '08']");
    private SelenideElement year = $("[placeholder = '22']");
    private SelenideElement cardOwner = $$(".input__inner").find(Condition.text("Владелец")).$(".input__control");
    private SelenideElement cvv = $("[placeholder = '999']");
    private SelenideElement submitButton = $x("//*[(text()= 'Продолжить')]");


    private SelenideElement successNotification = $(byText("Операция одобрена Банком."));
    private SelenideElement failedNotification = $(byText("Ошибка! Банк отказал в проведении операции."));
    private SelenideElement mandatoryFieldMessage = $(byText("Поле обязательно для заполнения"));
    private SelenideElement wrongFormatMessage = $(byText("Неверный формат"));
    private SelenideElement invalidCharactersMessage = $(byText("Поле содержит недопустимые символы"));
    private SelenideElement wrongCardExpirationMessage = $(byText("Неверно указан срок действия карты"));
    private SelenideElement cardExpiredMessage = $(byText("Истёк срок действия карты"));


    public void fillForm(int card, String monthNumber, String yearNumber, String owner, String cvvCode) {
        cardNumber.setValue(DataHelper.getCard(card));
        month.setValue(monthNumber);
        year.setValue(yearNumber);
        cardOwner.setValue(owner);
        cvv.setValue(cvvCode);
        submitButton.click();
    }

    public void cleanForm() {
        cardNumber.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        month.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        year.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        cardOwner.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        cvv.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);

    }

    public void clickSubmitButton() {
        submitButton.click();
    }

    public void waitSuccessNotification() {
        successNotification.shouldBe(visible, Duration.ofSeconds(12));
    }

    public void waitFailedNotification() {
        failedNotification.shouldBe(visible, Duration.ofSeconds(12));
    }

    public void waitWrongFormatMessage() {
        wrongFormatMessage.shouldBe(visible, Duration.ofSeconds(12));
    }

    public void waitInvalidCharactersMessage() {
        invalidCharactersMessage.shouldBe(visible, Duration.ofSeconds(12));
    }

    public void waitWrongCardExpirationMessage() {
        wrongCardExpirationMessage.shouldBe(visible, Duration.ofSeconds(12));
    }

    public void waitCardExpiredMessage() {
        cardExpiredMessage.shouldBe(visible, Duration.ofSeconds(12));
    }

    public void waitMandatoryFieldMessage() {
        mandatoryFieldMessage.shouldBe(visible, Duration.ofSeconds(12));
    }
}