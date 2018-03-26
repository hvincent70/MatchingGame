import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.event.InputEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

public class Testing1 {
    static WebDriver driver;
    static WebDriverWait wait;
    static String hubUrl = "http://x-hire-demo-jenkins.xpxdev.net:4455/wd/hub";
    static JavascriptExecutor jsdriver;
    static final String url = "https://www.google.com/";

    public static void main(String[] args) throws InterruptedException, AWTException {
        System.setProperty("webdriver.chrome.driver", "/home/xpanxion/webdrivers/chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        Actions action = new Actions(driver);
        Scanner sc = new Scanner(System.in);

        wait = new WebDriverWait(driver, 15);
        driver.get(url);
        WebElement hover = driver.findElement(By.xpath("//*[@id='gbqfbb']"));
        System.out.println(hover.getAttribute("type"));
        Point a = MouseInfo.getPointerInfo().getLocation();
        System.out.println(a.getX() + ", " + a.getY());
        action.moveToElement(hover);
        hover.click();
        Thread.sleep(5000);


        //robot.
    }
}
