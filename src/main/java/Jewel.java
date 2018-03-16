import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.*;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class Jewel {
    private String color;
    private int card;

    public String getColor() {
        return color;
    }

    public int getCard() {
        return card;
    }

    public Jewel(int jcard, String jcolor) {
        color = jcolor;
        card = jcard;
    }


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

        //List<WebElement> elements = driver.findElements(By.xpath("//*[starts-with(@id,'col1card')]"));
        List<Jewel> jewels = new ArrayList<Jewel>();
        ArrayList<Integer> cards = new ArrayList<Integer>();
        ArrayList<ArrayList<Integer>> xandy = new ArrayList<>();

        for (int i = 0; i < 15; i++) {
            cards.add(i);
        }
    }


    public static ArrayList<ArrayList<Integer>> getXAndY(ArrayList<Integer> cards, WebDriver driver) {
        ArrayList<ArrayList<Integer>> xandy = new ArrayList<>();
        ArrayList<Integer> justx = new ArrayList<Integer>();
        ArrayList<Integer> justy = new ArrayList<Integer>();
        for (Integer card : cards) {
            WebElement jewel = driver.findElement(By.xpath("//*[@id='col1card" + card + "']"));
            Integer xpos = Integer.valueOf(Integer.parseInt(jewel.getCssValue("left").replace("px", "")));
            Integer ypos = Integer.valueOf(Integer.parseInt(jewel.getCssValue("top").replace("px", "")));
            justx.add(xpos);
            justy.add(ypos);
        }
        Collections.sort(justx);
        Collections.sort(justy);
        xandy.add(justx);
        xandy.add(justy);
        return (xandy);
    }
}

