import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.*;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class Jewel {
    private int x;
    private int y;
    private String color;
    private String text;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getColor() {
        return color;
    }

    public String getText() {
        return text;
    }

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
        ArrayList<ArrayList<Integer>> xandy = new ArrayList<>();

        for (int i = 0; i < 15; i++) {
            cards.add(i);
        }
    }


    public static int[][] getXAndY(ArrayList<Integer> cards, WebDriver driver) {
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
        int[] x = justx.stream().mapToInt(Integer::intValue).toArray();
        int[] y = justx.stream().mapToInt(Integer::intValue).toArray();
        int[][] xandy = {{x[0], x[5], x[10]}, {y[0], y[3], y[6], y[9], y[12]}};
        System.out.println(xandy);
        return (xandy);
    }
}

