package fw.basic.wrap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 22.10.12
 * Time: 21:24
 * To change this template use File | Settings | File Templates.
 */
public class ActionListener extends AbstractWebDriverEventListener {

    private WebDriverWait wait;
    private static String color = "red";

    public ActionListener(WebDriver driver){
        wait = new WebDriverWait(driver,10);
    }

    /*********** Use without illumination elements ************/
    @Override
    public void beforeClickOn(WebElement element, WebDriver driver){
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Override
    public void beforeFindBy(By by, WebElement element, WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Override
    public void afterFindBy(By by, WebElement element, WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    /*********** Use for illumination elements beforeClickOn ************/
    /*@Override
    public void afterFindBy(By by, WebElement element, WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Override
    public void beforeFindBy(By by, WebElement element, WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Override
    public void beforeClickOn(WebElement element, WebDriver  driver) {
        flash(element, driver);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Override
    public void beforeChangeValueOf(WebElement element,  WebDriver driver) {
        flash(element, driver);
    }

    private void flash(WebElement element, WebDriver driver) {
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        String bgcolor  = element.getCssValue("backgroundColor");
        for (int i = 0; i <  2; i++) {
            changeColor(color, element, js);
            changeColor(bgcolor, element, js);
        }
    }

    private void changeColor(String color, WebElement element,  JavascriptExecutor js) {
        js.executeScript("arguments[0].style.backgroundColor = '"+color+"'",  element);
        try {
            Thread.sleep(10);
        }  catch (InterruptedException e) {
        }
    }*/
}





