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
    REQUIRED_FIELD("This field is required."),
    ENTER_YOUR_PASSWORD("Please enter your password"),
    ENTER_YOUR_RECRUIT_ID("Please enter your Recruit ID."),
    ENTER_AT_LEAST_9_CHARACTERS("Please enter at least 9 characters."),
    ENTER_ONLY_DIGITS("Please enter only digits."),
    ENTER_THE_FIRST_3_LETTERS_OF_LAST_NAME("Please enter the first 3 letters of your last name."),
    LOGIN_WITH_RECRUIT_ID_AND_PASSWORD("You already have a password. Please login with your recruitId and your password."),
    CANNOT_CONECT_TO_SERVER("Error: Cannot connect to server; please try again in a few minutes"),
    ;

    private String messageText;

    private ErrorMessages(String strValue){
        this.messageText = strValue;
    }

    public String getMessageText(){
        return messageText;
    }
}
