package ru.netology;

import com.codeborne.selenide.SelenideElement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {
    SelenideElement form = $(".form");

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
        form = $("[action]");

    }

    @Test
    void shouldSubmitRequest() {
        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        form.$("[data-test-id=city] input").setValue("Санкт-Петербург");
        form.$(".input__icon").click();
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").sendKeys(date);
        form.$("[data-test-id=name] input").setValue("Иван Петров");
        form.$("[data-test-id=phone] input").setValue("+79200000000");
        form.$("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=notification]").waitUntil(visible, 15000).shouldHave(exactText("Успешно! Встреча успешно забронирована на "
                + date));
    }

    //Задача 2 (НЕОБЯЗАТЕЛЬНАЯ)

    @Test
    void shouldSubmitRequestList() {
        String reservationDate = LocalDate.now().plusDays(7).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        form.$("[data-test-id=city] input").setValue("Са");
        $$(".menu-item").find(exactText("Санкт-Петербург")).click();
        form.$(".input__icon").click();
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").sendKeys(reservationDate);
        form.$("[data-test-id=name] input").setValue("Иван Петров");
        form.$("[data-test-id=phone] input").setValue("+79200000000");
        form.$("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=notification]").waitUntil(visible, 15000).shouldHave(exactText("Успешно! Встреча успешно забронирована на "
                + reservationDate));
    }

    @Test
    void shouldSubmitRequestCalendar() {
        form.$("[data-test-id=city] input").setValue("Са");
        $$(".menu-item").find(exactText("Санкт-Петербург")).click();
        form.$(".input__icon").click();
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        LocalDate date = LocalDate.now();
        LocalDate meetDate = date.plusDays(7);
        int currentMonth = date.getMonthValue();
        int meetMonth = meetDate.getMonthValue();
        int meetDay = meetDate.getDayOfMonth();
        if (meetMonth != currentMonth) {
            $("[class='popup__container'] [data-step='1']").click();
        }
        $(byText(String.valueOf(meetDay))).click();
        form.$("[data-test-id=name] input").setValue("Иван Петров");
        form.$("[data-test-id=phone] input").setValue("+79200000000");
        form.$("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $(withText("Успешно!")).waitUntil(visible, 15000);

    }
}

