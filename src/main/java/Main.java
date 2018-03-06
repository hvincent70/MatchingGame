import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

public class Main {

    public static void main(String[] args){
        String url = "http://www.cram.com/flashcards/games/jewel/istqb-study-6496542";
        System.setProperty("webdriver.chrome.driver", "/home/xpanxion/webdrivers/chromedriver");

        WebDriver driver = new ChromeDriver();
        driver.get(url);
        WebElement playGame = driver.findElement(By.id("menuButtonStartGame"));
        playGame.click();

    }
}
\