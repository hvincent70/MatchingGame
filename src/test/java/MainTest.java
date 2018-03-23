import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.awt.*;
import java.net.URL;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.textToBe;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;
import static org.testng.Assert.*;

public class MainTest {
    static WebDriver driver;
    static WebDriverWait wait;
    static String hubUrl = "http://x-hire-demo-jenkins.xpxdev.net:4455/wd/hub";
    static JavascriptExecutor jsdriver;
    static final String url = "http://www.cram.com/flashcards/games/jewel/istqb-study-6496542";

    @BeforeMethod
    public void setUp() throws Exception {
        //if (driver == null) {
            if (System.getProperty("runAs", "").equalsIgnoreCase("GRID")) {
                ChromeOptions options = new ChromeOptions();
                options.setHeadless(true);
                driver = new RemoteWebDriver(new URL(hubUrl), options);

            } else {
                System.setProperty("webdriver.chrome.driver", "/home/xpanxion/webdrivers/chromedriver");
                //ChromeOptions options = new ChromeOptions();
                //options.setHeadless(true);
                driver = new ChromeDriver();
            }
        //}
        wait = new WebDriverWait(driver, 15);
        driver.get(url);
        Robot robot = new Robot();
    }

    @Test
    public void Login() {
        // Login in account, assert you get in
        TitleScreen tscreen = new TitleScreen(driver, wait);
        tscreen.getloginButton().click();
        tscreen.getLoginInfo().get(0).sendKeys("promethius");
        tscreen.getLoginInfo().get(1).sendKeys("jasper");
        tscreen.getLoginInfo().get(1).submit();
        wait.until(visibilityOf(tscreen.getPopupScores()));
        assertTrue(tscreen.getPopupScores().getCssValue("display").equalsIgnoreCase("block"));
    }

    @Test
    public void goToSets() throws InterruptedException {
        //Go to sets
        TitleScreen tscreen = new TitleScreen(driver, wait);
        tscreen.getMenuButtons().get(1).click();
        assertTrue(tscreen.getPopupSet().getCssValue("display").equalsIgnoreCase("block"));
    }

    @Test
    public void checkSecond() throws InterruptedException {
        //Get back to title screen, go to play game
        TitleScreen tscreen = new TitleScreen(driver, wait);
        tscreen.getMenuButtons().get(0).click();
        String sectheta0 = tscreen.getSecond().getAttribute("style");
        Thread.sleep(2000);
        assertFalse(sectheta0.equalsIgnoreCase(tscreen.getSecond().getAttribute("style")));
    }

    @Test
    //Makes sure mustache rotates on startup
    public void checkMustache() throws InterruptedException {
        //Checks the game begins with a rotating mustache
        TitleScreen tscreen = new TitleScreen(driver, wait);
        tscreen.getMenuButtons().get(0).click();
        String rot0 = tscreen.getMustache().getAttribute("style");
        Thread.sleep(50);
        assertFalse(rot0.equalsIgnoreCase(tscreen.getMustache().getAttribute("style")));
    }

    @Test
    //Checks to make sure the eye follows the mouse
    public void checkEye() throws InterruptedException {
        TitleScreen tscreen = new TitleScreen(driver, wait);
        tscreen.getMenuButtons().get(0).click();
        tscreen.getQuestion(0).click();
        double eye1 = Double.parseDouble(tscreen.getEye().getCssValue("left").replace("px", ""));
        tscreen.getQuestion(0).click();
        tscreen.getAnswer(0).click();
        double eye2 = Double.parseDouble(tscreen.getEye().getCssValue("left").replace("px", ""));
        assertTrue(eye1 < 0 && eye2 > 0);
    }

    @Test
    //Ensures score increases with correct answer
    public void checkScore() throws InterruptedException {
        TitleScreen tscreen = new TitleScreen(driver, wait);
        tscreen.getMenuButtons().get(0).click();
        tscreen.resilientClick(tscreen.getQuestion(0));
        tscreen.resilientClick(tscreen.getAnswer(0));
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(By.id("scoreMessageInner"), "0")));
        assertTrue(Integer.parseInt(tscreen.getScore().getText()) >= 100);
    }
