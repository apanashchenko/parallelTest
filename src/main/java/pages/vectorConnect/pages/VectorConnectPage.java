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

    private final String US_COUNTRY = "US";
    private final String CANADA_COUNTRY = "Canada";

    /**Page WebElements**/
    @FindBy(xpath = COUNTRY_SWITCHER + US)
    private Button canada;

    @FindBy(xpath = COUNTRY_SWITCHER + CANADA)
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

    @FindBy(xpath = ACTIVE_LOGIN_FORM + LOGIN_BUTTON)
    private Button repLoginButton;

    public VectorConnectPage() {
        super(ApplicationManager.getInstance());
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
        if (country.equals(US_COUNTRY)) {
            if (getSelectedCountry().equals(country)) {
                reporterLog(country + " country is selected");
            } else {
                clickOnButton(us);
            }
        } else if (country.equals(CANADA_COUNTRY)) {
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
