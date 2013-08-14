package pages.staticdata;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 6/24/13
 * Time: 9:36 PM
 * To change this template use File | Settings | File Templates.
 */
public enum PageTitle {

    LOGIN("Login"),
    FIRST_LOGIN("Setup profile"),
    LEADS_LIST("Leads List"),
    LEAD_RECORD("Lead record"),
    SOURCE_RECORD("Source Record"),
    SORT_YOUR_LIST("Sort Your List"),
    BUILD_YOUR_LIST("Build Your List"),
    VERIFY_INFO("Verify info"),
    MANUAL_ADD_LEADS("Manual add Leads"),
    ADD_SOURCE("Add Source"),
    SOURCE_LIST("Source List"),
    DASHBOARD("Dashboard"),
    CALL_LIST("Call List"),
    ;

    private String titleValue;

    private PageTitle(String titleValue) {
        this.titleValue = titleValue;
    }

    public String getTitleValue() {
        return titleValue;
    }
}
