package pages.login.pages;

import fw.basic.helpers.BaseHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.login.data.LoginDataProviders;
import pages.login.data.Role;
import pages.staticdata.PageTitle;
import ru.yandex.qatools.htmlelements.element.Button;
import ru.yandex.qatools.htmlelements.element.Link;
import ru.yandex.qatools.htmlelements.element.TextInput;

/**
 * Created with IntelliJ IDEA.
 * User: alpa
 * Date: 16.01.13
 * Time: 16:10
 * To change this template use File | Settings | File Templates.
 */
public class LoginPage extends BaseHelper implements LoginDataProviders {

    /**Page WebElements**/
    @FindBy(xpath = "//a[@href='/webapp/profile/setup.htm']")
    private Link setupProfileLink;

    @FindBy(xpath = "//a[@href='/webapp/profile/verify.htm']")
    private Link verifyInfoLink;

    @FindBy(xpath = "//a[@href='/webapp/profile/uploadPhoto.htm']")
    private Button uploadProfilePhoto;

    @FindBy(id = "trainee_change_password")
    private WebElement traineeChangePassword;

    @FindBy(id = "password")
    private TextInput newPassword;

    /** Verify Login pages. */
    public void checkLoginPage() {
        assertVisibility(By.xpath(ROLE_SWITCHER_LINK), "Login form doesn't display");
        assertPageTitle(PageTitle.LOGIN);
        isStringContains(getElementText(By.xpath("//p[@class='top-information']")),
                ("If you already have a VectorConnect account\n" +
                        "use the \"Login with VectorConnector\""), "Login with VectorConnector text doesn't correct");
        reporterLog("Check Login pages");
    }

    /**
     * Select role for loginCredentials
     * @param role Enum role
     **/
    public void selectRole(Role role) {
        switch (role) {
            case TRAINEE_FIRST_LOGIN:
                if (getElementText(By.xpath(ROLE_SWITCHER_LINK)).equals(ALREADY_HAVE_PASSWORD)) {
                    reporterLog(role + " is selected");
                } else {
                    waitElementPresent(By.xpath(ROLE_SWITCHER_LINK));
                    clickOnButton(switchRoleLink);
                    isStringsEquals(ALREADY_HAVE_PASSWORD,
                            getElementText(By.xpath(firstTimeHereLink)), "Doesn't switch on trainee first login form");
                    reporterLog("Select " + role + " role");
                }
                break;
            case TRAINEE:
                if (getElementText(By.xpath(ROLE_SWITCHER_LINK)).equals(FIRST_TIME_HERE)) {
                    reporterLog(role + " is selected");
                } else {
                    clickOnButton(switchRoleLink);
                    waitElementPresent(By.xpath(ROLE_SWITCHER_LINK));
                    isStringsEquals(FIRST_TIME_HERE , getElementText(By.xpath(ROLE_SWITCHER_LINK))
                            , "Doesn't switch on trainee login");
                    reporterLog("Select " + role + " role");
                }
                break;
        }
    }

    /**
     * Verify correct login for select role
     * @param role (Trainee first loginCredentials, Trainee, Rep)
     **/
    public void assertThatUserWithSelectRoleLogin(Role role) {
        switch (role) {
            case TRAINEE:
                waitElementPresent(By.xpath(LOG_OUT_BUTTON));
                assertVisibility(logOutButton, "Log out button doesn't display");
                isStringsEquals(PageTitle.DASHBOARD.getTitleValue(), getPageTitle(), "Wrong Trainee Dashboard pages title");
                implicitlyWait(10);
                reporterLog("Successfully login as: " + role);
                break;
            case REP:
                waitElementPresent(By.xpath(LOG_OUT_BUTTON));
                manager.getNavigationHelper().goToDashboard();
                assertVisibility(logOutButton, "Log out button doesn't display");
                isStringsEquals(PageTitle.DASHBOARD.getTitleValue(), getPageTitle(),
                        "Wrong Trainee Dashboard pages title");
                reporterLog("Successfully login as: " + role);
                break;
            case MANAGER:
                waitElementPresent(By.xpath(LOG_OUT_BUTTON));
                manager.getNavigationHelper().goToDashboard();
                assertVisibility(logOutButton, "Log out button doesn't display");
                isStringsEquals(PageTitle.DASHBOARD.getTitleValue(), getPageTitle(),
                        "Wrong Trainee Dashboard pages title");
                reporterLog("Successfully login as: " + role);
                break;
        }
    }

    /**
     * @param locator element locator
     * @param messageText error message text
     * */
    public void checkErrorMessage(By locator, String messageText) {
        waitElementPresent(locator);
        implicitlyWait(10);
        if (getBrowser().equals(BROWSER_INTERNET_EXPLORER)) {
            implicitlyWait(10);
        }
        implicitlyWait(10);
        String actualMessageText =  getElementText(locator);
        isStringContains(messageText, actualMessageText, "Incorrect message text, expect: " + messageText
                + ", but found: " + actualMessageText);
        reporterLog("Show error message: " + messageText);
    }

    /**
     * Input Trainee first login credentials for login
     * @param log login
     * @param lastNameLetters password
     **/
    public void inputTraineeFirstLoginCredentials(String log, String lastNameLetters) {
        checkTraineeFirstLoginForm();
        inputLoginCredentials(traineeFirstLoginRecruitIDField, log, traineeFirstLoginPasswordField,
                lastNameLetters, traineeFirstLoginButton);
    }

    /**
     * Input Trainee login credentials for login
     * @param log login
     * @param pass password
     **/
    public void inputTraineeCredentials(String log, String pass) {
        checkTraineeLoginForm();
        inputLoginCredentials(traineeRecruitIDField, log, traineePasswordField, pass, traineeLoginButton);
    }
}
