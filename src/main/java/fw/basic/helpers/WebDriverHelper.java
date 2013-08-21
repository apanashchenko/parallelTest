package fw.basic.helpers;


import fw.basic.ApplicationManager;
import fw.basic.data.BaseDataProvider;
import fw.basic.data.properties.BrowserTypes;
import fw.basic.wrap.ActionListener;
import fw.basic.wrap.CustomEventFiringWebDriver;
import fw.basic.wrap.TracingWebDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.ITestResult;
import org.testng.Reporter;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.fail;


/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 19.01.13
 * Time: 21:25
 * To change this template use File | Settings | File Templates.
 */

/**
 * Class for init and working with WebDriver
 **/
public class WebDriverHelper implements BaseDataProvider {

    private WebDriver driver;
    private StringBuffer verificationErrors = new StringBuffer();
    private final ApplicationManager manager;

    /**Init WebDriver*/
    public WebDriverHelper(ApplicationManager manager) {
        String browser = ApplicationManager.getBrowserType();
        switch (BrowserTypes.valueOf(browser)) {
            case FIREFOX: initLocalFireFox();
                break;
            case CHROME: initLocalChrome();
                break;
            case IE: initLocalInternetExplorer();
                break;
            case CHROME_SAUCELABS: initChromeSaucelabs();
                break;
        }
        this.manager = manager;
    }

    /** Close existing WebDriver*/
    public void stop() {
        driver.manage().deleteAllCookies();
        driver.quit();
        String verificationErrorsString = verificationErrors.toString();
        if(!"".equals(verificationErrorsString)){
            fail(verificationErrorsString);
        }
    }

    public void deleteAllCookies() {
        driver.manage().deleteAllCookies();
    }

    public void refresh() {
        driver.navigate().refresh();
    }

    public void openUrl(String url) {
        driver.get(url);
    }

    public WebDriver getDriver() {
        return driver;
    }

    private void initLocalFireFox() {
        DesiredCapabilities capabilFireFox = DesiredCapabilities.firefox();
//        capabilFireFox.setCapability("nativeEvents", true);
//        capabilFireFox.setCapability("unexpectedAlertBehaviour", "accept");
        driver = new CustomEventFiringWebDriver(new TracingWebDriver(new FirefoxDriver(capabilFireFox)));
        ((CustomEventFiringWebDriver) driver).setFileDetector(new LocalFileDetector());
        ((CustomEventFiringWebDriver) driver).register(new ActionListener(driver));
        ApplicationManager.getInstance().setBrowserInfo(getBrowserInfo());
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//        driver.manage().window().maximize();
        driver.get(ApplicationManager.getServerSite());
    }

    private void initLocalChrome() {
        DesiredCapabilities capabilChrome = DesiredCapabilities.chrome();
        capabilChrome.setCapability("nativeEvents", true);
        driver = new CustomEventFiringWebDriver(new TracingWebDriver(new ChromeDriver(capabilChrome)));
        ((CustomEventFiringWebDriver) driver).register(new ActionListener(driver));
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//        driver.manage().window().maximize();
        ApplicationManager.getInstance().setBrowserInfo(getBrowserInfo());
        driver.get(ApplicationManager.getServerSite());
    }

    private void initLocalInternetExplorer() {
        DesiredCapabilities capabilIe = DesiredCapabilities.internetExplorer();
        capabilIe.setCapability("nativeEvents", true);
        driver = new EventFiringWebDriver(new TracingWebDriver(new InternetExplorerDriver(capabilIe)));
        ((EventFiringWebDriver) driver).register(new ActionListener(driver));
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        ApplicationManager.getInstance().setBrowserInfo(getBrowserInfo());
        driver.get(ApplicationManager.getServerSite());
    }

    private void initChromeSaucelabs() {
        DesiredCapabilities capabilChrome = DesiredCapabilities.chrome();
        capabilChrome.setCapability("nativeEvents", true);
        capabilChrome.setCapability("selenium-version", "2.35.0");
        capabilChrome.setCapability("platform", "Windows 7");
        capabilChrome.setCapability("name", "Chrome tests");
        capabilChrome.setCapability("screen-resolution", "1280x1024");
        try {
            driver = new RemoteWebDriver(
                    new URL("http://"+ ApplicationManager.getSaucelabsLogin() +":"+ ApplicationManager.getSaucelabsApiKey()
                            + "@ondemand.saucelabs.com:80/wd/hub"), capabilChrome);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        ((RemoteWebDriver) driver).setFileDetector(new LocalFileDetector());
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        ApplicationManager.getInstance().setBrowserInfo(getBrowserInfo());
        driver.get(ApplicationManager.getServerSite());
    }

    private Object dateToDay() {
        Date today;
        String resultDate;
        SimpleDateFormat formatter;

        formatter = new SimpleDateFormat("EEE-d-MMM-yyyy H-mm");
        today = new Date();
        resultDate = formatter.format(today);
        return resultDate;
    }

    /** Get screenshot*/
    public void getScreenshot(ITestResult result) throws IOException, ParseException {
        takeScreenshot(result);
    }

    private void takeScreenshot(ITestResult result) throws IOException, ParseException {
       if (! result.isSuccess()) {
           File screenshot = new File("target\\surefire-reports\\" + result.getMethod().getMethodName() + "-"+ dateToDay() + ".png");
           screenshot.delete();
           File screenshotTempFile = ((TakesScreenshot) driver)
                   .getScreenshotAs(OutputType.FILE);
           FileUtils.copyFile(screenshotTempFile, screenshot);
           Reporter.log("<a href=\"../" + screenshot.getName() + "\">"
                   + result.getMethod().getMethodName() + " screenshot</a><br/>");
       }
    }

    /** Get information about current browser*/
    private String getBrowserInfo() {
        return ((JavascriptExecutor) driver).executeScript("var N= navigator.appName, ua= navigator.userAgent, tem;\n" +
                "    var M= ua.match(/(opera|chrome|safari|firefox|msie)\\/?\\s*(\\.?\\d+(\\.\\d+)*)/i);\n" +
                "    if(M && (tem= ua.match(/version\\/([\\.\\d]+)/i))!= null) M[2]= tem[1];\n" +
                "    M= M? [M[1], M[2]]: [N, navigator.appVersion, '-?'];\n" +
                "\n" +
                "    return M;").toString();
    }
}
