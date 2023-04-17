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

public class TableManager {

    //I'm going to be real with whoever is reading this... I actually cannot remember what I used this for at all I literally cannot remember
    private static String[] parseTaskObject(JSONObject mTask) {
        JSONObject task = (JSONObject) mTask.get("task");
        int taskId = (int) task.get("num");
        String taskName = (String) task.get("nTask");
        String taskDescription = (String) task.get("dTask");
        String date = (String) task.get("date");
        boolean status = (boolean) task.get("isCompleated");
        return new String[]{String.valueOf(taskId), taskName, taskDescription, date, (status ? "✓" : "☐")};
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
        aat.addHeaderCols("Num", "Name of Task", "Description", "date", "Has been Completed ");
        int num = 1;
        for (Object objs : jsonArray) {
            JSONObject jsonObject = (JSONObject) objs;
            aat.add(num, (String) jsonObject.get("nTask"), (String) jsonObject.get("dTask"), (String) jsonObject.get("date"), ((boolean) jsonObject.get("isCompleated") ? "✓" : "X"));
            num++;
        }
        aat.print(System.out);
    }

    public void removeItem(String uName, int index) throws Exception {
        JSONArray jsonArray = getArray(uName);
        jsonArray.remove(index - 1); // to combat 0 starting point
        update(uName, jsonArray.toJSONString());
    }

    public void addItem(String uName, String taskName, String taskDescription, String dDate, boolean completed) throws Exception {
        JSONArray jsonArray = getArray(uName);
        JSONObject newObj = new JSONObject();
        newObj.put("nTask", taskName);
        newObj.put("dTask", taskDescription);
        newObj.put("date", dDate);
        newObj.put("isCompleated", completed);
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