/*
    @Test
    public void winGame() throws InterruptedException {
        TitleScreen tscreen = new TitleScreen(driver, wait);
        tscreen.getMenuButtons().get(0).click();
        boolean selected = false;
        for(int i = 0; i < 15; i++) {
            tscreen.getQuestion(i).click();
            tscreen.getAnswer(i).click();
            while(tscreen.getHeadFrameIdle().getCssValue("display").equalsIgnoreCase("block")) {
                sleep(50);
            }
            }
        }
*/
    protected void sleep(int milliseconds) {
        try{
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            System.out.println("man did you screw up");
        }
    }

    @Test
    //Makes sure explosion occurs with correct answer
    public static void explosionCheck() throws InterruptedException {
        TitleScreen tscreen = new TitleScreen(driver, wait);
        tscreen.getMenuButtons().get(0).click();
        String explosionloc = tscreen.getExplosion().getAttribute("style");
        tscreen.getQuestion(0).click();
        tscreen.getAnswer(0).click();
        assertFalse(explosionloc.equalsIgnoreCase(tscreen.getExplosion().getAttribute("style")));
    }

    @Test
    //Makes sure a wrong jewel selection turns to stone
    public static void stoneCheck() throws InterruptedException {
        TitleScreen tscreen = new TitleScreen(driver, wait);
        tscreen.getMenuButtons().get(0).click();
        tscreen.getQuestion(0).click();
        tscreen.getAnswer(1).click();
        assertTrue(tscreen.getQuestion(0).findElement(By.xpath(".//img")).getAttribute("src").contains("stone"));
    }

    @Test
    //Makes sure a wrong jewel selection makes head give "hmm" expression
    public static void hmmCheck() throws InterruptedException{
        TitleScreen tscreen = new TitleScreen(driver, wait);
        tscreen.getMenuButtons().get(0).click();
        tscreen.getQuestion(0).click();
        tscreen.getAnswer(1).click();
        assertTrue(tscreen.getHmm().getCssValue("display").contains("block"));
    }

    @Test
    //Makes sure a wrong jewel selection raises an eyebrow
    public static void eyebrowCheck() throws InterruptedException {
        TitleScreen tscreen = new TitleScreen(driver, wait);
        tscreen.getMenuButtons().get(0).click();
        tscreen.getQuestion(0).click();
        tscreen.getAnswer(1).click();
        wait.until(ExpectedConditions.attributeContains(tscreen.getMustache(),"class","hmm"));
        assertTrue(tscreen.getMustache().getAttribute("class").equals("hmm"));
    }

    @Test
    //Makes sure a title screen scores button goes to scores
    public static void highScores(){
        TitleScreen tscreen = new TitleScreen(driver, wait);
        tscreen.getMenuButtons().get(0).click();
        }
    /*
        tscreen.getMenuButtons().get(0).click();
        tscreen.getQuestion(0).click();
        tscreen.getAnswer(1).click();


        String mintheta0 = tscreen.getMinute().getAttribute("style");
        String timer0 = tscreen.getTime().getText();
        String score0 = tscreen.getScore().getText();
        String explosionloc = tscreen.getExplosion().getAttribute("style");

        //Make sure mustache rotates
        actions.moveToElement(tscreen.getMustache());
        String rot0 = tscreen.getMustache().getAttribute("style");
        Thread.sleep(50);
        String rot1 = tscreen.getMustache().getAttribute("style");
        assertFalse(rot0.equalsIgnoreCase(rot1));

        //Assert head idle is displayed
        assertTrue(tscreen.getHeadFrameIdle().getCssValue("display").equalsIgnoreCase("block"));

        //Assert jewel light up when mouse hovers overhead
        actions.moveToElement(tscreen.getQuestion(0));
        actions.moveByOffset(15, -5);
        wait.until(ExpectedConditions.attributeContains(tscreen.getQuestion(0).
                findElement(By.className("jewelImageHover")), "src", "selected"));
        assertTrue(tscreen.getQuestion(0).findElement(By.className("jewelImageHover"))
                .getAttribute("src").contains(".png"));

        //Make sure eyes move to the left; clear first card
        tscreen.getQuestion(0).click();
        assertTrue(Integer.parseInt(StringUtils.substringBetween(tscreen.getEye()
                        .getAttribute("style"), "left: ", "px; t")) < 0);

        //Assert jewel is selected
        assertTrue(StringUtils.substringBetween(tscreen.getQuestion(0).findElement(By.className("jewelImage"))
                .getAttribute("src"), "-", ".").equalsIgnoreCase("selected"));

        //Make sure eyes move to the right;
        tscreen.getAnswer(0).click();
        assertTrue(Integer.parseInt(StringUtils.substringBetween(tscreen.getEye()
                .getAttribute("style"), "left: ", "px; t")) > 0);

        //Assert explosion occurs when card is cleared
        assertFalse(explosionloc.equalsIgnoreCase(tscreen.getExplosion().getAttribute("style")));

        //Assert head smile or head laugh is displayed
        assertTrue(tscreen.getHeadFrameSmile().getCssValue("display").equalsIgnoreCase("block") ||
                    tscreen.getHeadFrameLaugh().getCssValue("display").equalsIgnoreCase("block"));

        //Assert score is greater
        assertTrue(Integer.parseInt(score0) > Integer.parseInt(tscreen.getScore().getText()));

        //Assert timer has advanced
        assertFalse(timer0.equalsIgnoreCase(tscreen.getTime().getText()));

        //Assert question clears
        assertTrue(tscreen.getQuestion(0).getCssValue("display").equalsIgnoreCase("none"));

        //Assert answer clears
        assertTrue(tscreen.getAnswer(0).getCssValue("display").equalsIgnoreCase("none"));

        //Assert wrong answer turns jewel to stone
        tscreen.getQuestion(1).click();
        tscreen.getAnswer(2).click();
        assertTrue(StringUtils.substringBetween(tscreen.getQuestion(1).findElement(By.className("jewelImage"))
                .getAttribute("src"), "-", ".").equalsIgnoreCase("stone"));

        //Assert second hand has moved
        assertFalse(sectheta0.equalsIgnoreCase(tscreen.getSecond().getAttribute("style")));

        //Assert minute hand has moved
        assertFalse(sectheta0.equalsIgnoreCase(tscreen.getMinute().getAttribute("style")));
    }
/*
    @Test
    public void searchForSet(){
        TitleScreen tscreen = new TitleScreen(driver, wait);
        //tscreen.getSearchBar().sendKeys("hello");
        }
*/

    @AfterMethod
    public void tearDown() {
        driver.close();
    }
}