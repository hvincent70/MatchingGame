import org.apache.commons.lang3.StringUtils;
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
                System.setProperty("webdriver.chrome.driver", "/home/xpanxion/webdrivers1/chromedriver");
                ChromeOptions options = new ChromeOptions();
                options.setHeadless(true);
                driver = new ChromeDriver(options);
            }
        //}
        wait = new WebDriverWait(driver, 15);
        driver.get(url);
        Robot robot = new Robot();
    }

    @Test
    public void Login() throws InterruptedException {
        // Login in account, assert you get in
        TitleScreen tscreen = new TitleScreen(driver, wait);
        tscreen.resilientClick(tscreen.getloginButton());
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
        tscreen.resilientClick(tscreen.getMenuButtons().get(1));
        assertTrue(tscreen.getPopupSet().getCssValue("display").equalsIgnoreCase("block"));
    }

    @Test
    public void checkSecond() throws InterruptedException {
        //Get back to title screen, go to play game
        TitleScreen tscreen = new TitleScreen(driver, wait);
        tscreen.resilientClick(tscreen.getMenuButtons().get(0));
        String sectheta = StringUtils.substringBetween
                (tscreen.getSecond().getAttribute("style"), "(", "d");
        wait.until(ExpectedConditions.not(ExpectedConditions.attributeContains(
                tscreen.getSecond(), "style", sectheta)));
        assertFalse(sectheta.equalsIgnoreCase(tscreen.getSecond().getAttribute("style")));
    }


    @Test
    //Makes sure mustache rotates on startup
    public void checkMustache() throws InterruptedException {
        //Checks the game begins with a rotating mustache
        TitleScreen tscreen = new TitleScreen(driver, wait);
        tscreen.resilientClick(tscreen.getMenuButtons().get(0));
        String rot0 = tscreen.getMustache().getAttribute("style");
        Thread.sleep(50);
        assertFalse(rot0.equalsIgnoreCase(tscreen.getMustache().getAttribute("style")));
    }

    @Test
    //Checks to make sure the eye follows the mouse
    public void checkEye() throws InterruptedException {
        TitleScreen tscreen = new TitleScreen(driver, wait);
        tscreen.resilientClick(tscreen.getMenuButtons().get(0));
        tscreen.getQuestion(0).click();
        double eye1 = Double.parseDouble(tscreen.getEye().getCssValue("left").replace("px", ""));
        tscreen.getQuestion(0).click();
        tscreen.getAnswer(0).click();
        double eye2 = Double.parseDouble(tscreen.getEye().getCssValue("left").replace("px", ""));
        assertTrue(eye1 < 0 && eye2 > 0);
    }

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
        tscreen.resilientClick(tscreen.getMenuButtons().get(0));
        tscreen.getQuestion(0).click();
        tscreen.getAnswer(0).click();
        wait.until(ExpectedConditions.attributeToBeNotEmpty(tscreen.getExplosion(), "style"));
        assertTrue(tscreen.getExplosion().getCssValue("display").equals("block"));
    }

    @Test
    //Makes sure a wrong jewel selection turns to stone
    public static void stoneCheck() throws InterruptedException {
        TitleScreen tscreen = new TitleScreen(driver, wait);
        tscreen.resilientClick(tscreen.getMenuButtons().get(0));
        tscreen.resilientClick(tscreen.getQuestion(0));
        tscreen.resilientClick(tscreen.getAnswer(1));
        String stone = tscreen.getQuestionImage(0).getAttribute("src");
        assertTrue(stone.contains("stone"));
    }

    @Test
    //Makes sure a wrong jewel selection makes head give "hmm" expression
    public static void hmmCheck() throws InterruptedException{
        TitleScreen tscreen = new TitleScreen(driver, wait);
        tscreen.resilientClick(tscreen.getMenuButtons().get(0));
        tscreen.resilientClick(tscreen.getQuestion(0));
        tscreen.resilientClick(tscreen.getAnswer(1));
        wait.until(ExpectedConditions.attributeContains
                (tscreen.getHeadFrameIdle(), "style", "display: none;"));
        assertTrue(tscreen.getHmm().getCssValue("display").contains("block"));
    }

    @Test
    //Makes sure a wrong jewel selection raises an eyebrow
    public static void eyebrowCheck() throws InterruptedException {
        TitleScreen tscreen = new TitleScreen(driver, wait);
        tscreen.resilientClick(tscreen.getMenuButtons().get(0));
        tscreen.getQuestion(0).click();
        tscreen.getAnswer(1).click();
        wait.until(ExpectedConditions.attributeContains(tscreen.getMustache(),"class","hmm"));
        assertTrue(tscreen.getMustache().getAttribute("class").equals("hmm"));
    }

    @Test
    //Makes sure a title screen scores button goes to scores
    public static void highScores() throws InterruptedException {
        TitleScreen tscreen = new TitleScreen(driver, wait);
        tscreen.resilientClick(tscreen.getMenuButtons().get(2));
        wait.until(ExpectedConditions.attributeContains(tscreen.getPopupScores(), "style", "block"));
        assertTrue(tscreen.getPopupScores().getCssValue("display").equals("block"));
        }


    @Test
    //Makes sure reclicking jewel unselects it
    public static void unselect() throws InterruptedException {
        TitleScreen tscreen = new TitleScreen(driver, wait);
        tscreen.resilientClick(tscreen.getMenuButtons().get(0));
        tscreen.resilientClick(tscreen.getQuestion(0));
        tscreen.resilientClick(tscreen.getQuestion(0));
        assertFalse(tscreen.getQuestion(0).findElement(By.xpath(".//img")).getCssValue("src").contains("selected"));
    }

