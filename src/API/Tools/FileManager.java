/**
 * @author Max Day
 * Created At: 2023/04/04
 */
package API.Tools;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;

public class FileManager {

    /**
     * @apiNote If anyone is looking at this file im sorry I just needed a miscellaneous class to chuck some file stuff in
     */
    private static final String PATH = "src/Tables/passwords.txt";

    public void writeFile(String data) throws IOException { // for anyone who wants to say that throwing it here is stupid then I say cope
        FileWriter fWriter = new FileWriter(PATH);
        fWriter.write(data);
        fWriter.close();
    }

    public String readFile() throws IOException {
        return Files.readString(Paths.get(PATH));
    }
    public void addEntry(String entry) throws IOException {
        String s = readFile();
        if (s.contains(entry.split("\\|")[0] + "|")) {
            System.err.println("The username already exists. Failed to write new user");
            return;
        }
        writeFile(s + "\n" + entry);
    }
    //////TBLManager//////

    //TODO DESPERATELY need to clean the file manager up
    public void createTBLManager(String uName) throws IOException {
        // Files.createFile(Path.of("src/tables/" + uName.toLowerCase(Locale.ROOT) + "Table.json"));
        byte data[] = "[{\"nTask\": \"Example task name\",\"dTask\": \"Example task description\",\"date\": \"22/22/2003\",\"isCompleated\": false}]".getBytes();
        Path file = Paths.get("src/Tables/" + uName.toLowerCase(Locale.ROOT) + "Table.json");
        Files.write(file, data);

    }


}



