/*
import jdk.nashorn.internal.objects.Global;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.pagefactory.ByChained;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class Logic {

    static WebDriver driver;
    static WebDriverWait wait;
    static String hubUrl = "http://x-hire-demo-jenkins.xpxdev.net:4455/wd/hub";
    static JavascriptExecutor jsdriver;
    static final String url = "http://www.cram.com/flashcards/games/jewel/istqb-study-6496542";

    public Logic(){};

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

    private static int[][] getConfig(ArrayList<ArrayList<Integer>> xandy) {
        int[] col = {0, 5, 10};
        int[] row = {0, 3, 6, 9, 12};
        int[][][] gameGrid = new int[5][3][2];
        Map<String, Integer> colormap = new HashMap<String, Integer>();
        colormap.put("pink", 0);
        colormap.put("green", 1);
        colormap.put("blue", 2);
        colormap.put("red", 3);
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 3; x++) {
                int findx = xandy.get(0).get(col[x]);
                int findy = xandy.get(1).get(row[y]);
                String path = "//*[@id='col1']/div[contains(@style,'" + findx + "px; top: " + findy + "px')]";
                WebElement jewel = driver.findElement(By.xpath(path));
                WebElement img = driver.findElement(By.xpath(path + "/img"));
                String s =  jewel.getAttribute("id");
                int card = Integer.parseInt(s.substring(s.lastIndexOf("d") + 1));
                String color = StringUtils.substringBetween(img.getAttribute("src"), "-", ".");
                int cmap = colormap.get(color);
                int[][] value =
                gameGrid[y][x] =
            }
        }
        System.out.println(gameGrid[2][2].getCard() + gameGrid[2][2].getColor());
        return (gameGrid);
    }

    public static void main(String[] args) throws IOException, InterruptedException {

        final String url = "http://www.cram.com/flashcards/games/jewel/istqb-study-6496542";
        if (driver == null) {
            if (System.getProperty("runAs", "").equalsIgnoreCase("GRID")) {
                ChromeOptions options = new ChromeOptions();
                options.setHeadless(true);
                driver = new RemoteWebDriver(new URL(hubUrl), options);

            } else {
                System.setProperty("webdriver.chrome.driver", "/home/xpanxion/webdrivers/chromedriver");
                //ChromeOptions options = new ChromeOptions();
                //options.setHeadless(true);
                driver = new ChromeDriver();
                driver.manage().window().maximize();
            }
        }
        //System.setProperty("webdriver.chrome.driver", "/home/xpanxion/webdrivers/chromedriver");
        //WebDriver driver = new ChromeDriver();


        */
/*set variables for the program*//*

        driver.get(url);
        jsdriver = (JavascriptExecutor) driver;
        WebDriverWait wait = new WebDriverWait(driver, 15);
        ArrayList<Integer> cards = new ArrayList<Integer>();
        Map<String, String> answers = new HashMap<>();
        int success = 0;
        int failure = 0;

        */
/* get the cards *//*

        for (int i = 0; i < 15; i++) {
            cards.add(i);
        }
        Boolean ready = false;

        */
/*By by = By.id("menuButtonStartGame");
        wait.until(visibilityOfElementLocated(by));
        WebElement playGame = driver.findElement(by);
        playGame.click();

        ArrayList<ArrayList<Integer>> xandy = Jewel.getXAndY(cards, driver);
        Jewel[][] gameGrid = Main.getConfig(xandy);
        int pick = Main.optimumPick(gameGrid);*//*


        //Continuous run, tries for fast score and reset if it gets in trouble
        while (true) {
            try {
                Logic.continuousRun(cards, success, failure, url);
            } catch (Exception e) {
                failure++;
                e.printStackTrace();
            }
        }
    }

*/
/*
    private static int optimumPick(Jewel[][] gameGrid) {

    }

*//*


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
                    */
/*wait.until(ExpectedConditions.attributeContains(new ByChained(qpath).findElement(driver)
                            .findElement(By.xpath("/div/div")), "class", "Rendering"));*//*

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
                System.out.println("Match " + card + " completed.");
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
}


*/
