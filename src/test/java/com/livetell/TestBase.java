package com.livetell;

import fw.basic.ApplicationManager;
import fw.basic.data.BaseDataProvider;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;


/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 19.01.13
 * Time: 18:06
 * To change this template use File | Settings | File Templates.
 */

/**
 * Parent class for test classes
 */
public class TestBase implements BaseDataProvider {

    protected ApplicationManager app;
    protected static String browser;
    private static org.slf4j.Logger LOG = LoggerFactory.getLogger(TestBase.class);
    protected static String serverSite = ApplicationManager.getServerSite();

    /**
     * Set up before test suite
     */
//    @BeforeSuite
    public void setUpBeforeSuite(ITestContext iTestContext) {
        app = new ApplicationManager();
        app.setUpProperties();
        iTestContext.setAttribute("application", app);
        LOG.info("Read system properties before suite");
    }

    /**
     * Set up before test
     */
    @BeforeTest
    public void deleteCookies() {
        app = new ApplicationManager();
        //set instance to Thread
        app.getThreadAppManager().set(app);
        app.setUpProperties();
//        iTestContext.setAttribute("application", app);
        LOG.info("Read system properties before suite");;
        app = ApplicationManager.getInstance();
        browser = app.getBrowserType();
        serverSite = app.getServerSite();
        String webDriverVersion = System.getProperty("webdriver.driver");
        String saucelabsLogin = app.getSaucelabsLogin();
        app.getWebDriverHelper().deleteAllCookies();
        app.getWebDriverHelper().openUrl(serverSite);
        app.getWebDriverHelper().refresh();
        LOG.info("Complete delete cookies before test");
        Reporter.log("<b style=\"color:#87CEFA\"> WebDriver version: " + webDriverVersion + "</b><br/>");
        Reporter.log("<b style=\"color:#1E90FF\"> Browser: " + browser + "</b><br/>");
        Reporter.log("<b style=\"color:#1E90FF\"> Site: " + serverSite + "</b><br/>");
        if (saucelabsLogin != null) {
            Reporter.log("<b style=\"color:#1E90FF\"> SouceLabs Login: " + saucelabsLogin + "</b><br/>");
        }
        LOG.info("Complete set up before test");
    }

    /**
     * Set up after test suite
     */
    @AfterTest
    public void tearDown() {
        app.getThreadAppManager().get().stop();
        LOG.info("Stopped tests");
    }
}
