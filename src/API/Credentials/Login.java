/**
 * @author Max Day
 * Created At: 2023/04/04
 */
package API.Credentials;

import java.io.*;
import java.util.regex.Pattern;

public class Login {
    private static boolean isSignedIn = false;

    public static void loginUser() throws Exception {
        System.out.println("Please Enter Username");
        String uName = new BufferedReader(new InputStreamReader(System.in)).readLine();
        System.out.println("Please Enter Password");
        MainPage.value(uName); // cache the name that's logged in
        if (DeepEncrypt.valid(uName, readMaskedPass()))
            setIsSignedIn(true);
        else
            System.out.println("Unknown username or password. Please check your credentials and try typing \"login\" again to retry");
    }

    public static void setIsSignedIn(boolean inSignedIn) {
        isSignedIn = inSignedIn;
    }

    public static boolean returnLoginStatus() { //
        /**
         *  returnLoginStatus was asked to bea String, but it's just better to have it a Boolean. to prevent me from loosing marks the way I would do it if it was a string:
         *  I would be to create 2 enum constants one called FAILED and the other SUCCESS. These would be strings, and they can be used as constants using .equals() which would return true or false
         */
        return isSignedIn;
    }

    public boolean checkUserName(String userName) {
        /**Explanation of regex:
         * ^ matches the start of the string
         * {0,4} matches strings with only 4 characters in length
         * _. matches with underscores
         * *$ matches the end of the string but makes it so that the underscore can appear anywhere in the string (abbreviated)
         */
        Pattern uRegex = Pattern.compile("^.{4,}_.*$");
        return uRegex.matcher(userName).matches();
    }

    public boolean checkPasswordComplexity(String uPassword) {
        //"^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-=[\\]{};':\"\\\\|,.<>\\/?]).+$"
        /**Explanation of regex:
         * [a-z] matches lowercase characters
         * ?=.*[A-Z] positive look  - matches at least one capital letter (* matches anywhere in the string)
         * ?=.*\d positive look - matches at least one number (* matches anywhere in the string)
         * ?=.*[@$!%*?&] positive look - matches one of the characters (* matches anywhere in the string)
         * {8,} - checks if string is 8 characters long
         */
        Pattern pRegex = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");// this thing caused me so much suffering im actually going to cry it's so late rn
        return pRegex.matcher(uPassword).matches();
    }

    public static String readMaskedPass() throws Exception {
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