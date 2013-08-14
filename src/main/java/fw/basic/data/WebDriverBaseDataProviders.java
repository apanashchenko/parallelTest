package fw.basic.data;

/**
 * Created with IntelliJ IDEA.
 * User: alpa
 * Date: 29.11.12
 * Time: 11:53
 * To change this template use File | Settings | File Templates.
 */
public interface WebDriverBaseDataProviders {

    String SUBMIT_BUTTON = "//input[@type='submit']";
    String FOLLOWING_SIBLING_DIV = "/following-sibling::div";
    String FOLLOWING_SIBLING_P = "/following-sibling::p";
    String FOLLOWING_SIBLING = "/following-sibling::";
    String FOLLOWING_SIBLING_SPAN = "/following-sibling::span";
    String TRAINEE_FIRS_LOGIN_FORM = "//input[@value='NEW']";
    String TRAINEE_LOGIN_FORM = "//input[@value='TRAINEE']";
    String ROLE_SWITCHER_LINK = "//span[@class='link_text login_toggler']";
    String VECTOR_CONNECT_BUTTON = "//a[text()='VectorConnect']";
    String ACTIVE_LOGIN_FORM = "//div[@class='login_form']";
    String LOGIN_BUTTON = "//input[@value='Login']";
    String TRAINEE_RECRUIT_ID_FIELD = "//input[@id='trainee-login-id']";
    String TRAINEE_PASSWORD_FIELD = "//input[@id='trainee-login-name']";
    String FORGOT_PASSWORD_LINK = "//p[@class='text-center']/a";
    String REP_NUMBER_FIELD = "rep-number";
    String VECTOR_CONNECT_PASSWORD = "vectorconnect-password";
    String LABEL_ERROR = "label[@class='error']";
    String SERVER_ERROR_MESSAGE = "//div[@class='inner']";
    String NAVIGATION_PANEL = "nav";
    String J_USER_NAME = "j_username";
    String LOG_OUT_BUTTON = "//a[@class='login-toggler']";
}
