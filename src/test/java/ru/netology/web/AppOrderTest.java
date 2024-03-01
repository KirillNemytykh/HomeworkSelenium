package ru.netology.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AppOrderTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        options.addArguments("--start-maximized");
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("credentials_enable_service", false);
        prefs.put("password_manager_enabled", false);
        options.setExperimentalOption("prefs", prefs);
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    //@Test
    // void shouldTestV1() {
    //  List<WebElement> elements = driver.findElements(By.className("input__control"));
    // elements.get(0).sendKeys("Василий");
    // elements.get(1).sendKeys("+79270000000");
    // driver.findElement(By.className("checkbox__box")).click();
    // driver.findElement(By.className("button")).click();
    //String text = driver.findElement(By.className("alert-success")).getText();
    //assertEquals("Ваша заявка успешно отправлена!", text.trim());
    // }

    // @Test
    //void shouldTestV2() {
    //  WebElement form = driver.findElement(By.cssSelector("[data-test-id=callback-form]"));
    //  form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Василий");
    // form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79270000000");
    //form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
    // form.findElement(By.cssSelector("[data-test-id=submit]")).click();
    // String text = driver.findElement(By.className("alert-success")).getText();
    // assertEquals("Ваша заявка успешно отправлена!", text.trim());
    //}
    @Test
    public void shouldBeSuccessfulForm() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector("[data-test-id= 'phone'] input")).sendKeys("+79999999");
        driver.findElement(By.cssSelector("[data-test-id= 'agreement'] ")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        var actualTextElement = driver.findElement(By.cssSelector("[data-test-id=order-success]"));
        var actualText = actualTextElement.getText().trim();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", actualText);
        assertTrue(actualTextElement.isDisplayed());
    }


    @Test
    public void wrongNameTest() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Ivan");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79999999999");
        driver.findElement(By.cssSelector("[data-test-id='agreement'] ")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.",
                driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim());
        assertTrue(driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).isDisplayed());
    }

    @Test
    public void wrongPhoneTest() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("79999999999");
        driver.findElement(By.cssSelector("[data-test-id='agreement'] ")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.",
                driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim());
        //assertTrue(driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).isDisplayed());
    }

    @Test
    public void emptyNameTest() {
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79999999999");
        driver.findElement(By.cssSelector("[data-test-id='agreement'] ")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        assertEquals("Поле обязательно для заполнения",
                driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim());
        assertTrue(driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).isDisplayed());
    }

    @Test
    public void emptyPhoneTest() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector("[data-test-id='agreement'] ")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        assertEquals("Поле обязательно для заполнения",
                driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim());
        assertTrue(driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).isDisplayed());
    }

    public void checkboxMissedTest() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector("[data-test-id= 'phone'] input")).sendKeys("+79999999999");
        driver.findElement(By.cssSelector("button.button")).click();
        assertTrue(driver.findElement(By.cssSelector("[data-test-id='agreement'].input_invalid")).isDisplayed());
    }

}

