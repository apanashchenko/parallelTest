package pages.login.pages;

import fw.basic.ApplicationManager;
import fw.basic.helpers.BaseWebDriverHelper;
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
public class LoginPage extends BaseWebDriverHelper implements LoginDataProviders {

    private String firstTimeHereLink = TRAINEE_FIRS_LOGIN_FORM + FOLLOWING_SIBLING_P + ROLE_SWITCHER_LINK;
    private String alreadyHavePasswordLink = TRAINEE_LOGIN_FORM + FOLLOWING_SIBLING_P + ROLE_SWITCHER_LINK;

    /**Page WebElements**/
    @FindBy(xpath = TRAINEE_FIRS_LOGIN_FORM + FOLLOWING_SIBLING_DIV + TRAINEE_RECRUIT_ID_FIELD)
    private TextInput traineeFirstLoginRecruitIDField;

    @FindBy(xpath = TRAINEE_FIRS_LOGIN_FORM + FOLLOWING_SIBLING_DIV + TRAINEE_PASSWORD_FIELD)
    private TextInput traineeFirstLoginPasswordField;

    @FindBy(xpath = TRAINEE_FIRS_LOGIN_FORM + FOLLOWING_SIBLING_DIV + LOGIN_BUTTON)
    private Button traineeFirstLoginButton;

    @FindBy(xpath = ROLE_SWITCHER_LINK)
    private Button switchRoleLink;

    @FindBy(xpath = TRAINEE_LOGIN_FORM + FOLLOWING_SIBLING_DIV + TRAINEE_RECRUIT_ID_FIELD)
    private TextInput traineeRecruitIDField;

    @FindBy(xpath = TRAINEE_LOGIN_FORM + FOLLOWING_SIBLING_DIV + TRAINEE_PASSWORD_FIELD)
    private TextInput traineePasswordField;

    @FindBy(xpath = TRAINEE_LOGIN_FORM + FOLLOWING_SIBLING_DIV + LOGIN_BUTTON)
    private Button traineeLoginButton;

    @FindBy(xpath = VECTOR_CONNECT_BUTTON)
    private Button vectorConnectButton;

    @FindBy(xpath = SUBMIT_BUTTON)
    private Button submit;

    @FindBy(xpath = FORGOT_PASSWORD_LINK)
    private Button forgotPassword;

    @FindBy(id = REP_NUMBER_FIELD)
    private TextInput repNumber;

    @FindBy(id = VECTOR_CONNECT_PASSWORD)
    protected TextInput vectorConnectPassword;

    @FindBy(xpath = ACTIVE_LOGIN_FORM + LOGIN_BUTTON)
    private Button repLoginButton;

    @FindBy(xpath = LOG_OUT_BUTTON)
    private Button logOutButton;

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

    public LoginPage() {
        super(ApplicationManager.getInstance());
    }

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
                assertVisibility(logOutButton, "Log out button doesn't display");
                isStringsEquals(PageTitle.DASHBOARD.getTitleValue(), getPageTitle(),
                        "Wrong Trainee Dashboard pages title");
                reporterLog("Successfully login as: " + role);
                break;
            case MANAGER:
                waitElementPresent(By.xpath(LOG_OUT_BUTTON));
                assertVisibility(logOutButton, "Log out button doesn't display");
                isStringsEquals(PageTitle.DASHBOARD.getTitleValue(), getPageTitle(),
                        "Wrong Trainee Dashboard pages title");
                reporterLog("Successfully login as: " + role);
                break;
        }
    }

    /**
            * Input credentials for login
    * @param login login field TextInput
    * @param log login
    * @param password password field TextInput
    * @param pass password
    * @param loginButton Button
    **/
    protected void inputLoginCredentials(TextInput login, String log, TextInput password, String pass,
                                         Button loginButton) {
        if (getBrowser().equals(BROWSER_SAFARI)) {
            waitElementDuringSendKeys(log, login);
            waitElementDuringSendKeys(pass, password);
            clickOnButton(loginButton);
        } else {
            inputText(login, log);
            inputText(password, pass);
            clickOnButton(loginButton);
        }
        reporterLog("Input login credentials: " + log + "/" + password);
    }

    protected void checkTraineeLoginForm() {
        if (isElementVisible(By.xpath(alreadyHavePasswordLink)) && getElementText(By.xpath(alreadyHavePasswordLink))
                .equals("First time here? Need a password?")) {
        } else {
            clickOnButton(By.xpath(firstTimeHereLink));
            isStringsEquals("First time here? Need a password?", getElementText(By.xpath(alreadyHavePasswordLink)),
                    "Doesn't switch on trainee login");
        }
        reporterLog("Check Trainee login form");
    }

    protected void checkTraineeFirstLoginForm() {
        if (isElementVisible(By.xpath(firstTimeHereLink)) && getElementText(By.xpath(firstTimeHereLink))
                .equals("Already have a password?")) {
        } else {
            clickOnButton(By.xpath(alreadyHavePasswordLink));
            isStringsEquals("Already have a password?", getElementText(By.xpath(firstTimeHereLink)),
                    "Doesn't switch on trainee first login form");
        }
        reporterLog("Check Trainee first login form");
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
