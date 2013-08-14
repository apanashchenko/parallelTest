package fw.basic.helpers;

import fw.basic.ApplicationManager;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.slf4j.LoggerFactory;
import pages.staticdata.PageTitle;
import ru.yandex.qatools.htmlelements.element.Button;
import ru.yandex.qatools.htmlelements.element.TextInput;

import java.util.Random;


/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 19.01.13
 * Time: 21:53
 * To change this template use File | Settings | File Templates.
 */
/**
 * Parent class for general methods in all project. All main pages should extends this class!
 **/
public class BaseHelper extends BaseWebDriverHelper {

    private org.slf4j.Logger LOG = LoggerFactory.getLogger(BaseHelper.class);
    protected String firstTimeHereLink = TRAINEE_FIRS_LOGIN_FORM + FOLLOWING_SIBLING_P + ROLE_SWITCHER_LINK;
    protected String alreadyHavePasswordLink = TRAINEE_LOGIN_FORM + FOLLOWING_SIBLING_P + ROLE_SWITCHER_LINK;
    private Random rnd = new Random();


    /**
     * PageFactory HTML elements for creating WebElement
     **/
    @FindBy(xpath = TRAINEE_FIRS_LOGIN_FORM + FOLLOWING_SIBLING_DIV + TRAINEE_RECRUIT_ID_FIELD)
    protected TextInput traineeFirstLoginRecruitIDField;

    @FindBy(xpath = TRAINEE_FIRS_LOGIN_FORM + FOLLOWING_SIBLING_DIV + TRAINEE_PASSWORD_FIELD)
    protected TextInput traineeFirstLoginPasswordField;

    @FindBy(xpath = TRAINEE_FIRS_LOGIN_FORM + FOLLOWING_SIBLING_DIV + LOGIN_BUTTON)
    protected Button traineeFirstLoginButton;

    @FindBy(xpath = ROLE_SWITCHER_LINK)
    protected Button switchRoleLink;

    @FindBy(xpath = TRAINEE_LOGIN_FORM + FOLLOWING_SIBLING_DIV + TRAINEE_RECRUIT_ID_FIELD)
    protected TextInput traineeRecruitIDField;

    @FindBy(xpath = TRAINEE_LOGIN_FORM + FOLLOWING_SIBLING_DIV + TRAINEE_PASSWORD_FIELD)
    protected TextInput traineePasswordField;

    @FindBy(xpath = TRAINEE_LOGIN_FORM + FOLLOWING_SIBLING_DIV + LOGIN_BUTTON)
    protected Button traineeLoginButton;

    @FindBy(xpath = VECTOR_CONNECT_BUTTON)
    protected Button vectorConnectButton;

    @FindBy(xpath = SUBMIT_BUTTON)
    protected Button submit;

    @FindBy(xpath = FORGOT_PASSWORD_LINK)
    protected Button forgotPassword;

    @FindBy(id = REP_NUMBER_FIELD)
    protected TextInput repNumber;

    @FindBy(id = VECTOR_CONNECT_PASSWORD)
    protected TextInput vectorConnectPassword;

    @FindBy(xpath = ACTIVE_LOGIN_FORM + LOGIN_BUTTON)
    protected Button repLoginButton;

    @FindBy(xpath = LOG_OUT_BUTTON)
    protected Button logOutButton;

    public BaseHelper() {
        super(ApplicationManager.getInstance());
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

    protected void assertPageTitle(PageTitle expectedPageTitle) {
        String titleValue = expectedPageTitle.getTitleValue();
        String currentTitle = getPageTitle();
        isStringsEquals(titleValue, currentTitle, "Should be on page " + titleValue +
                " but I am actually on page " + currentTitle);
    }

    protected Random getRandom() {
        return rnd;
    }
}
