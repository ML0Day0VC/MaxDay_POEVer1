/**
 * @author Max Day
 * Created At: 2023/04/05
 */

package API.Credentials;

import API.Table.TaskManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;

public class MainPage extends Thread {
    private static HashMap<String, Object> cache = new HashMap<>();
    private static boolean stopped = false;
    private static String currentUserFromCache = "";

    public MainPage() {
    }

    /**
     * Cache lets goo this is so badly made but its nice and funny so its fine
     * This is such a good way of doing this i think its the simplest but im rly not sure
     *
     * @param key
     * @return
     */
    public static Object value(String key) {
        return cache.computeIfAbsent(key, k -> retrieveValueFromSource(k));
    }

    private static Object retrieveValueFromSource(String key) {
        return key.toUpperCase();
    }

    public static void mainPage() {
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
            LoginManager lm = new LoginManager();
            System.out.println("Please type help for more information");
            while (!stopped) {
                String[] strInput = reader.readLine().split(" ");
                if (strInput.length <= 0) continue;
                switch (strInput[0].toLowerCase(Locale.ROOT)) {
                    case "?":
                    case "help": //TODO: add help for the table stuff im thinking i need to go from the login into the table immediately that seems like the most logical
                        System.out.println("""
                                > help - displays this page
                                > login - provides page for user login
                                > signup - allows the user to create a new account
                                > exit - exits the program""");
                        break;
                    case "table":
                        // if (!lm.returnLoginStatus()) {
                        //          System.err.println("User is not logged in. Please Sign in before continuing");
                        //           break;
                        //     }
                        currentUserFromCache = "max";
                        TaskManager taskManager = new TaskManager();
                        System.out.println("\tTable View:\n");
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
                                    taskManager.printTaskDetails(currentUserFromCache); //TODO: rename this to show report
                                    break;
                                case "add":
                                    String[] str = new String[6];
                                    System.out.println("Please prepare to enter the data for the new entry\nPlease enter the name of the task"); // 0
                                    str[0] = reader.readLine();
                                    System.out.println("Please enter the description of the task"); // 1
                                    str[1] = reader.readLine();
                                    System.out.println("Please enter the details of the developer of the task"); //2
                                    str[2] = reader.readLine();
                                    System.out.println("Please enter the task duration in hours");// 3
                                    str[3] = reader.readLine();
                                    System.out.println("Please enter the status of the task [1: To Do  2: Doing  3: Done]"); // 3
                                    str[4] = reader.readLine();
                                    taskManager.addItem(currentUserFromCache, str[0], str[1], str[2], Integer.parseInt(str[3]), Integer.parseInt(str[4]));
                                    break;
                                case "edit":
                                    System.out.println("Please prepare to enter the data for the edited entry");
                                    System.out.println("Please select the index of the entry you would like to edit");
                                    int var1 = Integer.parseInt(reader.readLine());
                                    System.out.println("""
                                            Please select the entry that you would like to change                                
                                                > 1 - Task Name
                                                > 2 - Description of Task
                                                > 3 - Developer Details 
                                                > 4 - Task Duration [Integer]
                                                > 5 - Task Status  [1: To Do  2: Doing  3: Done]
                                            Please enter a value between 1 and 5 to select what u want to edit""");
                                    int var2 = Integer.parseInt(reader.readLine());
                                    System.out.println("Please enter the replacement data");
                                    String var3 = reader.readLine();
                                    taskManager.edit(currentUserFromCache, var1, var2, var3);
                                    break;
                                case "remove":
                                case "delete":
                                    System.out.println("Please input the num of the entry you would like to remove");
                                    int var4 = Integer.parseInt(reader.readLine());
                                    System.out.println(String.format("ARE YOU SURE YOU WANT TO REMOVE ENTRY %d FROM THE TABLE?\n type  \"yes\" to confirm\n to back out type\"no\"", var4));
                                    if (reader.readLine().equalsIgnoreCase("yes"))
                                        taskManager.removeItem(currentUserFromCache, var4);
                                    break;
                                case "logout": //TODO: write this as quit i think
                                case "signout":
                                    System.out.println("User is now logged out");
                                    LoginManager.setIsSignedIn(false);
                                    isStoppedTable = true;
                                    break;
                                default:
                                    System.err.println("Unknown command. Run \"help\" for more info on commands");
                            }
                        }
                        break;
                    case "login":
                    case "signin":
                        if (lm.returnLoginStatus()) {
                            System.err.println("Another User is already signed in. Please Sign out before continuing");
                            break;
                        }
                        lm.loginUser();
                        Collection<Object> values = cache.values();
                        currentUserFromCache = values.toString().toLowerCase(Locale.ROOT).substring(1, values.toString().length() - 1); // extracting from cache

                        break;
                    case "signup":
                        /**
                         * TODO: i really wanna make something that prevents GIGO where poor data entry will lead to errors cause this is CLI.. and its annoying
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
                        if (!lm.checkUserName(uName)) {
                            System.out.println("Username is not correctly formatted, please ensure that your username contains an underscore and is no more than 5 characters in length\n\t Process has been canceled");
                            break;
                        }
                        System.out.println("Please Enter your password [ Please note the password must at least be 8 characters long, must contain a capital letter, a number and a special character ]");
                        String uPassword = lm.readMaskedPass();
                        if (lm.checkPasswordComplexity(uPassword)) {
                            DeepEncrypt.registerUser(uName, uPassword, fName, sName, dOB);
                            System.out.println("Password successfully captured\nPlease Sign in if you want to continue");
                        } else
                            System.out.println("Password is not correctly formatted, please ensure that the password contains at least 8 characters, a capital letter, a number and a special character\n\t Process has been canceled");
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
        System.out.println("\u001b[32m\n _    _      _                             _           _____  _     _____ _                 \n" + "| |  | |    | |                           | |         /  __ \\| |   |_   _| |                \n" + "| |  | | ___| | ___ ___  _ __ ___   ___   | |_ ___    | /  \\/| |     | | | |__   __ _ _ __  \n" + "| |/\\| |/ _ \\ |/ __/ _ \\| '_ ` _ \\ / _ \\  | __/ _ \\   | |    | |     | | | '_ \\ / _` | '_ \\ \n" + "\\  /\\  /  __/ | (_| (_) | | | | | |  __/  | || (_) |  | \\__/\\| |_____| |_| |_) | (_| | | | |\n" + " \\/  \\/ \\___|_|\\___\\___/|_| |_| |_|\\___|   \\__\\___/    \\____/\\_____/\\___/|_.__/ \\__,_|_| |_|\n" + "                                                                                            \n");
    }
}
