package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataHelper;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.MainPage;

import static com.codeborne.selenide.Selenide.open;


public class PaymentDebitPage {

    @BeforeAll
    static void allureSetUp() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    public void setUp() {
        open("http://localhost:8080/");
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    @DisplayName("№1 успешная покупка по карте")
    public void happyCase() {
        MainPage main = new MainPage();
        main.payWithDebit();
        page.PaymentDebitPage form = new page.PaymentDebitPage();
        var month = DataHelper.validMonthForCard();
        var year = DataHelper.validYearForCard();
        var owner = "Elena Ivanova";
        var cvv = DataHelper.validCvvCode();
        form.fillForm(1, month, year, owner, cvv);
        form.waitSuccessNotification();
    }

    @Test
    @DisplayName("№3 Оплата покупки по карте со статусом DECLINED ")
    public void bankShouldCancelCard() {
        MainPage main = new MainPage();
        main.payWithDebit();
        page.PaymentDebitPage form = new page.PaymentDebitPage();
        var month = DataHelper.validMonthForCard();
        var year = DataHelper.validYearForCard();
        var owner = "Elena Ivanova";
        var cvv = DataHelper.validCvvCode();
        form.fillForm(2, month, year, owner, cvv);
        form.waitFailedNotification();
    }

    @Test
    @DisplayName("№5 Ввод невалидного номера карты в форму покупки")
    public void wrongCardNumber() {
        MainPage main = new MainPage();
        main.payWithDebit();
        page.PaymentDebitPage form = new page.PaymentDebitPage();
        var month = DataHelper.validMonthForCard();
        var year = DataHelper.validYearForCard();
        var owner = "Elena Ivanova";
        var cvv = DataHelper.validCvvCode();
        form.fillForm(3, month, year, owner, cvv);
        form.waitWrongFormatMessage();
    }

    @Test
    @DisplayName("№6 Нулевой месяц действия карты")
    public void zeroMonthOfCard() {
        MainPage main = new MainPage();
        main.payWithDebit();
        page.PaymentDebitPage form = new page.PaymentDebitPage();
        var month = "00";
        var year = DataHelper.validYearForCard();
        var owner = "Elena Ivanova";
        var cvv = DataHelper.validCvvCode();
        form.fillForm(1, month, year, owner, cvv);
        form.waitWrongCardExpirationMessage();
    }

    @Test
    @DisplayName("№7 Неверный месяц действия карты")
    public void wrongMonthOfCard() {
        MainPage main = new MainPage();
        main.payWithDebit();
        page.PaymentDebitPage form = new page.PaymentDebitPage();
        var month = "13";
        var year = DataHelper.validYearForCard();
        var owner = "Elena Ivanova";
        var cvv = DataHelper.validCvvCode();
        form.fillForm(1, month, year, owner, cvv);
        form.waitWrongCardExpirationMessage();
    }

    @Test
    @DisplayName("№8 Просроченная дата действия карты")
    public void wrongDateOfCard() {
        MainPage main = new MainPage();
        main.payWithDebit();
        page.PaymentDebitPage form = new page.PaymentDebitPage();
        var month = DataHelper.validMonthForCard();
        var year = "22";
        var owner = "Elena Ivanova";
        var cvv = DataHelper.validCvvCode();
        form.fillForm(1, month, year, owner, cvv);
        form.waitCardExpiredMessage();
    }

    @Test
    @DisplayName("№9 Неверный год действия карты")
    public void wrongYearOfCard() {
        MainPage main = new MainPage();
        main.payWithDebit();
        page.PaymentDebitPage form = new page.PaymentDebitPage();
        var month = DataHelper.validMonthForCard();
        var year = "99";
        var owner = "Elena Ivanova";
        var cvv = DataHelper.validCvvCode();
        form.fillForm(1, month, year, owner, cvv);
        form.waitWrongCardExpirationMessage();
    }

    @Test
    @DisplayName("№10 Цифры в имени держателя карты")
    public void wrongOwnerOfCard() {
        MainPage main = new MainPage();
        main.payWithDebit();
        page.PaymentDebitPage form = new page.PaymentDebitPage();
        var month = DataHelper.validMonthForCard();
        var year = DataHelper.validYearForCard();
        var owner = "099244";
        var cvv = DataHelper.validCvvCode();
        form.fillForm(1, month, year, owner, cvv);
        form.waitInvalidCharactersMessage();
    }

    @Test
    @DisplayName("№11 Ввод спец.символов вместо имени держателя карты")
    public void symbolsInCardOwnerName() {
        MainPage main = new MainPage();
        main.payWithDebit();
        page.PaymentDebitPage form = new page.PaymentDebitPage();
        var month = DataHelper.validMonthForCard();
        var year = DataHelper.validYearForCard();
        var owner = "&$#@@!@";
        var cvv = DataHelper.validCvvCode();
        form.fillForm(1, month, year, owner, cvv);
        form.waitInvalidCharactersMessage();
    }

    @Test
    @DisplayName("№12 Пробел перед именем держателя карты")
    public void spaceBeforeOwnerOfCard() {
        MainPage main = new MainPage();
        main.payWithDebit();
        page.PaymentDebitPage form = new page.PaymentDebitPage();
        var month = DataHelper.validMonthForCard();
        var year = DataHelper.validYearForCard();
        var owner = "  Elena Ivanova";
        var cvv = DataHelper.validCvvCode();
        form.fillForm(1, month, year, owner, cvv);
        form.waitInvalidCharactersMessage();
    }

    @Test
    @DisplayName("№13 Неверный формат CVV кода карты ( 2 цифры) ")
    public void wrongFormatOfCvvCode() {
        MainPage main = new MainPage();
        main.payWithDebit();
        page.PaymentDebitPage form = new page.PaymentDebitPage();
        var month = DataHelper.validMonthForCard();
        var year = DataHelper.validYearForCard();
        var owner = "Evgenii Shipov";
        var cvv = "52";
        form.fillForm(1, month, year, owner, cvv);
        form.waitWrongFormatMessage();
    }

    @Test
    @DisplayName("№14 Неверный формат CVV кода карты ( 1 цифра) ")
    public void wrongFormatOfCvvCode2() {
        MainPage main = new MainPage();
        main.payWithDebit();
        page.PaymentDebitPage form = new page.PaymentDebitPage();
        var month = DataHelper.validMonthForCard();
        var year = DataHelper.validYearForCard();
        var owner = "Evgenii Shipov";
        var cvv = "2";
        form.fillForm(1, month, year, owner, cvv);
        form.waitWrongFormatMessage();
    }

    @Test
    @DisplayName("№15 Отправка пустой формы")
    public void emptyForm() {
        MainPage main = new MainPage();
        main.payWithDebit();
        page.PaymentDebitPage form = new page.PaymentDebitPage();
        form.cleanForm();
        form.clickSubmitButton();
        form.waitMandatoryFieldMessage();
    }
}
