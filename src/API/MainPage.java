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
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
                    case "help": //TODO: add help for the table stuff im thinking i need to go from the login into the table immediately that seems like the most logical
                        System.out.println("""
                                > help - displays this page
                                > login - provides page for user login
                                > signup - allows the user to create a new account
                                > exit - exits the program""");
                        break;
                    case "table":

                        //TODO:check if user is logged in
                        TableManager tableManager = new TableManager();
                        System.out.println("Table View:\n\n");
                        String testName = "max";
                        boolean isStoppedTable = false;
                        while (!isStoppedTable) {
                            switch (reader.readLine()) {
                                case "?":
                                case "help":
                                    System.out.println("""
                                            > help - displays this page
                                            > display - shows the updated table
                                            > add - adds info to the table
                                            > edit - edits the table 
                                            > remove - removes a line from the table
                                            > logout - logs out the user""");
                                    break;
                                case "display":
                                    tableManager.genTable(testName);
                                    break;
                                case "add":
                                    String[] str = new String[4];
                                    System.out.println("Please prepare to enter the data for the new entry");
                                    System.out.println("Please enter the name of the task");
                                    str[0] = reader.readLine();
                                    System.out.println("Please enter the description of the task");
                                    str[1] = reader.readLine();
                                    System.out.println("Please enter the date of the task [Format : dd/mm/yyyy]");
                                    str[2] = reader.readLine();
                                    System.out.println("Please enter the state of the task if it has been completed [true | false]");
                                    str[3] = reader.readLine();
                                    tableManager.addItem(testName, str[0], str[1], str[2], str[3].equalsIgnoreCase("true"));
                                    break;
                                case "edit":
                                    System.out.println("Please prepare to enter the data for the edited entry");
                                    System.out.println("Please select the index of the entry you would like to edit");
                                    int var1 = Integer.parseInt(reader.readLine());
                                    System.out.println("""
                                            Please select the entry that you would like to change
                                                                                        
                                            > Name of Task: 1
                                            > Description of Task: 2
                                            > Date of the Task: 3
                                            > Task Completion: 4
                                                    Please enter a value between 1 and 4 to select what u want to edit""");
                                    int var2 = Integer.parseInt(reader.readLine());
                                    System.out.println("Please enter the new data");
                                    String var3 = reader.readLine();
                                    tableManager.edit(testName, var1, var2, var3);
                                    break;
                                case "remove":
                                    System.out.println("Please input the num of the entry you would like to remove");
                                    int var4 = Integer.parseInt(reader.readLine());
                                    System.out.println(String.format("ARE YOU SURE YOU WANT TO REMOVE ENTRY %d FROM THE TABLE?\n type  \"yes\" to confirm\n to back out type\"no\"", var4));
                                    if (reader.readLine().equalsIgnoreCase("yes"))
                                        tableManager.removeItem(testName, var4);
                                    break;
                                case "logout":
                                    System.out.println("API.Entities.User is now logged out");
                                    lm.setIsSignedIn(false);
                                    isStoppedTable = true;
                                    break;
                                default:
                                    //TODO add info to default
                            }
                        }

                    case "login":
                        if (lm.getSignedIn()) {
                            System.err.println("Another API.Entities.User is already signed in. Please Sign out before continuing");
                            break;
                        }
                        lm.login();

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
                        System.out.println("Please Enter your password [ Please note the password must at least be 8 characters long, must contain a capital letter, a number and a special character ]");
                        String uPassword = lm.readMaskedPass(">");

                        /**
                         * ^: asserts that the string starts at the beginning.
                         * (?=.*[a-z]): a positive lookahead that asserts that the string contains at least one lowercase letter (a-z).
                         * (?=.*[A-Z]): a positive lookahead that asserts that the string contains at least one uppercase letter (A-Z).
                         * (?=.*\d): a positive lookahead that asserts that the string contains at least one digit (0-9).
                         * [a-zA-Z\d]{8,}: matches any character that is a lowercase letter, an uppercase letter, or a digit, and requires that the length of the string is at least 8 characters.
                         * $: asserts that the string ends at this point.
                         */
                        //"^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-=[\\]{};':\"\\\\|,.<>\\/?]).+$"
                        Pattern pRegex = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$");
                        if (pRegex.matcher(uPassword).matches()) {
                            //password is valid
                            deepEncrypt.genNewUser(uName, uPassword, fName, sName, dOB);
                            System.out.println("New API.Entities.User Created\nPlease Sign in if you want to continue");
                        } else
                            System.out.println("The password does not contain the required characters and numbers\n\t Process has been canceled");
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
