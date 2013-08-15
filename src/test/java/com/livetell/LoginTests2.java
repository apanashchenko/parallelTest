package com.livetell;

import org.testng.annotations.Test;

/**
 * Created with IntelliJ IDEA.
 * User: alpa
 * Date: 26/06/13
 * Time: 16:07
 * To change this template use File | Settings | File Templates.
 */
public class LoginTests2 extends TestBase {

    @Test(enabled = true)
    public void Login_with_incorrect_credentials() {
        app.getLoginHelper2()
                .inputTraineeLoginCredentials("123564872", "798411")
                .errorThatUserNameOrPasswordDidNotMatchRecords();
    }

    @Test(enabled = true)
    public void Login_only_with_login_credential() {
        app.getLoginHelper2()
                .inputTraineeLoginCredentials("798418623", "")
                .errorPasswordFieldIsRequired();
    }

    @Test(enabled = true)
    public void Login_only_with_password_credential() {
        app.getLoginHelper2()
                .inputTraineeLoginCredentials("", "135698456")
                .errorPleaseEnterYourRecruitId();
    }
}
