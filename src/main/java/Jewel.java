import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.commons.lang3.StringUtils;
import java.util.HashSet;

import java.awt.geom.RectangularShape;
import java.util.*;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class Jewel {
    private int x;
    private int y;
    private String color;
    private String text;

    public int getX(){return x;}

    public int getY(){return y;}

    public String getColor(){return color;}

    public String getText(){return text;}

    public Jewel(int xpos, int ypos, String jcolor, String question) {
        x = xpos;
        y = ypos;
        color = jcolor;
        text = question;
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
        NavigableSet<Integer> xvals = new TreeSet<Integer>();

        for(int i = 0; i < 15; i++){
            cards.add(i);
        }

        for (Integer card: cards) {
            WebElement jewel = driver.findElement(By.xpath("//*[@id='col1card" + card + "']"));
            int xpos = Integer.parseInt(jewel.getCssValue("left").replace("px", ""));
            int ypos = Integer.parseInt(jewel.getCssValue("top").replace("px", ""));
            String color = StringUtils.substringBetween(jewel.findElement(By.className("jewelImage"))
                    .getAttribute("src"), "-", ".");
            String text = driver.findElement(By.xpath("//*[@id='col1card" + card + "']/div/div/div")).getText();
            System.out.println(xpos + ", " + ypos + ", " + color + ", " + text);
        }
        /*for (int i = 0; i < jewels.size(); i ++){
            System.out.println(jewels.get(i).getX() + ", " + jewels.get(i).getY() + ", " + jewels.get(i).getColor()
                    + ", " + jewels.get(i).getText());*/
        }
    }

