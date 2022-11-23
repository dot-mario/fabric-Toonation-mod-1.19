package com.dotmario.toonation.Donation;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DonationCheck extends Thread {
    private boolean isAllowed;

    public DonationCheck(boolean isCurrentAllow) {
        isAllowed = isCurrentAllow;
        WebDriverManager.chromedriver().setup();
    }
    @Override
    public void run() {
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Integer.MAX_VALUE));

        driver.get("https://toon.at/widget/alertbox/f74be28c7a9ee311f222abf8d9f553a4");
        while (isAllowed) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class='alert-layout animated fadeIn v-enter-to']")));
            WebElement element = driver.findElement(By.xpath("/html/body/div/div[2]/div/div/div/div[2]/div[1]/div/span[4]"));
            int donationAmount = Integer.parseInt(element.getText().replaceAll(",", ""));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div[class='alert-layout animated fadeIn v-enter-to']")));
        }
    }
    public void isWorked(boolean isCurrentAllow) {
        isAllowed = isCurrentAllow;
    }
}
