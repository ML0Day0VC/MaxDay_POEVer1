/**
 * @author Max Day
 * Created At: 2023/04/05
 */
package API;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public class TableManager {

    //Im going to be real with you i actually cannot remember what i used this for at all i literally cannot remember
    private static String[] parseTaskObject(JSONObject mTask) {
        JSONObject task = (JSONObject) mTask.get("task");
        int taskId = (int) task.get("num");
        String taskName = (String) task.get("nTask");
        String taskDescription = (String) task.get("dTask");
        String date = (String) task.get("date");
        boolean status = (boolean) task.get("isCompleated");
        return new String[]{String.valueOf(taskId), taskName, taskDescription, date, (status ? "✓" : "☐")};
    }


    public static void genTable(String username) throws Exception {
        //TODO: what the fuck is a table at this point this is like cpme on man im loosing my mind at how todo this without loosing my mind
        JSONParser parser = new JSONParser();
        JSONArray jsonArray = new JSONArray();
        AsciiArtTable aat = new AsciiArtTable();
        aat.addHeaderCols("Num", "Name of Task", "Description", "date", "Has been Completed ");
        FileReader reader = new FileReader("src/tables/maxTable.json"); // ik u can throw this into a try catch im not dumb im just lazy
        Object obj = parser.parse(reader);
        if (obj instanceof JSONArray)
            jsonArray = (JSONArray) obj;
        for (Object objs : jsonArray) {
            String str = objs.toString();
            System.out.println();
            JSONObject jsonObject = (JSONObject) objs;
            aat.add((long) jsonObject.get("num"), (String) jsonObject.get("nTask"), (String) jsonObject.get("dTask"), (String) jsonObject.get("date"), ((boolean) jsonObject.get("isCompleated") ? "✓" : "X"));
        }
        aat.print(System.out);
    }


    // uncommented crap. for some reason i rly struggled with converting the JSOnArray to a string for whatever reason it is literally hell


    //  System.out.println(aat.getOutput());
    // String[] stringArray = jsonArray.subList(new String[0]);
    //toArray(new String[0]);


    //   for (String s : stringArray) {
    //       System.out.println(s);
    //   }

    //String[] arr = jsonArray.toString().replace("},{", " ,").split(",");     // please dont murder me its such a bad way of doing it but it works
/*
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < jsonArray.size(); i++) {
            list.add(jsonArray.toString());
        }
        String[] stringArray = list.toArray(new String[list.size()]);

 */

/*
        for (int i = 0; i < stringArray.length; i++) {
            System.out.println(stringArray[i]);
            System.out.println();
        }


        // gotta make a format for this so we can tick stuff off and remove it and more but its gonna be kinda dumb
/*
        JSONParser jsonParser = new JSONParser();
        try {


            FileManager fileManager = new FileManager();

            String s = fileManager.readFile("src/tables/maxTable.json");
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(s);

            JSONArray taskList = new JSONArray();
            taskList.add(obj);
*/

    // Object obj = jsonParser.parse(reader);
    //  JSONArray taskList = (JSONArray) obj;
    //taskList.add(obj);

    // taskList.forEach(emp -> parseTaskObject((JSONObject) emp));
/*
            taskList.forEach(item -> {
                JSONObject ob = (JSONObject) item;
                parse(String.valueOf(ob));

            });

 */


    // AsciiArtTable aat = new AsciiArtTable();
    // aat.addHeaderCols("title", "task name", "due date", "has been completed");


    //   aat.addHeaderCols("Num", "Name of Task", "Description", "date", "Has been Completed ");
    // aat.add(00, "This is the name of a task that has set up ", "This is a random task with a random description that can be anything ", "date", " ☐ ✓ ");
/*

            for (Object o : taskList) {
                JSONObject jsonLineItem = (JSONObject) o;
                System.out.println(parseTaskObject(jsonLineItem));

            }
/*
            taskList.forEach(item -> {
              String[] s = parseTaskObject(taskList.);
            aat.add();


            });

/*
    public static String[] toStringArray(JSONArray array) {
        if(array==null)
            return new String[0];

        String[] arr=new String[array.length()];
        for(int i=0; i<arr.length; i++) {
            arr[i]=array.optString(i);
        }
        return arr;
    }

 */


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