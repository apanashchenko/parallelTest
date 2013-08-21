package pages.login;

import fw.basic.ApplicationManager;
import fw.basic.data.BaseDataProvider;
import fw.basic.helpers.BaseWebDriverHelper;
import org.openqa.selenium.By;
import pages.login.data.LoginDataProviders;
import pages.login.data.Role;
import pages.login.pages.LoginPage;
import pages.staticdata.ErrorMessages;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 19.01.13
 * Time: 21:33
 * To change this template use File | Settings | File Templates.
 */
public class LoginHelper implements LoginDataProviders {

    private LoginPage loginPage;

    private LoginPage onLoginPage() {
        if (loginPage == null) {
            return new LoginPage();
        }
        return loginPage;
    }

    public LoginHelper checkLoginPage() {
        onLoginPage().checkLoginPage();
        return this;
    }

    public LoginHelper errorPasswordFieldIsRequired() {
        onLoginPage().checkErrorMessage(By.xpath(ERROR_PASSWORD_FIELD),
                ErrorMessages.ENTER_YOUR_PASSWORD.getMessageText());
        return this;
    }

    public LoginHelper errorPleaseEnterYourRecruitId() {
        onLoginPage().checkErrorMessage(By.xpath(ERROR_RECRUIT_ID_FIELD),
                ErrorMessages.ENTER_YOUR_RECRUIT_ID.getMessageText());
        return this;
    }

    public LoginHelper errorPleaseEnterAtLeast9Characters() {
        onLoginPage().checkErrorMessage(By.xpath(ERROR_RECRUIT_ID_FIELD),
                ErrorMessages.ENTER_AT_LEAST_9_CHARACTERS.getMessageText());
        return this;
    }

    public LoginHelper errorPleaseEnterOnlyDigits() {
        onLoginPage().checkErrorMessage(By.xpath(ERROR_RECRUIT_ID_FIELD),
                ErrorMessages.ENTER_ONLY_DIGITS.getMessageText());
        return this;
    }

    public LoginHelper inputTraineeLoginCredentials(String log, String pass) {
        onLoginPage().inputTraineeCredentials(log, pass);
        return this;
    }
}
