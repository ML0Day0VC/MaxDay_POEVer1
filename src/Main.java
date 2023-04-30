/**
 * @author Max Day
 * Created At: 2023/04/15
 */

import API.Credentials.MainPage;

public class Main {

    /**
     * Main method. I typically don't like to put a lot of code into the main method so this is just to start the thread and catch any errors that occur
     *
     * @param args
     */
    public static void main(String[] args) {

        try {
            MainPage mainPage = new MainPage();
            mainPage.mainPage();
        } catch (Exception e) {
            /* This is only here for the thread if it dies */
            throw new RuntimeException(e);
        }

    }
}