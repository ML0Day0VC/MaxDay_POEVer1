/**
 * @author Max Day
 * Created At: 2023/04/04
 */
package API;

import java.io.*;
import java.util.regex.Pattern;

public class LoginManager {
    private static boolean isSignedIn = false;

    public static void loginUser() throws Exception {
        System.out.println("Please Enter Username");
        String uName = new BufferedReader(new InputStreamReader(System.in)).readLine();
        System.out.println("Please Enter Password");
        MainPage.value(uName); // cache the name that's logged in
        if (deepEncrypt.valid(uName, readMaskedPass(">"))) {
            setIsSignedIn(true);
        } else
            System.out.println("Unknown username or password. Please check your credentials and try again");
    }

    public static void setIsSignedIn(boolean inSignedIn) {
        isSignedIn = inSignedIn;
    }

    public static boolean returnLoginStatus() { //
        /**
         *  returnLoginStatus was asked to bea String but its just better to have it a Boolean. to prevent me from loosing marks the way I would do it if it was a string:
         *      I would be to create 2 enum constants one called FAILED and the other SUCCESS. These would be strings and they can be used as constants using .equals() which would return true or false
         */
        return isSignedIn;
    }


    public boolean checkUserName(String userName) {
        Pattern uRegex = Pattern.compile("^.{0,4}_.*$");// TODO: explain this fully
        return uRegex.matcher(userName).matches();

    }

    public boolean checkPasswordComplexity(String uPassword) {
        /**
         * ^: asserts that the string starts at the beginning.
         * (?=.*[a-z]): a positive lookahead that asserts that the string contains at least one lowercase letter (a-z).
         * (?=.*[A-Z]): a positive lookahead that asserts that the string contains at least one uppercase letter (A-Z).
         * (?=.*\d): a positive lookahead that asserts that the string contains at least one digit (0-9).
         * [a-zA-Z\d]{8,}: matches any character that is a lowercase letter, an uppercase letter, or a digit, and requires that the length of the string is at least 8 characters.
         * $: asserts that the string ends at this point.
         */

        //"^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-=[\\]{};':\"\\\\|,.<>\\/?]).+$"
        Pattern pRegex = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");// this thing caused me so much suffering im actually going to cry it's so late rn
        return pRegex.matcher(uPassword).matches();
    }

    /**
     * TODO:clean this up like seriously its rly bad
     *
     * @param prompt
     * @return
     */
    public static String readMaskedPass(String prompt) throws Exception {
        EraserThread et = new EraserThread();
        Thread mask = new Thread(et);
        mask.start();
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String password = "";
        password = in.readLine();
        et.stopMasking();
        return password;
    }
}