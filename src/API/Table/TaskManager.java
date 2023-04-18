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


public class TaskManager {
    private enum taskState {
        TODO,
        DOING,
        DONE
    }

    private enum taskStatus { // I love this  - UPDATE: I cant use this im just over complicating it honestly
        TODO("To Do"),
        DOING("Doing"),
        DONE("Done");
        private String label;

        taskStatus(String label) {
            this.label = label;
        }

        public String toString() {
            return label;
        }
    }

    public String validateLabel(int stat) {
        return switch (stat) {
            case 1 -> "To Do";
            case 2 -> "Doing";
            case 3 -> "Done";
            default -> throw new IllegalStateException("Unexpected value: " + stat);
        };
    }

    public static JSONArray getArray(String path) throws Exception {
        FileReader reader = new FileReader("src/tables/" + path.toLowerCase(Locale.ROOT) + "Table.json"); // ik u can throw this into a try catch im not dumb im just lazy
        Object obj = new JSONParser().parse(reader);
        if (obj instanceof JSONArray)
            return (JSONArray) obj;
        return null;
    }

    public static void genTable(String uName) throws Exception {
        JSONArray jsonArray = getArray(uName);
        AsciiArtTable aat = new AsciiArtTable();
        aat.addHeaderCols("Task Number", "Task Name", "Task Description", "Developer Details", "Task Duration", "Task ID", "Task Status");
        int num = 0;
        for (Object objs : jsonArray) {
            JSONObject jsonObject = (JSONObject) objs;
            //TODO DONT STORE TASK ID it will cause hell later i can feel it
            String taskID = jsonObject.get("taskName").toString().substring(0, 2).toUpperCase() + ":" + jsonObject.get("taskNumber") + ":" + jsonObject.get("devDetails").toString().substring(jsonObject.get("devDetails").toString().length() - 3).toUpperCase();
            aat.add(num, jsonObject.get("taskName"), jsonObject.get("taskDesc"), jsonObject.get("devDetails"), jsonObject.get("taskDuration"), taskID, jsonObject.get("taskStatus"));
            num++;
        }
        aat.print(System.out);
    }

    public void removeItem(String uName, int index) throws Exception {
        JSONArray jsonArray = getArray(uName);
        jsonArray.remove(index - 1); // to combat 0 starting point
        update(uName, jsonArray.toJSONString());
    }

    public void addItem(String uName, String tName, String tDescription, String dDetails, int tHours, int status) throws Exception {
        JSONArray jsonArray = getArray(uName);
        JSONObject newObj = new JSONObject();
        newObj.put("taskName", tName);
        newObj.put("taskDesc", tDescription);
        newObj.put("devDetails", dDetails);
        newObj.put("taskDuration", tHours);
        newObj.put("taskStatus", validateLabel(status)); //TODO enum issues could occure here idk
        jsonArray.add(newObj);
        update(uName, jsonArray.toJSONString());
    }

    public void edit(String uName, int index, int byIndex, String newData) throws Exception {
        //TODO: im putting this as todo cause its so important, its gonna work as array then the byIndex is the date stuff ect working from left to right
        /**
         * @param taskname 1
         *  @param taskDescription 2
         *  @param taskDate 3
         *  @param compleated 4
         */
        JSONArray jsonArray = getArray(uName);
        JSONObject newObj = (JSONObject) jsonArray.get(index - 1);
        switch (byIndex) {
            case 1:
                newObj.put("nTask", newData);
                break;
            case 2:
                newObj.put("dTask", newData);
                break;
            case 3:
                newObj.put("date", newData); //TODO: add date checker to check for a valid date ect...
                break;
            case 4:
                newObj.put("isCompleated", newData.equalsIgnoreCase("true"));
                break;
            default:
                System.out.println("you messed up its between 1 and 4");
        }
        update(uName, jsonArray.toJSONString());
    }

    public void update(String path, String data) throws Exception { //
        FileWriter fileWriter = new FileWriter("src/Tables/" + path + "Table.json");
        fileWriter.write(data);
        fileWriter.flush();
        fileWriter.close();
    }
}
