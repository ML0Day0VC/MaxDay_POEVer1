/**
 * @author Max Day
 * Created At: 2023/04/04
 */
package API;

import java.io.*;

/**
 * TODO: I CANT USE THE FCING COORS OF THE CONSOLE WHEN I AM USING THE FUCKING CONSOLE AND I HAVE TO FOR THE INTELLJ BUG OMGGGG
 */
public class LoginManager {
    private static boolean isSignedIn = false;

    public static void login() throws Exception {
        System.out.println("Please Enter Username");
        String uName = new BufferedReader(new InputStreamReader(System.in)).readLine();
        System.out.println("Please Enter Password");
        MainPage.value(uName); // cache the name that's logged in lol
        if (deepEncrypt.valid(uName, readMaskedPass(">"))) {

            setIsSignedIn(true);
        }else
            System.out.println("Unknown username or password. Please check your credentials and try again");
    }

    public static void setIsSignedIn(boolean inSignedIn) {
        isSignedIn = inSignedIn;
    }

    public static boolean getSignedIn() {
        return isSignedIn;
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
        return password; //TODo; need to adda validate password for the criteria sobbing face
    }
}
/*

    public static String testmaskPassword() throws Exception {
        System.out.print("Enter your password: ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String password = "";
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                char[] chars = line.toCharArray();
                for (int i = 0; i < chars.length; i++) {
                    char c = chars[i];
                    if (c == '\r' || c == '\n') {
                        break;
                    } else if (c == '\b' && password.length() > 0) {
                        password = password.substring(0, password.length() - 1);
                        System.out.print("\b \b");
                    } else {
                        password += c;
                        System.out.print("*");
                    }
                }
                if (password.length() > 0) {
                    break;
                }
            }
            System.out.println("\nPassword entered: " + password);
        } finally {
            password = null;
        }

   return password;
    }



 */