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
    DASHBOARD("Dashboard"),
    ;

    private String titleValue;

    private PageTitle(String titleValue) {
        this.titleValue = titleValue;
    }

    public String getTitleValue() {
        return titleValue;
    }
}
