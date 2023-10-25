package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import data.DataHelper;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class PaymentCreditPage {
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

    // Set a 12-second timeout for notifications
    private final Duration notificationTimeout = Duration.ofSeconds(12);

    public void fillCreditForm(int card, String monthNumber, String yearNumber, String owner, String cvvCode) {
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

    public void waitSuccessNotification() {
        successNotification.shouldBe(visible, notificationTimeout);
    }

    public void waitFailedNotification() {
        failedNotification.shouldBe(visible, notificationTimeout);
    }

    public void waitWrongFormatMessage() {
        wrongFormatMessage.shouldBe(visible);
    }

    public void waitInvalidCharactersMessage() {
        invalidCharactersMessage.shouldBe(visible);
    }

    public void waitWrongCardExpirationMessage() {
        wrongCardExpirationMessage.shouldBe(visible);
    }

    public void waitCardExpiredMessage() {
        cardExpiredMessage.shouldBe(visible);
    }

    public void clickSubmitButton() {
        submitButton.click();
    }

    public void waitMandatoryFieldMessage() {
        mandatoryFieldMessage.shouldBe(visible);
    }
}
