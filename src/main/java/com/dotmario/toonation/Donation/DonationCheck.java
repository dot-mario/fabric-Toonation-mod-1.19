package com.dotmario.toonation.Donation;

import com.dotmario.toonation.config.MidnightConfigExample;
import com.dotmario.toonation.networking.ModMessages;
import io.github.bonigarcia.wdm.WebDriverManager;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static com.dotmario.toonation.ToonationMod.LOGGER;

public class DonationCheck extends Thread {
    private static boolean isAllowed;
    private static ChromeDriver driver;

    public DonationCheck() {
        isAllowed = true;
        WebDriverManager.chromedriver().setup();
    }
    @Override
    public void run() {
        if (MidnightConfigExample.toonationURL!=null) {
            ChromeOptions options = new ChromeOptions();
            options.setHeadless(true);
            options.addArguments("−−mute−audio");
            driver = new ChromeDriver(options);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Integer.MAX_VALUE));
            driver.get(MidnightConfigExample.toonationURL);
            driver.findElement(By.xpath("/html/body")).click();
            while (isAllowed) {
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class='alert-layout animated fadeIn v-enter-to']")));
                WebElement element = driver.findElement(By.xpath("/html/body/div/div[2]/div/div/div/div[2]/div[1]/div/span[4]"));
                int donationAmount = Integer.parseInt(element.getText().replaceAll(",", ""));
                LOGGER.info(String.valueOf(donationAmount));

                if(MinecraftClient.getInstance().world != null && MinecraftClient.getInstance().world.isClient) {
                    actionCheck(donationAmount);
                }

                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div[class='alert-layout animated fadeIn v-enter-to']")));
            }
        }
    }
    public static void stopSelenium() {
        LOGGER.info("quit selenium");
        isAllowed = false;
        driver.quit();
    }
    private void actionCheck(int amount) {
        if (amount==MidnightConfigExample.addInventory) {
            LOGGER.info("addInventory");
            ClientPlayNetworking.send(ModMessages.AMOUNT_ID, PacketByteBufs.create().writeString("addInventory"));
        } else if (amount==MidnightConfigExample.removeInventory) {
            LOGGER.info("removeInventory");
            ClientPlayNetworking.send(ModMessages.AMOUNT_ID, PacketByteBufs.create().writeString("removeInventory"));
        } else if (amount==MidnightConfigExample.removeItem) {
            LOGGER.info("removeItem");
            ClientPlayNetworking.send(ModMessages.AMOUNT_ID, PacketByteBufs.create().writeString("removeItem"));
        } else if (amount==MidnightConfigExample.addHP) {
            LOGGER.info("addHP");
            ClientPlayNetworking.send(ModMessages.AMOUNT_ID, PacketByteBufs.create().writeString("addHP"));
        } else if (amount==MidnightConfigExample.removeHP) {
            LOGGER.info("removeHP");
            ClientPlayNetworking.send(ModMessages.AMOUNT_ID, PacketByteBufs.create().writeString("removeHP"));
        } else if (amount==MidnightConfigExample.spawnCreeper) {
            LOGGER.info("spawnCreeper");
            ClientPlayNetworking.send(ModMessages.AMOUNT_ID, PacketByteBufs.create().writeString("spawnCreeper"));
        } else if (amount==MidnightConfigExample.spawnTNT) {
            LOGGER.info("spawnTNT");
            ClientPlayNetworking.send(ModMessages.AMOUNT_ID, PacketByteBufs.create().writeString("spawnTNT"));
        }
    }
}
