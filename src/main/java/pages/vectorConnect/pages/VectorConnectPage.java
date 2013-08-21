package pages.vectorConnect.pages;

import fw.basic.ApplicationManager;
import fw.basic.helpers.BaseWebDriverHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import pages.staticdata.PageTitle;
import pages.vectorConnect.data.VectorConnectData;
import ru.yandex.qatools.htmlelements.element.Button;
import ru.yandex.qatools.htmlelements.element.TextInput;

/**
 * Created with IntelliJ IDEA.
 * User: alpa
 * Date: 24/05/13
 * Time: 18:05
 * To change this template use File | Settings | File Templates.
 */
public class VectorConnectPage extends BaseWebDriverHelper implements VectorConnectData {

    private final String LOGIN_PAGE = "/login/signin.htm";
    private final String US = "US";
    private final String CANADA = "Canada";

    /**Page WebElements**/
    @FindBy(xpath = CANADA_COUNTRY)
    private Button canada;

    @FindBy(xpath = US_COUNTRY)
    private Button us;

    @FindBy(xpath = VECTOR_CONNECT_BUTTON)
    private Button vectorConnectButton;

    @FindBy(xpath = SUBMIT_BUTTON)
    private Button submit;

    @FindBy(xpath = FORGOT_PASSWORD_LINK)
    private Button forgotPassword;

    @FindBy(id = REP_NUMBER_FIELD)
    private TextInput repNumber;

    @FindBy(id = VECTOR_CONNECT_PASSWORD)
    private TextInput vectorConnectPassword;

    @FindBy(xpath = REP_LOGIN_BUTTON)
    private Button repLoginButton;

    public VectorConnectPage() {
        super(ApplicationManager.getInstance());
    }

    public void goToVectorConnectPage() {
        String defaultServer = ApplicationManager.getServerSite();
        if (getCurrentUrl().equals(defaultServer + LOGIN_PAGE)) {
            if (getBrowser().equals(BROWSER_SAFARI)) {
                openUrl(defaultServer + LOGIN_PAGE);
            }
            clickOnVectorConnectButton();
            reporterLog("VectorConnect is current page");
            checkVectorConnectPage();
        } else {
            openUrl(defaultServer + LOGIN_PAGE);
            clickOnVectorConnectButton();
            reporterLog("Go to VectorConnect page");
            checkVectorConnectPage();
        }
    }

    /** Verify VectorConnect pages. */
    public void checkVectorConnectPage() {
        waitElementVisibility(By.id(REP_NUMBER_FIELD));
        assertVisibility(repNumber, "Rep Number field doesn't present");
        assertPageTitle(PageTitle.LOGIN);
        assertVisibility(vectorConnectPassword, "VectorConnect password field doesn't present");
        assertVisibility(repLoginButton, "Rep Login button doesn't present");
        reporterLog("Check VectorConnect pages");
    }

    /**
     * Switch country
     * @param country profile country (US, Canada)
     */
    public void switchCountry(String country) {
        if (country.equals(US)) {
            if (getSelectedCountry().equals(country)) {
                reporterLog(country + " country is selected");
            } else {
                clickOnButton(us);
            }
        } else if (country.equals(CANADA)) {
            if (getSelectedCountry().equals(country)) {
                reporterLog(country + " country is selected");
            } else {
                clickOnButton(canada);
            }
        }
    }

    /**
     * Input Rep credentials
     * @param log rep number
     * @param pass rep password
     */
    public void inputRepCredentials(String log, String pass) {
        inputText(repNumber, log);
        inputText(vectorConnectPassword, pass);
        clickOnButton(repLoginButton);
    }

    private String getSelectedCountry() {
        return getElementText(By.xpath(COUNTRY_SWITCHER + "//a[contains(@class, 'checked')]"));
    }

    public void clickOnVectorConnectButton() {
        clickOnButton(vectorConnectButton);
    }
}
