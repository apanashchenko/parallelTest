package pages.login2;

import fw.basic.helpers.BaseHelper;
import org.openqa.selenium.By;
import pages.staticdata.ErrorMessages;
import pages.login2.data.LoginDataProviders;
import pages.login2.data.Role;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 19.01.13
 * Time: 21:33
 * To change this template use File | Settings | File Templates.
 */
public class LoginHelper2 extends BaseHelper implements LoginDataProviders {

    private LoginPage2 loginPage2;

    private LoginPage2 onLoginPage() {
        if (loginPage2 == null) {
            return new LoginPage2();
        }
        return loginPage2;
    }

    public LoginHelper2 checkLoginPage() {
        onLoginPage().checkLoginPage();
        return this;
    }

    public LoginHelper2 selectRole(Role role) {
        onLoginPage().selectRole(role);
        return this;
    }

    public LoginHelper2 assertThatUserWithSelectRoleLogin(Role role) {
        onLoginPage().assertThatUserWithSelectRoleLogin(role);
        return this;
    }

    public LoginHelper2 errorThatUserNameOrPasswordDidNotMatchRecords() {
        onLoginPage().checkErrorMessage(By.xpath(SERVER_ERROR_MESSAGE),
                ErrorMessages.INCORRECT_USER_NAME_OR_PASSWORD.getMessageText());
        return this;
    }

    public LoginHelper2 errorPasswordFieldIsRequired() {
        onLoginPage().checkErrorMessage(By.xpath(ERROR_PASSWORD_FIELD),
                ErrorMessages.ENTER_YOUR_PASSWORD.getMessageText());
        return this;
    }

    public LoginHelper2 errorPleaseEnterYourRecruitId() {
        onLoginPage().checkErrorMessage(By.xpath(ERROR_RECRUIT_ID_FIELD),
                ErrorMessages.ENTER_YOUR_RECRUIT_ID.getMessageText());
        return this;
    }

    public LoginHelper2 errorPleaseEnterAtLeast9Characters() {
        onLoginPage().checkErrorMessage(By.xpath(ERROR_RECRUIT_ID_FIELD),
                ErrorMessages.ENTER_AT_LEAST_9_CHARACTERS.getMessageText());
        return this;
    }

    public LoginHelper2 errorPleaseEnterOnlyDigits() {
        onLoginPage().checkErrorMessage(By.xpath(ERROR_RECRUIT_ID_FIELD),
                ErrorMessages.ENTER_ONLY_DIGITS.getMessageText());
        return this;
    }

    public LoginHelper2 errorPleaseEnterFirst3LettersOfYourLastName() {
        onLoginPage().checkErrorMessage(By.xpath(TRAINEE_FIRS_LOGIN_FORM + FOLLOWING_SIBLING_DIV + ERROR_PASSWORD_FIELD),
                ErrorMessages.ENTER_THE_FIRST_3_LETTERS_OF_LAST_NAME.getMessageText());
        return this;
    }

    public LoginHelper2 inputTraineeFirstLoginCredentials(String log, String pass) {
        onLoginPage().inputTraineeFirstLoginCredentials(log, pass);
        return this;
    }

    public LoginHelper2 inputTraineeLoginCredentials(String log, String pass) {
        onLoginPage().inputTraineeCredentials(log, pass);
        return this;
    }

    public LoginHelper2 errorLoginWithRecruitIdAndPassword() {
        onLoginPage().checkErrorMessage(By.xpath(SERVER_ERROR_MESSAGE),
                ErrorMessages.LOGIN_WITH_RECRUIT_ID_AND_PASSWORD.getMessageText());
        return this;
    }
}
