package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataHelper;
import data.DbUtils;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.MainPage;
import page.PaymentCreditPage;
import page.PaymentDebitPage;

import static com.codeborne.selenide.Selenide.open;

public class DbTests {

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

    @AfterEach
    public void cleanDb() {
        DbUtils.cleanDb();
    }

    @Test
    @DisplayName("27. Отправка запроса в СУБД при покупке тура по карте со статусом 'APPROVED'")
    public void checkApprovedStatusInDb() {
        MainPage mainPage = new MainPage();
        mainPage.payWithDebit();
        page.PaymentDebitPage page = new PaymentDebitPage();
        var month = DataHelper.validMonthForCard();
        var year = DataHelper.validYearForCard();
        var owner = "Daria Nikova";
        var cvv = DataHelper.validCvvCode();
        page.fillForm(1, month, year, owner, cvv);
        mainPage.waitNotificationForDb();
        var actual = DbUtils.getDebitStatus();
        var expected = "APPROVED";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("28. Отправка запроса в СУБД при покупке тура по карте со статусом 'APPROVED' в кредит ")
    public void checkApprovedStatusInDbInCaseCreditPayment() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCredit();
        page.PaymentCreditPage page = new PaymentCreditPage();
        var month = DataHelper.validMonthForCard();
        var year = DataHelper.validYearForCard();
        var owner = "Daria Nikulina";
        var cvv = DataHelper.validCvvCode();
        page.fillCreditForm(1, month, year, owner, cvv);
        mainPage.waitNotificationForDb();
        var actual = DbUtils.getCreditStatus();
        var expected = "APPROVED";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("29. Отправка запроса в СУБД при покупке тура по карте со статусом 'DECLINED'")
    public void checkDeclinedStatusInDb() {
        MainPage mainPage = new MainPage();
        mainPage.payWithDebit();
        page.PaymentDebitPage page = new PaymentDebitPage();
        var month = DataHelper.validMonthForCard();
        var year = DataHelper.validYearForCard();
        var owner = "Oleg Tinkoff";
        var cvv = DataHelper.validCvvCode();
        page.fillForm(2, month, year, owner, cvv);
        mainPage.waitNotificationForDb();
        var actual = DbUtils.getDebitStatus();
        var expected = "DECLINED";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("30. Отправка запроса в СУБД при покупке тура по карте со статусом 'DECLINED' в кредит ")
    public void checkDeclinedStatusInDbInCaseCreditPayment() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCredit();
        page.PaymentCreditPage page = new PaymentCreditPage();
        var month = DataHelper.validMonthForCard();
        var year = DataHelper.validYearForCard();
        var owner = "Oleg Tinkoff";
        var cvv = DataHelper.validCvvCode();
        page.fillCreditForm(2, month, year, owner, cvv);
        mainPage.waitNotificationForDb();
        var actual = DbUtils.getCreditStatus();
        var expected = "DECLINED";
        Assertions.assertEquals(expected, actual);
    }

}
