import jdk.nashorn.internal.objects.Global;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.pagefactory.ByChained;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class Main {

    static WebDriver driver;
    static WebDriverWait wait;
    static String hubUrl = "http://x-hire-demo-jenkins.xpxdev.net:4455/wd/hub";
    static JavascriptExecutor jsdriver;

    public static void main(String[] args) throws IOException, InterruptedException {

        final String url = "http://www.cram.com/flashcards/games/jewel/istqb-study-6496542";
        if (driver == null) {
            if (System.getProperty("runAs", "").equalsIgnoreCase("GRID")) {
                ChromeOptions options = new ChromeOptions();
                options.setHeadless(true);
                driver = new RemoteWebDriver(new URL(hubUrl), options);

            } else {
                System.setProperty("webdriver.chrome.driver", "/home/xpanxion/webdrivers/chromedriver");
                driver = new ChromeDriver();
                driver.manage().window().maximize();
            }
        }
        //System.setProperty("webdriver.chrome.driver", "/home/xpanxion/webdrivers/chromedriver");
        //WebDriver driver = new ChromeDriver();


        /*set variables for the program*/
        driver.get(url);
        jsdriver = (JavascriptExecutor) driver;
        WebDriverWait wait = new WebDriverWait(driver, 15);
        ArrayList<Integer> cards = new ArrayList<Integer>();
        Map<String, String> answers = new HashMap<>();
        int success = 0;
        int failure = 0;

        /* get the cards */
        for (int i = 0; i < 15; i++) {
            cards.add(i);
        }
        Boolean ready = false;
        //WebDriverWait wait = new WebDriverWait(driver, 15);

        By by = By.id("menuButtonStartGame");
        wait.until(visibilityOfElementLocated(by));
        WebElement playGame = driver.findElement(by);
        playGame.click();

        int[][] xandy = Jewel.getXAndY(cards, driver);
        System.out.println(xandy);
        /*ArrayList[] xyarray = xandy.toArray(new ArrayList[xandy.size()]);
        Integer[] xinteger = (Integer[]) xyarray[0].toArray(new Integer[xyarray[0].size()]);
        Integer[] yinteger = (Integer[]) xyarray[1].toArray(new Integer[xyarray[0].size()]);
        System.out.println(xinteger + ", " + yinteger);
        int[] y = xandy.get(0).stream().mapToInt(Integer::intValue).toArray();
        System.out.println("Here are x and y " + x + ", " + y);
        String[][] gameGrid = Main.getConfig(x, y);*/

        /*Continuous run, tries for fast score and reset if it gets in trouble*/
        /*while (true) {
            try {
                Main.continuousRun(cards, success, failure, url);
            } catch (Exception e) {
                failure++;
                e.printStackTrace();
            }
        }*/
    }


    public static void continuousRun(ArrayList<Integer> cards, int success, int failure, String url) throws InterruptedException {
        Boolean ready = false;
        WebDriverWait wait = new WebDriverWait(driver, 15);

        By by = By.id("menuButtonStartGame");
        wait.until(visibilityOfElementLocated(by));
        WebElement playGame = driver.findElement(by);
        playGame.click();

        while (true) {
            for (Integer card : cards) {
                wait.until(numberOfElementsToBeLessThan(By.xpath("//*[@id='cardFaller']/div"), 1));
                By qpath = By.xpath("//*[@id='col1card" + card + "']");
                By apath = By.xpath("//*[@id='col2card" + card + "']");
                WebElement question = driver.findElement(qpath);
                wait.until(visibilityOf(question));
                    /*wait.until(ExpectedConditions.attributeContains(new ByChained(qpath).findElement(driver)
                            .findElement(By.xpath("/div/div")), "class", "Rendering"));*/
                wait.until(elementToBeClickable(question));
                wait.until(not(ExpectedConditions.stalenessOf(question)));
                try {
                    question.click();
                } catch (WebDriverException e) {
                    jsdriver.executeScript("arguments[0].click()", question);
                }
                ready = false;
                while (!ready) {
                    wait.until(not(ExpectedConditions.stalenessOf(question)));
                    ready = driver.findElement(By.xpath("//*[@id='col1card" + card + "']/img")).getAttribute("src")
                            .contains("selected");
                    Thread.sleep(100);
                }
                //wait.until(ExpectedConditions.attributeContains(By.xpath("//*[@id='col1card" + card + "']/img"),"src", "selected"));
                WebElement answer = driver.findElement(apath);
                wait.until(elementToBeClickable(answer));
                try {
                    answer.click();
                } catch (WebDriverException e) {

                    jsdriver.executeScript("arguments[0].click()", answer);
                }
                wait.until(invisibilityOfElementLocated(apath));
                wait.until(invisibilityOfElementLocated(qpath));
            }
            WebElement okay = driver.findElement(By.xpath("//*[@id='menuButtonWonOkay']"));
            wait.until(visibilityOf(okay));
            wait.until(elementToBeClickable(okay));
            okay.click();
            WebElement next = driver.findElement(By.xpath("//*[@id='menuButtonNextRound']"));
            wait.until(visibilityOf(next));
            Thread.sleep(3000);
            wait.until(elementToBeClickable(next));
            next.click();
            success++;
            System.out.println(success + " successes, " + failure + "failures");
        }
    }



    private static String[][] getConfig(int[] col, int[] row) {
        String[][] gameGrid = new String[3][5];
        List<WebElement> jewels = driver.findElements(By.xpath("//*[@id='col1']"));
        System.out.println(col);
        System.out.println(row);

        for (int x: col){
            for(int y: row){
                System.out.println(col[x]);
                System.out.println(row[y]);
                WebElement jewel = driver.findElement(By.xpath("//*[@id='col1']/div[contains(@style,'" + col[x] + "')" +
                        " AND contains(@style,'" + col[y] + "')]"));
                String id = jewel.getAttribute("id");
                gameGrid[x][y] = id;
                }
            }
            System.out.print(gameGrid);
            return(gameGrid);
        }
    }

