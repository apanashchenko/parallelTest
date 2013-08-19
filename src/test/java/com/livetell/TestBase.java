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

    protected String browser;
    protected String defaultServer;
    protected ApplicationManager app;
    private org.slf4j.Logger LOG = LoggerFactory.getLogger(TestBase.class);
    private String webDriverVersion;
    private String saucelabsLogin;


    /**
     * Read properties  and start WebDriver instance before test
     */
    @BeforeTest
    public void setUpBeforeTest(ITestContext iTestContext) {
        crateAppManagerInstance(iTestContext);
        deleteBrowserCookies();
        outputBrowserInfo();
        LOG.info("Suite name: " + iTestContext.getCurrentXmlTest().getSuite().getName());
        LOG.info("Test name: " + iTestContext.getCurrentXmlTest().getName());
    }

    /**
     * Stop current WebDriver instance after test
     */
    @AfterTest
    public void tearDown() {
        stopCurrentBrowser();
    }

    private void crateAppManagerInstance(ITestContext iTestContext) {
        app = new ApplicationManager();
        app.getThreadAppManager().set(app);
        app.setUpProperties();
        iTestContext.setAttribute("application", app);
        app = ApplicationManager.getInstance();
        browser = app.getBrowserType();
        defaultServer = app.getServerSite();
        webDriverVersion = System.getProperty("webdriver.driver");
        saucelabsLogin = app.getSaucelabsLogin();
        LOG.info("Crate AppManager instance");
    }

    private void deleteBrowserCookies() {
        app.getWebDriverHelper().deleteAllCookies();
        app.getWebDriverHelper().openUrl(defaultServer);
        app.getWebDriverHelper().refresh();
        LOG.info("Complete delete cookies before test");
    }

    private void outputBrowserInfo() {
        Reporter.log("<b style=\"color:#87CEFA\"> WebDriver version: " + webDriverVersion + "</b><br/>");
        Reporter.log("<b style=\"color:#1E90FF\"> Browser: " + browser + "</b><br/>");
        Reporter.log("<b style=\"color:#1E90FF\"> Site: " + defaultServer + "</b><br/>");
        if (saucelabsLogin != null) {
            Reporter.log("<b style=\"color:#1E90FF\"> Soucelabs Login: " + saucelabsLogin + "</b><br/>");
        }
        LOG.info("Complete set up before test");
    }

    private void stopCurrentBrowser() {
        app.getThreadAppManager().get().getWebDriverHelper().stop();
        LOG.info("Stopped tests");
    }
}
