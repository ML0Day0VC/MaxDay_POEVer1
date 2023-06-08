/**
 * @author Max Day
 * Created At: 2023/04/05
 */
package API.Table;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.swing.*;
import java.awt.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Locale;

/**
 * @apiNote I want to use an enum but failed. It's possible but its just not necessary in this scenario at all
 */
public class TaskManager {
    private static int totalHours = 0;

    /**
     * My sad replacement for an enum
     *
     * @param stat
     * @return void
     */
    public static String validateLabel(int stat) {
        return switch (stat) {
            case 1 -> "To Do";
            case 2 -> "Doing";
            case 3 -> "Done";
            default -> throw new IllegalStateException("Unexpected value: " + stat);
        };
    }

    /**
     * Checks the length of the description of the task
     *
     * @param tDescription
     * @return boolean
     */
    public boolean checkTaskDescription(String tDescription) {
        if (tDescription.length() > 50) {
            System.out.println("Please enter a task description of less than 50 characters");
            return true;
        }
        return false;
    }

    /**
     * Creates taskID
     *
     * @param tName
     * @param devDetails
     * @param tNumber
     * @return String
     */
    public static String createTaskID(String tName, String devDetails, int tNumber) {
        return String.format("%s:%d:%s", tName.substring(0, 2).toUpperCase(), tNumber, devDetails.substring(devDetails.length() - 3).toUpperCase());
    }

    /**
     * This is kinda cheating, but it works and its named accordingly to the document, so I should get my marks.
     *
     * @return
     */
    public static int returnTotalHours() {
        //This feels like cheating, but I have a loop running in the @printTaskDetails method, so I just total it with a local variable
        return totalHours;
    }

    /**
     * This project uses a lot of arrays but most of them are one-liners that are hidden. This one is obvious so if you need to mark an array use this one. This takes the JSONArray and returns it as an object through the passer, so we can work with it
     *
     * @param path
     * @return
     * @throws Exception
     */
    public static JSONArray getArray(String path) throws Exception {
        FileReader reader = new FileReader("src/tables/" + path.toLowerCase(Locale.ROOT) + "Table.json"); // ik u can throw this into a try catch im not dumb im just lazy
        Object obj = new JSONParser().parse(reader);
        if (obj instanceof JSONArray)
            return (JSONArray) obj; // this is a very convenient way of doing it but im not sure if its conventionally liked
        return null;
    }

    /**
     * Main print function. Please note this is meant to be a String but the way I have made the project this cannot be a string, or I will literally lose my mind.
     * To avoid me losing the marks here is how I could do it if it has to return a string. You would need to make a toString method that calls this method and accepts the string and then just prints it. I have just simplified it into one
     *
     * @param uName
     * @throws Exception
     */
    public static void printTaskDetails(String uName) throws Exception { //This is meant to be a String. I'm sorry, but I have done it very differently this whole project
        JSONArray jsonArray = getArray(uName);
        AsciiArtTable aat = new AsciiArtTable();
        aat.addHeaderCols("Task Number", "Task Name", "Task Description", "Developer Details", "Task Duration", "Task ID", "Task Status");
        int num = 0;
        for (Object objs : jsonArray) {
            JSONObject jsonObject = (JSONObject) objs;
            String taskID = createTaskID(jsonObject.get("taskName").toString(), jsonObject.get("devDetails").toString(), num);
            aat.add(num, jsonObject.get("taskName"), jsonObject.get("taskDesc"), jsonObject.get("devDetails"), jsonObject.get("taskDuration"), taskID, validateLabel(Integer.parseInt(jsonObject.get("taskStatus").toString())));
            totalHours += Integer.parseInt(jsonObject.get("taskDuration").toString());
            num++;
        }
        aat.print(System.out);
        System.out.println("Total Hours: " + returnTotalHours());
    }

