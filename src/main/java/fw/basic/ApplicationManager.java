package fw.basic;

import fw.basic.data.BaseDataProvider;
import fw.basic.data.properties.EnvironmentProperties;
import fw.basic.helpers.NavigationHelper;
import fw.basic.helpers.NavigationHelper2;
import fw.basic.helpers.WebDriverHelper;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import pages.login.LoginHelper;
import pages.login2.LoginHelper2;

import java.io.IOException;
import java.text.ParseException;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 19.01.13
 * Time: 21:14
 * To change this template use File | Settings | File Templates.
 */
/**
 * Class for management of all existing helpers
 **/
public class ApplicationManager {

    private static final String defaultBrowser = "CHROME";
    private static final String defaultSite = EnvironmentProperties.getProperty(BaseDataProvider.STAGE_LIVETELL_NET);
    private static ThreadLocal<ApplicationManager> applicationManager = new ThreadLocal<ApplicationManager>();
    private static String browserType;
    private static String serverSite;
    private static String saucelabsLogin;
    private static String saucelabsApiKey;
    private static String browserInfo;
    private org.slf4j.Logger LOG = LoggerFactory.getLogger(ApplicationManager.class);
    private WebDriverHelper webDriverHelper;
    private LoginHelper loginHelper;
    private NavigationHelper navigationHelper;
    private NavigationHelper2 navigationHelper2;
    private LoginHelper2 loginHelper2;

    public static ThreadLocal<ApplicationManager> getThreadAppManager() {
        return applicationManager;
    }

    /**
     * Read properties from Maven command line
     **/
    public void setUpProperties() {
        browserType = System.getProperty("browser", defaultBrowser).trim();
        LOG.info("Browser: " + browserType);
        serverSite = System.getProperty("site", defaultSite).trim();
        LOG.info("Site: " + serverSite);
        saucelabsLogin = System.getProperty("sauceLogin") != null ? System.getProperty("sauceLogin").trim() : null;
        saucelabsApiKey = System.getProperty("sauceApiKey") != null ? System.getProperty("sauceApiKey").trim() : null;
        LOG.info("Saucelabs login: " + saucelabsLogin);
    }

    /**
     * Create instance of ApplicationManager
     **/
    public static ApplicationManager getInstance() {
        System.out.println("!!!!!!!!!!!!!!!!!!ApplicationManager getInstance " + Thread.currentThread().getId());
        return applicationManager.get();
    }

    /**
     * Stop WebDriver and kill instance of ApplicationManager
     **/
    public void stop() {
        if (webDriverHelper != null) {
            webDriverHelper.stop();
        }
        if (applicationManager != null) {
            applicationManager = null;
        }
        System.out.println("!!!!!!!!!!!!!!!!!!!stop" + Thread.currentThread().getId());
    }

    public  void getScreenshot(ITestResult result) throws IOException, ParseException {
        webDriverHelper.getScreenshot(result);
    }

    public WebDriverHelper getWebDriverHelper() {
        if (webDriverHelper == null) {
            webDriverHelper = new WebDriverHelper(this);
        }
        return webDriverHelper;
    }

    public LoginHelper getLoginHelper() {
        if(loginHelper == null){
            loginHelper = new LoginHelper();
        }
        return loginHelper;
    }

    public NavigationHelper getNavigationHelper() {
        if(navigationHelper == null){
            navigationHelper = new NavigationHelper();
        }
        return navigationHelper;
    }

    public NavigationHelper2 getNavigationHelper2() {
        if(navigationHelper2 == null){
            navigationHelper2 = new NavigationHelper2();
        }
        return navigationHelper2;
    }

    public LoginHelper2 getLoginHelper2() {
        if(loginHelper2 == null){
            loginHelper2 = new LoginHelper2();
        }
        return loginHelper2;
    }

    public static String getBrowserType() {
        return browserType;
    }

    public static String getServerSite() {
        return serverSite;
    }

    public static String getSaucelabsLogin() {
        return saucelabsLogin;
    }

    public static String getSaucelabsApiKey() {
        return saucelabsApiKey;
    }

    public static String getBrowserInfo() {
        return browserInfo;
    }

    public static void setBrowserInfo(String browserInfo) {
        ApplicationManager.browserInfo = browserInfo;
    }
}
