package pages.login.data;

import fw.basic.data.BaseDataProvider;

/**
 * Created with IntelliJ IDEA.
 * User: alpa
 * Date: 24/01/13
 * Time: 10:40
 * To change this template use File | Settings | File Templates.
 */
public interface LoginDataProviders extends BaseDataProvider {

    String ALREADY_HAVE_PASSWORD = "Already have a password?";
    String FIRST_TIME_HERE = "First time here? Need a password?";
    String ERROR_PASSWORD_FIELD = "//label[@class='error'][@for='trainee-login-name']";
    String ERROR_RECRUIT_ID_FIELD = "//label[@class='error'][@for='trainee-login-id']";
    String firstTimeHereLink = TRAINEE_FIRS_LOGIN_FORM + FOLLOWING_SIBLING_P + ROLE_SWITCHER_LINK;
    String alreadyHavePasswordLink = TRAINEE_LOGIN_FORM + FOLLOWING_SIBLING_P + ROLE_SWITCHER_LINK;
    String RECRUIT_ID_FIELD = TRAINEE_LOGIN_FORM + FOLLOWING_SIBLING_DIV + TRAINEE_RECRUIT_ID_FIELD;
    String TRAINEE_PASSWORD_FIELD = TRAINEE_LOGIN_FORM + FOLLOWING_SIBLING_DIV + TRAINEE_LOGIN_NAME_FIELD;
    String TRAINEE_LOGIN_BUTTON = TRAINEE_LOGIN_FORM + FOLLOWING_SIBLING_DIV + LOGIN_BUTTON;

}
