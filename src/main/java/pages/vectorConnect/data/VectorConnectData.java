package pages.vectorConnect.data;

import fw.basic.data.BaseDataProvider;

/**
 * Created with IntelliJ IDEA.
 * User: alpa
 * Date: 24/05/13
 * Time: 18:19
 * To change this template use File | Settings | File Templates.
 */
public interface VectorConnectData extends BaseDataProvider {

    String COUNTRY_SWITCHER = "//div[@class='toggler country_toggler']";
    String US = "/a[text()='US']";
    String CANADA = "/a[text()='Canada']";
    String ERROR_REP_NUMBER = "//label[@class='error'][@for='rep-number']";
    String REP_LOGIN_BUTTON = ACTIVE_LOGIN_FORM + LOGIN_BUTTON;
    String US_COUNTRY = COUNTRY_SWITCHER + US;
    String CANADA_COUNTRY = COUNTRY_SWITCHER + CANADA;

}
