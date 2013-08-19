package com.livetell;

import org.testng.annotations.Test;

/**
 * Created with IntelliJ IDEA.
 * User: alpa
 * Date: 26/06/13
 * Time: 16:07
 * To change this template use File | Settings | File Templates.
 */
public class RepLoginTests extends TestBase {

    @Test(enabled = true)
    public void Login_with_incorrect_Rep_credentials() {
        app.getVectorConnectHelper()
                .goToVectorConnectPage()
                .inputRepCredentials("123564872", "7498456")
                .errorPasswordFieldIsRequired();
    }

    @Test(enabled = true)
    public void Login_only_with_Rep_number_credential() {
        app.getVectorConnectHelper()
                .goToVectorConnectPage()
                .inputRepCredentials("12365489", "")
                .errorPleaseEnterAtLeast9Characters();
    }

    @Test(enabled = true)
    public void Login_only_with_Rep_password_credential() {
        app.getVectorConnectHelper()
                .goToVectorConnectPage()
                .inputRepCredentials("", "135698456")
                .errorPleaseEnterYourRepNumber();
    }
}
