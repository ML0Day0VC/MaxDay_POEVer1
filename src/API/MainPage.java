/**
 * @author Max Day
 * Created At: 2023/04/05
 */
package API;


import API.Entities.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLOutput;
import java.util.Locale;


public class MainPage extends Thread {
    private static User user;

    private static boolean stopped = false;

    public MainPage() {

    }

    public static void mainPage() throws IOException {
        welcome();
        new MainPage();
        Thread t1 = new Thread();
        MainPage main = new MainPage();
        main.start();
        new Thread(t1).start();
    }

    public void start() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {

            /*    System.out.println("Starting...");
                System.out.println("Please State weather you want to login or register as a new user");
                String inLR = reader.readLine();
                if (!inLR.isEmpty()) {

                }
                System.out.println("Please enter the server port, the default is 8888, press Enter to end the input:");
                String port = reader.readLine();
                if (!port.isEmpty()) {
                    config.setServerPort(Integer.parseInt(port));
                }
                System.out.println("Please enter the file path for the database, the default is 'database.accdb', press Enter to end the input:");
                String databasePath = reader.readLine();
                if (!databasePath.isEmpty()) {
                    config.setDatabasePath(databasePath);
                }
                System.out.println("Configuration complete");
                config.setConfigured(true);
                config.save();
                return;
                */


            /**
             * options
             * login
             * signup
             * stop
             * display
             * signout
             *
             */


            /**
             * TODO: The best way todo this is to have 2 method layers one for logging in and stuff and then have another one embed into it so that it can be used without the random other tools like loging in when your already logged in.
             */





            LoginManager lm = new LoginManager();
            FileManager fm = new FileManager();
            System.out.println("Please type help for more information");
            while (!stopped) {
                String[] string = reader.readLine().split(" ");
                if (string.length <= 0) {
                    continue;
                }
                switch (string[0].toLowerCase(Locale.ROOT)) {
                    case "?":
                    case "help":
                        System.out.println("""
                                > help - displays this page
                                > display - shows the table 
                                > login - provides page for user login
                                > logout - logs out the current user
                                > signup - allows the user to create a new acoount
                                > exit - exits the program""");
                        break;
                    case "table":

                       TableManager tableManager = new TableManager();

                       tableManager.genTable("max");


                      // tableManager.removeItem("max",2);
                       // tableManager.genTable("max");

                       //     tableManager.addItem("max","this is a new task", "idk this could work its not clear at this point " , "22/33/2343",false);


                    //   tableManager.edit("max",1, 2, "new task descritmkawdl");


                      //  tableManager.genTable("max");








                        break;
                    case "login":
                        if (lm.getSignedIn()) {
                            System.err.println("Another API.Entities.User is already signed in. Please Sign out before continuing");
                            break;
                        }
                        lm.login();

                        break;
                    case "logout":
                        System.out.println("API.Entities.User is now logged out");
                        lm.setIsSignedIn(false);
                        break;
                    case "signup":

                        /**
                         * TODO: i really wanna make something that prevents idiots from entering blank values like if they hit enter before they have done there stuff idk
                         *  //  if (!fName.isEmpty());
                         */
                        System.out.println("Please Enter your first name");
                        String fName = reader.readLine();
                        System.out.println("Please Enter your surname");
                        String sName = reader.readLine();
                        System.out.println("Please Enter your date of birth  - FORMAT: DD/MM/YYYY ");
                        String dOB = reader.readLine();
                        System.out.println("Please Enter your username");
                        String uName = reader.readLine();
                        System.out.println("Please Enter your password");
                        String uPassword = lm.readMaskedPass(">");
                        deepEncrypt.genNewUser(uName, uPassword, fName, sName, dOB);
                        System.out.println("New API.Entities.User Created\nPlease Sign in if you want to continue");

                        break;
                    case "exit":

                        System.exit(420);
                        break;
                    default:
                        System.err.println("Unknown command. Run \"help\" for more info on commands");
                }
            }
            System.out.println("Stopped");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void welcome() {

        System.out.println("\u001b[32m\n _    _      _                             _           _____  _     _____ _                 \n" +
                "| |  | |    | |                           | |         /  __ \\| |   |_   _| |                \n" +
                "| |  | | ___| | ___ ___  _ __ ___   ___   | |_ ___    | /  \\/| |     | | | |__   __ _ _ __  \n" +
                "| |/\\| |/ _ \\ |/ __/ _ \\| '_ ` _ \\ / _ \\  | __/ _ \\   | |    | |     | | | '_ \\ / _` | '_ \\ \n" +
                "\\  /\\  /  __/ | (_| (_) | | | | | |  __/  | || (_) |  | \\__/\\| |_____| |_| |_) | (_| | | | |\n" +
                " \\/  \\/ \\___|_|\\___\\___/|_| |_| |_|\\___|   \\__\\___/    \\____/\\_____/\\___/|_.__/ \\__,_|_| |_|\n" +
                "                                                                                            \n" +
                "                                                                                            \n");

    }

}
