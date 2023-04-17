import API.Credentials.MainPage;

public class Main {
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