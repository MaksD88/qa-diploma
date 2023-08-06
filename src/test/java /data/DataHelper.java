package data;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.Year;
import java.util.*;

public class DataHelper {

    public static String getCard(int cardNumber) {
        HashMap<Integer, String> list = new HashMap<>();
        list.put(1, "4444 4444 4444 4441");
        list.put(2, "4444 4444 4444 4442");
        list.put(3, "4444 4444 4444 444");
        return list.get(cardNumber);
    }


    public static String validMonthForCard() {
        Faker faker = new Faker();
        LocalDate date = LocalDate.now();
        int randomMonth = faker.random().nextInt(1,3);
        String month = date.plusMonths(randomMonth).format(DateTimeFormatter.ofPattern("MM"));
        return month;
    }


    public static String validYearForCard() {
        Faker faker = new Faker();
        int randomInteger = faker.random().nextInt(1, 4);
        int year = Year.now().getValue() % 100 + randomInteger;
        return String.valueOf(year);
    }


    public static String validCvvCode() {
        Faker faker = new Faker();
        return String.valueOf(faker.number().numberBetween(100, 999));
    }

}