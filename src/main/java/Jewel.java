import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class Jewel {

    public static void main(String[] args) {
        final String url = "http://www.cram.com/flashcards/games/jewel/istqb-study-6496542";

        System.setProperty("webdriver.chrome.driver", "/home/xpanxion/webdrivers/chromedriver");
        WebDriver driver = new ChromeDriver();

        driver.get(url);

        By by = By.id("menuButtonStartGame");
        WebDriverWait wait = new WebDriverWait(driver, 15);
        wait.until(visibilityOfElementLocated(by));

        WebElement playGame = driver.findElement(by);
        playGame.click();

        List<WebElement> jewels = driver.findElements(By.xpath("//*[starts-with(@id,'col1card')]"));
        TreeMap<Coor, String> gg = new TreeMap<Coor, String>();

        for (WebElement jewel : jewels) {
            String xpos = jewel.getCssValue("left").replace("px", "");
            String ypos = jewel.getCssValue("top").replace("px", "");
            String color = StringUtils.substringBetween(jewel.findElement(By.className("jewelImage"))
                    .getAttribute("src"), "-", ".");
            System.out.println(xpos + ", " + ypos + ", " + color);
            Coor coor = new Coor(Integer.parseInt(xpos), Integer.parseInt(ypos));
            gg.put(coor, color);
        }
    }
}