/*
    @Test
    //Makes sure completing game takes you to the win game screen
    public void winGame() throws InterruptedException {
        TitleScreen tscreen = new TitleScreen(driver, wait);
        tscreen.resilientClick(tscreen.getMenuButtons().get(0));
        for(int i = 0; i < 15; i++) {
            tscreen.resilientClick(tscreen.getQuestion(i));
            tscreen.resilientClick(tscreen.getAnswer(i));
            while(!tscreen.getHeadFrameIdle().getCssValue("display").equalsIgnoreCase("block")) {
                sleep(50);
            }
            }
        assertTrue(tscreen.getWin().getCssValue("display").equals("block"));
        }
*/

/*
    @Test
    //Assert jewel light up when mouse hovers overhead
    public static void jewelLight() throws InterruptedException, AWTException {
        Actions action = new Actions(driver);
        TitleScreen tscreen = new TitleScreen(driver, wait);
        tscreen.resilientClick(tscreen.getMenuButtons().get(0));
        action.moveToElement(tscreen.getQuestion(0));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("jewelImageHover")));
        wait.until(ExpectedConditions.attributeContains(tscreen.getQuestion(0).
                findElement(By.className("jewelImageHover")), "src", "selected"));
        assertTrue(tscreen.getQuestion(0).findElement(By.className("jewelImageHover"))
                .getAttribute("src").contains(".png"));
    }
*/


/*
    @Test
    //Assert jewel light up when mouse hovers overhead
    public static void scriptLight() throws InterruptedException, AWTException {
        Actions action = new Actions(driver);
        TitleScreen tscreen = new TitleScreen(driver, wait);
        tscreen.resilientClick(tscreen.getMenuButtons().get(0));
        action.moveToElement(tscreen.getAnswer(0));
        wait.until(ExpectedConditions.attributeToBe(tscreen.getAnswer(0).findElement(By.className("jewelImage")),
                "style", "opacity: 0.6;"));
        assertTrue(tscreen.getAnswer(0).getCssValue("opacity").equals(0.6));
    }
*/

/*
    @Test
    //Assert smile or laugh is displayed when answer is correct
    public static void smileLaugh() throws InterruptedException{
        TitleScreen tscreen = new TitleScreen(driver, wait);
        tscreen.resilientClick(tscreen.getMenuButtons().get(0));
        tscreen.resilientClick(tscreen.getQuestion(0));
        tscreen.resilientClick(tscreen.getQuestion(0));
        wait.until(ExpectedConditions.attributeContains
                (tscreen.getHeadFrameIdle(), "style", "display: none;"));
        assertTrue(tscreen.getHeadFrameSmile().getCssValue("display").equalsIgnoreCase("block") ||
                tscreen.getHeadFrameLaugh().getCssValue("display").equalsIgnoreCase("block"));
    }
*/

/*
    @Test
    public void checkMinute() throws InterruptedException {
        //Get back to title screen, go to play game
        TitleScreen tscreen = new TitleScreen(driver, wait);
        tscreen.resilientClick(tscreen.getMenuButtons().get(0));
        String secminute = StringUtils.substringBetween
                (tscreen.getMinute().getAttribute("style"), "(", "d");
        wait.until(ExpectedConditions.not(ExpectedConditions.attributeContains(
                tscreen.getMinute(), "style", secminute)));
        assertFalse(secminute.equalsIgnoreCase(tscreen.getMinute().getAttribute("style")));
    }
*/

/*
    @Test
    //Ensures score increases with correct answer
    public void checkScore() throws InterruptedException {
        TitleScreen tscreen = new TitleScreen(driver, wait);
        tscreen.resilientClick(tscreen.getMenuButtons().get(0));
        tscreen.resilientClick(tscreen.getQuestion(0));
        tscreen.resilientClick(tscreen.getAnswer(0));
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(By.id("scoreMessageInner"), "0")));
        assertTrue(Integer.parseInt(tscreen.getScore().getText()) >= 100);
    }
*/

/*
    @Test
    //Makes sure clicking on script expands script
    public void expandAnswer() throws InterruptedException{
        TitleScreen tscreen = new TitleScreen(driver, wait);
        tscreen.resilientClick(tscreen.getMenuButtons().get(0));
        tscreen.resilientClick(tscreen.getAnswer(0));
        assertFalse(tscreen.getScript().getCssValue("user-select").equals("none"));
    }
*/




    @AfterMethod
    public void tearDown() {
        driver.close();
    }
}