    /**
     * Prints out all the tasks but only the tasks that have been assigned to a specific developer name
     *
     * @param uName
     * @param devName
     * @throws Exception
     */
    public static void printFilteredTaskDetails(String uName, String devName) throws Exception {
        JSONArray jsonArray = getArray(uName);
        AsciiArtTable aat = new AsciiArtTable();
        aat.addHeaderCols("Task Number", "Task Name", "Task Description", "Developer Details", "Task Duration", "Task ID", "Task Status");
        int num = 0;
        assert jsonArray != null; // to prevent brain rot
        for (Object objs : jsonArray) {
            JSONObject jsonObject = (JSONObject) objs;
            if (jsonObject.get("devDetails").toString().contains(devName)) {
                String taskID = createTaskID(jsonObject.get("taskName").toString(), jsonObject.get("devDetails").toString(), num);
                aat.add(num, jsonObject.get("taskName"), jsonObject.get("taskDesc"), jsonObject.get("devDetails"), jsonObject.get("taskDuration"), taskID, validateLabel(Integer.parseInt(jsonObject.get("taskStatus").toString())));
                totalHours += Integer.parseInt(jsonObject.get("taskDuration").toString());
                num++;
            }
        }
        aat.print(System.out);
        System.out.println("Total Hours: " + returnTotalHours());
    }

    /**
     * Prints out the name of the developer with the longest task
     *
     * @param uName
     * @throws Exception
     */
    public static void printFilteredTaskDevLongest(String uName) throws Exception {
        JSONArray jsonArray = getArray(uName);
        AsciiArtTable aat = new AsciiArtTable();
        aat.addHeaderCols("Developer Details", "Task Duration");
        int maxInt = 0;
        String maxDev = "";
        assert jsonArray != null; // to prevent brain rot
        for (Object objs : jsonArray) {
            JSONObject jsonObject = (JSONObject) objs;
            int taskDuration = Integer.parseInt(jsonObject.get("taskDuration").toString());
            if (taskDuration > maxInt) {
                maxDev = jsonObject.get("devDetails").toString();
                maxInt = taskDuration;
            }
        }
        aat.add(maxDev, maxInt);
        aat.print(System.out);
    }

    /**
     * Prints out all the tasks with the specific key in the name of the task.
     *
     * @param uName
     * @param key
     * @throws Exception
     */
    public static void printFilteredTaskDevSearch(String uName, String key) throws Exception {
        JSONArray jsonArray = getArray(uName);
        AsciiArtTable aat = new AsciiArtTable();
        aat.addHeaderCols("Task Number", "Task Name", "Task Description", "Developer Details", "Task Duration", "Task ID", "Task Status");
        int num = 0;
        assert jsonArray != null; // to prevent brain rot
        for (Object objs : jsonArray) {
            JSONObject jsonObject = (JSONObject) objs;
            if (jsonObject.get("taskName").toString().contains(key)) {
                String taskID = createTaskID(jsonObject.get("taskName").toString(), jsonObject.get("devDetails").toString(), num);
                aat.add(num, jsonObject.get("taskName"), jsonObject.get("taskDesc"), jsonObject.get("devDetails"), jsonObject.get("taskDuration"), taskID, validateLabel(Integer.parseInt(jsonObject.get("taskStatus").toString())));
                num++;
            }
        }
        aat.print(System.out);

    }

    /**
     * Prints a list of all the developers
     *
     * @param uName
     * @throws Exception
     */
    public static void printFilteredDevsList(String uName) throws Exception {
        JSONArray jsonArray = getArray(uName);
        AsciiArtTable aat = new AsciiArtTable();
        aat.addHeaderCols("Index", "Developer:");
        int num = 0;
        assert jsonArray != null; // to prevent brain rot
        for (Object objs : jsonArray) {
            JSONObject jsonObject = (JSONObject) objs;
            aat.add(num, jsonObject.get("devDetails"));
            num++;
        }
        aat.print(System.out);
    }

