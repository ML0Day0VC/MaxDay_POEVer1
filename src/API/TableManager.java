/**
 * @author Max Day
 * Created At: 2023/04/05
 */
package API;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

import static org.json.simple.JSONValue.parse;

public class TableManager {

    private static String[] parseTaskObject(JSONObject mTask) {
        JSONObject task = (JSONObject) mTask.get("task");
        int taskId = (int) task.get("num");
        String taskName = (String) task.get("nTask");
        String taskDescription = (String) task.get("dTask");
        String date = (String) task.get("date");
        boolean status = (boolean) task.get("isCompleated");
        return new String[]{String.valueOf(taskId), taskName, taskDescription, date, (status ? "✓" : "☐")};
    }

    public static void table() {

        // gotta make a format for this so we can tick stuff off and remove it and more but its gonna be kinda dumb

        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("src/tables/maxTable.json")) {


            Object obj = jsonParser.parse(reader);
            JSONArray taskList = (JSONArray) obj;
            //taskList.add(obj);




            // taskList.forEach(emp -> parseTaskObject((JSONObject) emp));
/*
            taskList.forEach(item -> {
                JSONObject ob = (JSONObject) item;
                parse(String.valueOf(ob));

            });

 */


            AsciiArtTable aat = new AsciiArtTable();
            // aat.addHeaderCols("title", "task name", "due date", "has been completed");


            aat.addHeaderCols("Num", "Name of Task", "Description", "date", "Has been Completed ");
            // aat.add(00, "This is the name of a task that has set up ", "This is a random task with a random description that can be anything ", "date", " ☐ ✓ ");


            for (Object o : taskList) {
                JSONObject jsonLineItem = (JSONObject) o;
                System.out.println(parseTaskObject(jsonLineItem));

            }
/*
            taskList.forEach(item -> {
              String[] s = parseTaskObject(taskList.);
            aat.add();


            });

 */

            aat.print(System.out);


        } catch (Exception e) {
            e.printStackTrace();
        }


        // System.out.println(genRow(1,"order the package", "the package number is 288271672", "29/27/16262",false));


        //have a visual impression (not part of the test)


    }
/*

    public static String genRow(int n,String name, String desc, String date, boolean compleeated) {
        String top = "╠═";
        for (int i = 0; i < name.length()* 1.5 ; i++) {
            top += "═";
        }
        top += "|";
        for (int i = 0; i < desc.length()* 1.5; i++) {
            top += "═";
        }
        top += "|";
        for (int i = 0; i < date.length()* 1.5; i++) {
            top += "═";
        }
        top += "|════╣";

        String bottom = "║\t"  + n + "\t|\t" + name + "\t|\t" + desc + "\t|\t" + date  + (compleeated ? "✓": "☐" ) + " │";


        return top + "\n" + bottom + "\n" + top;
    }

 */


}

/**
 * {
 * "Tasks": [
 * {
 * "num": 1,
 * "nTask": "Name of our task",
 * "dTask": "This is the description of the task",
 * "date": "22/22/2003",
 * "isCompleated": false
 * <p>
 * },
 * {
 * "num": 1,
 * "nTask": "Name of our task",
 * "dTask": "This is the description of the task",
 * "date": "22/22/2003",
 * "isCompleated": false
 * <p>
 * },
 * {
 * "num": 1,
 * "nTask": "Name of our task",
 * "dTask": "This is the description of the task",
 * "date": "22/22/2003",
 * "isCompleated": false
 * <p>
 * },
 * {
 * "num": 1,
 * "nTask": "Name of our task",
 * "dTask": "This is the description of the task",
 * "date": "22/22/2003",
 * "isCompleated": false
 * <p>
 * }
 * ]
 * }
 */