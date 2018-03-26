import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.script.ScriptContext;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfAllElementsLocatedBy;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

public class TitleScreen {
    static WebDriver driver;
    static WebDriverWait wait;
    static String hubUrl = "http://x-hire-demo-jenkins.xpxdev.net:4455/wd/hub";
    static JavascriptExecutor jsdriver;
    static final String url = "http://www.cram.com/flashcards/games/jewel/istqb-study-6496542";

    public TitleScreen(WebDriver driver, WebDriverWait wait){
        this.driver = driver;
        this.wait = wait;
    }

    /**
     * Returns a list of the menu buttons found in id = titleScreenArea
     * 0 is menuButtonStartGame
     * 1 is menuButtonPickSet
     * 2 is menuButtonHighScores
     * 3 is menuButtonQuit
     */
    public  static List<WebElement> getMenuButtons() {
        List<WebElement> menuButtons = driver.findElements(By.xpath("//*[@id='titleScreenButtons']/child::*"));
        wait.until(ExpectedConditions.elementToBeClickable(menuButtons.get(0)));
        return menuButtons;
    }

    /**
     * Returns a list WebElements found in id = popupSets
     * 0 is menuButtonMySetsTabArea
     * 1 is menuButtonSearchTabArea
     * 2 is menuButtonHighScores
     * 3 is menuButtonQuit
     */
    public static List<WebElement> getTitleButtons() {
        List<WebElement> titleButtons = driver.findElements(By.id("//*[@id='titleScreenButtons']/child::*"));
        wait.until(visibilityOf(titleButtons.get(1)));
        return(titleButtons);
    }

    public WebElement getPopupSet() {
        WebElement  popupSet = driver.findElement(By.id("popupSets"));
        wait.until(visibilityOf(popupSet));
        return(popupSet);
    }

    public WebElement getloginButton() {
        WebElement loginButton = driver.findElement(By.xpath("//*[@id='gameHeader']/div/ul/li[1]"));
        return(loginButton);
    }

    public WebElement popupLogin() {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("popupLogin")));
        WebElement  popupLogin = driver.findElement(By.id("popupLogin"));
        return(popupLogin);
    }

    public List<WebElement> getLoginInfo() {
        List<WebElement> loginInfo = new ArrayList<>();
        loginInfo.add(driver.findElement(By.xpath("//*[@id='loginInputArea']/child::input[1]")));
        loginInfo.add(driver.findElement(By.xpath("//*[@id='loginInputArea']/child::input[2]")));
        return(loginInfo);
    }

    public WebElement getPopupScores() {
        WebElement  popupScores = driver.findElement(By.id("popupScores"));
        wait.until(visibilityOf(popupScores));
        return(popupScores);
    }

    public WebElement highToMain(){
        WebElement toMain = driver.findElement(By.xpath("//*[@id='scoresArea']/center/div[3]"));
        wait.until(visibilityOf(toMain));
        return(toMain);
    }

    public WebElement getMainMenu() {
        WebElement mainMenu = driver.findElement(By.id("menuButtonMainMenuArea"));
        return(mainMenu);
    }

    public WebElement getMustache() {
        WebElement mustache = driver.findElement(By.id("headMustache"));
        wait.until(visibilityOf(mustache));
        return(mustache);
    }

    public WebElement getSecond() {
        WebElement second = driver.findElement(By.id("clockSecondHand"));
        return second;
    }

    public WebElement getMinute() {
        WebElement minute = driver.findElement(By.id("clockMinuteHand"));
        return minute;
    }

    public WebElement getTime() {
        WebElement time = driver.findElement(By.id("timerInner"));
        return time;
    }

    public WebElement getScore() {
        WebElement score = driver.findElement(By.id("scoreMessageInner"));
        return score;
    }

    public WebElement getExplosion() {
        WebElement explosion = driver.findElement(By.id("explosion"));
        return explosion;
    }

    public WebElement getHeadFrameIdle() {
        WebElement headFrameIdle = driver.findElement(By.className("idle"));
        return headFrameIdle;
    }

    public WebElement getQuestion(int i) throws InterruptedException {
        WebElement question = driver.findElement(By.id("col1card" + i));
        wait.until(ExpectedConditions.not(ExpectedConditions.stalenessOf(question)));
        Thread.sleep(50);
        wait.until(ExpectedConditions.elementToBeClickable(question));
        return question;
    }

    public WebElement getEye() {
        WebElement eye = driver.findElement(By.id("headEye"));
        return eye;
    }

    public WebElement getAnswer(int i) throws InterruptedException {
        WebElement answer = driver.findElement(By.id("col2card" + i));
        wait.until(ExpectedConditions.not(ExpectedConditions.stalenessOf(answer)));
        Thread.sleep(50);
        wait.until(ExpectedConditions.elementToBeClickable(answer));
        return answer;
    }

    public WebElement getHeadFrameSmile() {
        WebElement headFrameSmile = driver.findElement(By.className("smile"));
        return headFrameSmile;
    }

    public WebElement getHeadFrameLaugh() {
        WebElement headFrameLaugh = driver.findElement(By.className("laugh"));
        return headFrameLaugh;
    }

    public WebElement getHmm() {
        WebElement hmm = driver.findElement(By.className("hmm"));
        return hmm;
    }

    public void resilientClick(WebElement webElement) throws InterruptedException {
        for(int i = 0; i < 500; i++){
            try{
                webElement.click();
                break;
            } catch(Exception e){
                Thread.sleep(50);
            }
        }
    }

    public WebElement getWin() {
        WebElement win = driver.findElement(By.id("popupWon"));
        return win;
    }

    public WebElement getScript() {
        WebElement script = driver.findElement(By.id("col2"));
        return script;
    }


/*
    public WebElement getSearchBar() {
    }*/
}
