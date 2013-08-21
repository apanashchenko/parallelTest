package pages.staticdata;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 6/3/13
 * Time: 9:07 PM
 * To change this template use File | Settings | File Templates.
 */
public enum ErrorMessages {

    INCORRECT_USER_NAME_OR_PASSWORD("That user name or password did not match our records. Please try again"),
    ENTER_YOUR_PASSWORD("Please enter your password"),
    ENTER_YOUR_RECRUIT_ID("Please enter your Recruit ID."),
    ENTER_YOUR_REP_NUMBER("Please enter your Rep Number."),
    ENTER_AT_LEAST_9_CHARACTERS("Please enter at least 9 characters."),
    ENTER_ONLY_DIGITS("Please enter only digits."),
    ;

    private String messageText;

    private ErrorMessages(String strValue){
        this.messageText = strValue;
    }

    public String getMessageText(){
        return messageText;
    }
}
