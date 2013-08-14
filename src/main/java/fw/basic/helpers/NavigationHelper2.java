package fw.basic.helpers;

import fw.basic.ApplicationManager;
import fw.basic.data.NavigationDataProviders;
import org.slf4j.LoggerFactory;
import pages.login.LoginHelper;
import pages.login.data.Role;
import pages.login2.LoginHelper2;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 19.01.13
 * Time: 21:45
 * To change this template use File | Settings | File Templates.
 */

/**
 * Basic navigation on LiveTell
 **/
public class NavigationHelper2 extends BaseHelper implements NavigationDataProviders {

    private static String serverSite;
    private static org.slf4j.Logger LOG = LoggerFactory.getLogger(NavigationHelper2.class);

    protected String getBaseUrl() {
        serverSite = ApplicationManager.getServerSite();
        return serverSite;
    }

    public LoginHelper goToLoginPage() {
        if (getCurrentUrl().equals(getBaseUrl() + LOGIN_PAGE)) {
            if (getBrowser().equals(BROWSER_SAFARI)) {
                openUrl(getBaseUrl() + LOGIN_PAGE);
            }
            reporterLog("Login is a current page");
            manager.getLoginHelper().checkLoginPage();
            return new LoginHelper();
        } else {
            openUrl(getBaseUrl() + LOGIN_PAGE);
            reporterLog("Go to Login page");
            manager.getLoginHelper().checkLoginPage();
            return new LoginHelper();
        }
    }

  public LoginHelper2 goToLoginPage2() {
        if (getCurrentUrl().equals(getBaseUrl() + LOGIN_PAGE)) {
            if (getBrowser().equals(BROWSER_SAFARI)) {
                openUrl(getBaseUrl() + LOGIN_PAGE);
            }
            reporterLog("Login is a current page");
            manager.getLoginHelper().checkLoginPage();
            return new LoginHelper2();
        } else {
            openUrl(getBaseUrl() + LOGIN_PAGE);
            reporterLog("Go to Login page");
            manager.getLoginHelper().checkLoginPage();
            return new LoginHelper2();
        }
    }

    public void goToTraineeMenuPage() {
        if (getCurrentUrl().equals(getBaseUrl() + TRAINEE_MENU_PAGE)) {
            if (getBrowser().equals(BROWSER_SAFARI)) {
                openUrl(getBaseUrl() + TRAINEE_MENU_PAGE);
            }
            reporterLog("Trainee menu is current page");
            manager.getLoginHelper().assertThatUserWithSelectRoleLogin(Role.TRAINEE);
            reporterLog("Check Trainee menu page");
        } else {
            openUrl(getBaseUrl() + TRAINEE_MENU_PAGE);
            reporterLog("Go to Trainee menu page");
            manager.getLoginHelper().assertThatUserWithSelectRoleLogin(Role.TRAINEE);
            reporterLog("Check Trainee menu page");
        }
    }

    public void goToDashboard()  {
        if (getCurrentUrl().equals(getBaseUrl() + DASHBOARD)) {
            if (getBrowser().equals(BROWSER_SAFARI)) {
                openUrl(getBaseUrl() + DASHBOARD);
            }
            reporterLog("Dashboard current page");
        } else {
            openUrl(getBaseUrl() + DASHBOARD);
            reporterLog("Go to Dashboard page");
        }
    }

}
