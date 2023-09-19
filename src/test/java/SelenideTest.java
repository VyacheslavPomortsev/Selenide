import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;

public class SelenideTest {
    private String generateDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void shouldTest() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Хабаровск");
        String planningDate = generateDate(5, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Петров Петр");
        $("[data-test-id='phone'] input").setValue("+79990009900");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $(".notification__title")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldBe(Condition.text("Успешно!"));
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate))
                .shouldBe(Condition.visible);

    }
    @Test
    void shouldTestOnCalendar() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Ха");
        $$(".menu-item__control").findBy(Condition.text("Хабаровск")).click();
        String planningDate = generateDate(5, "dd.MM.yyyy");
        $("[data-test-id='date'] input").click();
        if(!generateDate(3, "MM").equals(generateDate(7, "MM"))){
            $("[data-step='1']").click();
        }
        $$("[data-day]").findBy(Condition.text(generateDate(5, "dd"))).click();
        $("[data-test-id='name'] input").setValue("Петров Петр");
        $("[data-test-id='phone'] input").setValue("+79990009900");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $(".notification__title")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldBe(Condition.text("Успешно!"));
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate))
                .shouldBe(Condition.visible);

    }
}
