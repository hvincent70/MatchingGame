import jdk.nashorn.internal.objects.Global;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class Main {

    static WebDriver driver;
    static WebDriverWait wait;
    static String hubUrl = "http://x-hire-demo-jenkins.xpxdev.net:4455/wd/hub";

    public static void main(String[] args) throws MalformedURLException {

        final String url = "http://www.cram.com/flashcards/games/jewel/istqb-study-6496542";
        if(driver == null){
            if (System.getProperty("runAs", "").equalsIgnoreCase("GRID")){
                ChromeOptions options = new ChromeOptions();
                options.setHeadless(true);
                driver = new RemoteWebDriver(new URL(hubUrl), options);
            }else {
                System.setProperty("webdriver.chrome.driver", "/home/xpanxion/webdrivers/chromedriver");
                driver = new ChromeDriver();
            }
        }
        System.setProperty("webdriver.chrome.driver", "/home/xpanxion/webdrivers/chromedriver");
        WebDriver driver = new ChromeDriver();

        driver.get(url);

        By by = By.id("menuButtonStartGame");
        WebDriverWait wait = new WebDriverWait(driver, 15);
        wait.until(visibilityOfElementLocated(by));

        WebElement playGame = driver.findElement(by);
        playGame.click();

        for(int i = 0; i < 14; i++){
            String[][] gameGrid = Main.getConfig(driver);


        }
    }

    private static String[][] getConfig(WebDriver driver) {
        int[] ycol = {0, 53, 106, 159, 212};
        int[] xcol = {10, 103, 196};
        String source;
        String gameGrid[][] = new String[3][5];
        List<WebElement> jewels = driver.findElements(By.xpath("//*[@id='col1']"));

        for (int x: xcol){
            for(int y: ycol){
                for(WebElement jewel: jewels){
                    Point loc = jewel.getLocation();
                    if(x == loc.x && y == loc.y){
                      gameGrid[x][y] = jewel.getAttribute("src");
                      break;
                    }
                }
            }
        }
        return gameGrid;
    }
}
