package pages.vectorConnect;

import fw.basic.data.BaseDataProvider;
import org.openqa.selenium.By;
import pages.staticdata.ErrorMessages;
import pages.vectorConnect.data.VectorConnectData;
import pages.vectorConnect.pages.VectorConnectPage;

/**
 * Created with IntelliJ IDEA.
 * User: alpa
 * Date: 24/05/13
 * Time: 18:06
 * To change this template use File | Settings | File Templates.
 */
public class VectorConnectHelper implements VectorConnectData {

    private VectorConnectPage vectorConnectPage;

    public VectorConnectHelper goToVectorConnectPage() {
        onVectorConnectPage().goToVectorConnectPage();
        return new VectorConnectHelper();
    }

    private VectorConnectPage onVectorConnectPage() {
        if (vectorConnectPage == null) {
            return new VectorConnectPage();
        }
        return vectorConnectPage;
    }

    public VectorConnectHelper checkVectorConnectPage() {
        onVectorConnectPage().checkVectorConnectPage();
        return this;
    }

    public VectorConnectHelper switchCountry(String country) {
        onVectorConnectPage().switchCountry(country);
        return this;
    }

    public VectorConnectHelper inputRepCredentials(String log, String pass) {
        onVectorConnectPage().inputRepCredentials(log, pass);
        return this;
    }

    public VectorConnectHelper errorPasswordFieldIsRequired() {
        onVectorConnectPage().checkErrorMessage(By.xpath(SERVER_ERROR_MESSAGE),
                ErrorMessages.INCORRECT_USER_NAME_OR_PASSWORD.getMessageText());
        return this;
    }

    public VectorConnectHelper errorPleaseEnterAtLeast9Characters() {
        onVectorConnectPage().checkErrorMessage(By.xpath(ERROR_REP_NUMBER),
                ErrorMessages.ENTER_AT_LEAST_9_CHARACTERS.getMessageText());
        return this;
    }

    public VectorConnectHelper errorPleaseEnterYourRepNumber() {
        onVectorConnectPage().checkErrorMessage(By.xpath(ERROR_REP_NUMBER),
                ErrorMessages.ENTER_YOUR_REP_NUMBER.getMessageText());

        return this;
    }

}
