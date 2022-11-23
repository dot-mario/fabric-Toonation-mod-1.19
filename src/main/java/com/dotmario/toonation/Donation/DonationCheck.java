package com.dotmario.toonation.Donation;

import com.dotmario.toonation.config.MidnightConfigExample;
import com.dotmario.toonation.networking.ModMessages;
import io.github.bonigarcia.wdm.WebDriverManager;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static com.dotmario.toonation.ToonationMod.LOGGER;

public class DonationCheck extends Thread {
    private boolean isAllowed;

    public DonationCheck(boolean isCurrentAllow) {
        isAllowed = isCurrentAllow;
        WebDriverManager.chromedriver().setup();
    }
    @Override
    public void run() {
        if (MidnightConfigExample.toonationURL!=null) {
            ChromeOptions options = new ChromeOptions();
            options.setHeadless(true);
            WebDriver driver = new ChromeDriver(options);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Integer.MAX_VALUE));
            driver.get(MidnightConfigExample.toonationURL);
            while (isAllowed) {
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class='alert-layout animated fadeIn v-enter-to']")));
                WebElement element = driver.findElement(By.xpath("/html/body/div/div[2]/div/div/div/div[2]/div[1]/div/span[4]"));
                int donationAmount = Integer.parseInt(element.getText().replaceAll(",", ""));
                LOGGER.info(String.valueOf(donationAmount));
                ClientPlayNetworking.send(ModMessages.AMOUNT_ID, (PacketByteBuf) PacketByteBufs.create().writeByte(donationAmount));
                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div[class='alert-layout animated fadeIn v-enter-to']")));
            }
        }
    }
    public void isWorked(boolean isCurrentAllow) {
        isAllowed = isCurrentAllow;
    }
}
