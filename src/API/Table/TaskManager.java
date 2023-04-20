/**
 * @author Max Day
 * Created At: 2023/04/05
 */
package API.Table;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.Locale;

/**
 * @apiNote I wanna use an enum but failed. Its possible but its just not necessary in this scenario at all
 */
public class TaskManager {
    private static int totalHours = 0;

    public static String validateLabel(int stat) {
        return switch (stat) {
            case 1 -> "To Do";
            case 2 -> "Doing";
            case 3 -> "Done";
            default -> throw new IllegalStateException("Unexpected value: " + stat);
        };
    }

    public boolean checkTaskDescription(String tDescription) {
        if (tDescription.length() > 50) {
            System.out.println("Please enter a task description of less than 50 characters");
            return true;
        }
        return false;
    }

    public static String createTaskID(String tName, String devDetails, int tNumber) {
        return String.format("%s:%d:%s", tName.substring(0, 2).toUpperCase(), tNumber, devDetails.substring(devDetails.length() - 3).toUpperCase());
    }

    public static int returnTotalHours() {
        //This feels like cheating but i have a loop running in the @printTaskDetails method so i just total it with a local variable
        return totalHours;
    }

    public static JSONArray getArray(String path) throws Exception {
        FileReader reader = new FileReader("src/tables/" + path.toLowerCase(Locale.ROOT) + "Table.json"); // ik u can throw this into a try catch im not dumb im just lazy
        Object obj = new JSONParser().parse(reader);
        if (obj instanceof JSONArray) return (JSONArray) obj;
        return null;
    }

    public static void printTaskDetails(String uName) throws Exception { //This is meant to be a String. I'm sorry but I have done it very differently this whole project
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

    public void removeItem(String uName, int index) throws Exception {
        JSONArray jsonArray = getArray(uName);
        jsonArray.remove(index);
        update(uName, jsonArray.toJSONString());
    }

    public void addItem(String uName, String tName, String tDescription, String dDetails, int tHours, int status) throws Exception {
        JSONArray jsonArray = getArray(uName);
        JSONObject newObj = new JSONObject();
        newObj.put("taskName", tName);
        if (checkTaskDescription(tDescription)) return;
        newObj.put("taskDesc", tDescription);
        newObj.put("devDetails", dDetails);
        newObj.put("taskDuration", tHours);
        newObj.put("taskStatus", status); //TODO enum issues could occure here idk
        jsonArray.add(newObj);
        System.out.println("Task successfully captured");
        update(uName, jsonArray.toJSONString());

    }

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
            case 2 -> newObj.put("taskDesc", newData); //TODO lenght of description check
            case 3 -> newObj.put("devDetails", newData);
            case 4 -> newObj.put("taskDuration", newData);
            case 5 -> newObj.put("taskStatus", newData);
            default -> System.out.println("you messed up its between 1 and 4");
        }
        update(uName, jsonArray.toJSONString());
    }

    public void update(String path, String data) throws Exception { //
        FileWriter fileWriter = new FileWriter("src/Tables/" + path + "Table.json");
        fileWriter.write(data);
        fileWriter.flush();
        fileWriter.close();
        System.out.println("\n\n\t\t\t\t>>>  Updated the table  <<<");
        printTaskDetails(path);
    }


    //TODO filter task for each developer
}


