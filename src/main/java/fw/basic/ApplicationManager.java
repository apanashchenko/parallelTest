package fw.basic;

import fw.basic.data.BaseDataProvider;
import fw.basic.data.properties.EnvironmentProperties;
import fw.basic.helpers.WebDriverHelper;
import org.slf4j.LoggerFactory;
import pages.login.LoginHelper;
import pages.vectorConnect.VectorConnectHelper;

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

    private final String defaultBrowser = "FIREFOX";
    private  final String defaultSite = EnvironmentProperties.getProperty(BaseDataProvider.STAGE_LIVETELL_NET);
    private static ThreadLocal<ApplicationManager> applicationManager = new ThreadLocal<ApplicationManager>();
    private static String browserType;
    private static String serverSite;
    private static String saucelabsLogin;
    private static String saucelabsApiKey;
    private static String browserInfo;
    private org.slf4j.Logger LOG = LoggerFactory.getLogger(ApplicationManager.class);
    private WebDriverHelper webDriverHelper;
    private LoginHelper loginHelper;
    private VectorConnectHelper vectorConnectHelper;

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
        return applicationManager.get();
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

    public VectorConnectHelper getVectorConnectHelper() {
        if(vectorConnectHelper == null){
            vectorConnectHelper = new VectorConnectHelper();
        }
        return vectorConnectHelper;
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