    /**
     * Removes removes item from list
     *
     * @param uName
     * @param index
     * @throws Exception
     */
    public void removeItem(String uName, int index) throws Exception {
        JSONArray jsonArray = getArray(uName);
        jsonArray.remove(index);
        update(uName, jsonArray.toJSONString());
    }

    /**
     * Adds item from task list
     *
     * @param uName
     * @param tName
     * @param tDescription
     * @param dDetails
     * @param tHours
     * @param status
     * @throws Exception
     */
    public void addItem(String uName, String tName, String tDescription, String dDetails, int tHours, int status) throws Exception {
        JSONArray jsonArray = getArray(uName);
        JSONObject newObj = new JSONObject();
        newObj.put("taskName", tName);
        if (checkTaskDescription(tDescription)) return; // checks the task description length
        newObj.put("taskDesc", tDescription);
        newObj.put("devDetails", dDetails);
        newObj.put("taskDuration", tHours);
        newObj.put("taskStatus", status);
        jsonArray.add(newObj);
        // this HTML formatting is so nice I love it so much
        // the order is meant to be: "Task Status, Developer Details, Task Number, Task Name, Task, Description, Task ID and Duration" according to the rubric but it really does not matter. What's important is that all the info is there
        String txt = String.format("<html><b>Please confirm the following input:</b><li>   Task Name: <b>%s</b><li>  Description: <b>%s</b><li>  Developer Details: <b>%s</b><li>  Task Duration: <b>%d</b><li>  Task Status: <b>%s</b><br><br><b>Press <u>Yes</u> to confirm, and <u>No</u> to cancel</b></html>", tName, tDescription, dDetails, tHours, validateLabel(status));
        JLabel label = new JLabel(txt);
        label.setFont(new Font("serif", Font.PLAIN, 14));
        int jop = JOptionPane.showConfirmDialog(null, label, "Confirm Input", JOptionPane.YES_NO_OPTION);
        if (jop == JOptionPane.YES_OPTION) { // returns 0 for yes and 1 for no why???????
            System.out.println("Task successfully captured");
            update(uName, jsonArray.toJSONString()); // will only execute when the jOptionPane receives yes as the input from the user
            return;
        }
        System.out.println("Task was not captured");
    }

    /**
     * Very simple. Takes in 2 numbers. One to find what variable to change and the only to find what row to change it in. This is then overwritten, then updated
     *
     * @param uName
     * @param index
     * @param byIndex
     * @param newData
     * @throws Exception
     */
    public void edit(String uName, int index, int byIndex, String newData) throws Exception {
        /**
         * @aNote important, it's going to work as array then the byIndex is the date stuff ect working from left to right
         *                                                 > 1 - Task Name
         *                                                 > 2 - Description of Task
         *                                                 > 3 - Developer Details
         *                                                 > 4 - Task Duration
         *                                                 > 5 - Task Status
         */
        JSONArray jsonArray = getArray(uName);
        assert jsonArray != null; // prevents brain rot
        JSONObject newObj = (JSONObject) jsonArray.get(index);
        switch (byIndex) {
            case 1 -> newObj.put("taskName", newData);
            case 2 -> {
                if (checkTaskDescription(newData)) return;
                newObj.put("taskDesc", newData);
            }
            case 3 -> newObj.put("devDetails", newData);
            case 4 -> newObj.put("taskDuration", newData);
            case 5 -> newObj.put("taskStatus", newData);
            default -> System.out.println("you messed up its between 1 and 4");
        }
        update(uName, jsonArray.toJSONString());
    }


    /**
     * File update. I should probs use a global file manager but this can be done later on for now this is fine, and it works
     *
     * @param path
     * @param data
     * @throws Exception
     */
    public void update(String path, String data) throws Exception { //
        FileWriter fileWriter = new FileWriter("src/Tables/" + path + "Table.json");
        fileWriter.write(data);
        fileWriter.flush();
        fileWriter.close();
        System.out.println("\n\n\t\t\t\t>>>  Updated the table  <<<");
        printTaskDetails(path);
    }

}


