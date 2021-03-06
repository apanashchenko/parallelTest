package com.livetell;


import org.testng.annotations.Test;

/**
 * Created with IntelliJ IDEA.
 * User: alpa
 * Date: 11/02/13
 * Time: 14:01
 * To change this template use File | Settings | File Templates.
 */
public class TraineeLoginTests extends TestBase {

    @Test(enabled = true)
    public void Login_without_credentials() {
        app.getLoginHelper()
                .inputTraineeLoginCredentials("", "")
                .errorPleaseEnterYourRecruitId()
                .errorPasswordFieldIsRequired();
    }

    @Test(enabled = true)
    public void Input_recruit_ID_less_9_number() {
        app.getLoginHelper()
                .inputTraineeLoginCredentials("34534656", "1435")
                .errorPleaseEnterAtLeast9Characters();
    }

    @Test(enabled = true)
    public void Input_letters_in_recruit_ID_field() {
        app.getLoginHelper()
                .inputTraineeLoginCredentials("fgdfgdfg", "1435")
                .errorPleaseEnterOnlyDigits();
    }
}