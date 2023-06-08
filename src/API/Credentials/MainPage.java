/**
 * @author Max Day
 * Created At: 2023/04/05
 */

package API.Credentials;

import API.Table.TaskManager;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;

public class MainPage extends Thread {
    private static HashMap<String, Object> cache = new HashMap<>(); // cache hashmap
    private static final boolean stopped = false;

    public MainPage() {
    }

    public static Object value(String key) {
        return cache.computeIfAbsent(key, k -> retrieveValueFromSource(k));
    }
    // Caching value, so I don't need to carry object over all the threads and methods. I can legit pull it from everywhere I love it

    private static Object retrieveValueFromSource(String key) {
        return key.toUpperCase();
    }

    //thread method to create a CLI input, so we can constantly type in the console to input data commands ect....
    public static void mainPage() {
        welcome();
        new MainPage();
        Thread t1 = new Thread();
        MainPage main = new MainPage();
        main.start();
        new Thread(t1).start();
    }

    /**
     * This is the main interface control section. This works by constantly running in a while(true) loop. The loop constantly runs take in users keystrokes and creates and interpret event when new line is entered
     * This means users can enter command line like commands to the terminal to register commands and inputs. This idea ive used multiple times when making back end terminals that need commands to be entered while a program is running on a seperate thread in the background
     */
    public void start() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            Login lm = new Login();
            System.out.println("Please type \"help\" for more information");
            while (true) {// better than !stopped
                String[] strInput = reader.readLine().split(" ");
                if (strInput.length <= 0) continue;// if the line is greater than 0 then proceed with check
                switch (strInput[0].toLowerCase(Locale.ROOT)) {
                    case "?", "help" -> System.out.println("""
                            > help - Displays info about the commands that can be run
                            > login - Provides page for user login
                            > signup - Allows the user to create a new account
                            > exit - Exits the program""");

                    case "login", "signin" -> { //logs the user in and checks and validates there credentials
                        if (Login.returnLoginStatus()) {
                            System.err.println("Another User is already signed in. Please Sign out before continuing");
                            break;
                        }
                        Login.loginUser();
                        if (!Login.returnLoginStatus()) break;
                        Collection<Object> values = cache.values(); // reads the username of the user from the cache. Note this is just a standard hashmap declared at the top of the class
                        String currentUserFromCache = values.toString().toLowerCase(Locale.ROOT).substring(1, values.toString().length() - 1); // extracting from cache
                        Object[] options = {"Manage Tasks", "Display", "Quit"};
                        // I cannot get the formatting of the "task Manager" correct i think its just a bug with stupid Jframes
                        String text = "<html><b>Please Select One of the Options:</b><br><br>\n" + "    \n" + "&nbsp<b>Task Manager:</b><li>   Add Tasks<li>  Remove Tasks<li>  Edit Tasks<li>  Display and Mange Developers<br><br>\n" + "\n" + "<b>Display:</b><li>   View the Completed Report<li>  View Total Hours<br><br>\n" + "\n" + "<b>Quit:</b><li>   Quits the Program<li>  Logs out the current user\n" + "\n" + "\n" + "</html>";
                        JLabel label = new JLabel(text);
                        label.setFont(new Font("serif", Font.PLAIN, 14));
                        TaskManager taskManager = new TaskManager();
                        boolean isStoppedTable = false;
                        while (!isStoppedTable) {
                            int result = JOptionPane.showOptionDialog(null, label, "Please select one of the options", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
                            switch (result) {
                                case 0 ->
                                        System.out.println("Opening report view"); // this will just break out to the following method so it works lol
                                case 1 -> TaskManager.printTaskDetails(currentUserFromCache); // displays the report
                                case 2 -> System.exit(420); // exits the program
                            }
                            // TaskManager taskManager = new TaskManager();
                            //   System.out.println("\n\tReport View:\n\tPlease type \"help\" for more information");
                            //    boolean isStoppedTable = false;
                            //     while (!isStoppedTable) { TODO: please redo this as it is very very inefficient and poorly made. Yes it works but the GUI is such an inconvenience
                            switch (reader.readLine()) { //new help as there is now more commands as the user has logged in and has authorised access to them
                                case "?", "help" -> System.out.println("""
                                        > help - Displays info about the commands that can be run
                                        > display - Shows the final report
                                        > dev list - Lists all the developers who have accounts on the system
                                        > dev task list - Lists all the tasks that the developer has assigned to them
                                        > dev max duration - Lists the develop with the longest task
                                        > dev max search - Lists the developer with the most amount of tasks
                                        > add - Adds info to the table
                                        > edit - Edits the table
                                        > remove - Removes a line from the table
                                        > logout - Logs out the user""");
                                case "display" ->
                                        TaskManager.printTaskDetails(currentUserFromCache); // displays the report
                                case "dev list" ->
                                        TaskManager.printFilteredDevsList(currentUserFromCache); // lists the developers
                                case "dev task list" -> { //lists developers with tasks according to their names
                                    System.out.println("Please list the name of the developer");
                                    TaskManager.printFilteredTaskDetails(currentUserFromCache, reader.readLine());
                                }
                                case "dev max duration" -> // lists the developer with the longest task
                                        TaskManager.printFilteredTaskDevLongest(currentUserFromCache);
                                case "dev task search" -> { //lists the developer with a search key per task name
                                    System.out.println("Please enter the name of the task to search");
                                    TaskManager.printFilteredTaskDevSearch(currentUserFromCache, reader.readLine());
                                }
                                case "add" -> { // adds a new task to the table
                                    String[] str = new String[6];
                                    System.out.println("Please prepare to enter the data for the new entry\nPlease enter the name of the task"); // 0
                                    str[0] = reader.readLine();
                                    System.out.println("Please enter the description of the task"); // 1
                                    str[1] = reader.readLine();
                                    System.out.println("Please enter the details of the developer of the task"); //2
                                    str[2] = reader.readLine();
                                    System.out.println("Please enter the task duration in hours");// 3
                                    str[3] = reader.readLine();
                                    System.out.println("Please enter the status of the task [1: To Do  2: Doing  3: Done]"); // 4
                                    str[4] = reader.readLine();
                                    taskManager.addItem(currentUserFromCache, str[0], str[1], str[2], Integer.parseInt(str[3]), Integer.parseInt(str[4]));
                                }
                                case "edit" -> { //edits current data within the existing table
                                    System.out.println("Please prepare to enter the data for the edited entry\nPlease select the index of the entry you would like to edit");
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
                                }
                                case "remove", "delete" -> { // allows users to delete a specific task
                                    System.out.println("Please input the num of the entry you would like to remove");
                                    int var4 = Integer.parseInt(reader.readLine());
                                    System.out.printf("ARE YOU SURE YOU WANT TO REMOVE ENTRY %d FROM THE TABLE?\n type  \"yes\" to confirm\n to back out type\"no\"%n", var4);
                                    if (reader.readLine().equalsIgnoreCase("yes"))
                                        taskManager.removeItem(currentUserFromCache, var4);
                                }
                                case "logout", "signout" -> { //logs the user out meaning that they cannot access the table view anymore
                                    System.out.println("User is now logged out");
                                    Login.setIsSignedIn(false);
                                    isStoppedTable = true;
                                }
                                default ->
                                        System.err.println("Unknown command. Run \"help\" for more info on commands");
                            }
                        }
                    }
                    case "signup" -> { // allows users to create accounts
                        System.out.println("Please Enter your first name");
                        String fName = reader.readLine();
                        System.out.println("Please Enter your surname");
                        String sName = reader.readLine();
                        System.out.println("Please Enter your username");
                        String uName = reader.readLine();
                        if (!lm.checkUserName(uName)) { //checks the usernames complexity
                            System.out.println("Username is not correctly formatted, please ensure that your username contains an underscore and is no more than 5 characters in length\nProcess has been canceled. To try again please type \"signup\" and try again");
                            break;
                        }
                        System.out.println("Please Enter your password [ Please note the password must at least be 8 characters long, must contain a capital letter, a number and a special character ]");
                        String uPassword = Login.readMaskedPass(); // masks the password
                        if (lm.checkPasswordComplexity(uPassword)) { // checks the passwords complexity
                            DeepEncrypt.registerUser(uName, uPassword, fName, sName); //encrypts the password
                            System.out.println("Password successfully captured\nPlease Sign in if you want to continue");
                        } else
                            System.out.println("Password is not correctly formatted, please ensure that the password contains at least 8 characters, a capital letter, a number and a special character\n\t Process has been canceled.  To try again please type \"signup\" and try again");
                    }
                    case "exit" -> System.exit(420); // exits the program
                    default -> System.err.println("Unknown command. Run \"help\" for more info on commands");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void welcome() { // a nice ascii big lettering that 90 percent of console apps have that make it just a little nicer
        System.out.println("\u001b[32m\n _    _      _                             _           _____  _     _____ _                 \n" + "| |  | |    | |                           | |         /  __ \\| |   |_   _| |                \n" + "| |  | | ___| | ___ ___  _ __ ___   ___   | |_ ___    | /  \\/| |     | | | |__   __ _ _ __  \n" + "| |/\\| |/ _ \\ |/ __/ _ \\| '_ ` _ \\ / _ \\  | __/ _ \\   | |    | |     | | | '_ \\ / _` | '_ \\ \n" + "\\  /\\  /  __/ | (_| (_) | | | | | |  __/  | || (_) |  | \\__/\\| |_____| |_| |_) | (_| | | | |\n" + " \\/  \\/ \\___|_|\\___\\___/|_| |_| |_|\\___|   \\__\\___/    \\____/\\_____/\\___/|_.__/ \\__,_|_| |_|\n" + "                                                                                            \n");
    }
}